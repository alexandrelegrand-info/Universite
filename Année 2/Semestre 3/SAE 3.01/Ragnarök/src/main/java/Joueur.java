import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Joueur extends Entity{
    private int lvl;
    private int current_xp;
    private int xp_cap;
    private Weapon weapon;
    private Inventaire inventaire;

    public Joueur(String name){
        setName(name);
        setHp(50);
        setHp_cap(50);
        setAttack(5);
        setDefense(5);
        ArrayList<Attack> a = new ArrayList<>();
        a.add(new Attack("Attaque légère", null, 10));
        a.add(new Attack("Attaque lourde", null, 25));
        this.inventaire = new Inventaire(buffer("res/lettre-inventaire.txt"));
        this.weapon = new Weapon("Epée magique", a);
        this.lvl = 1;
        this.current_xp = 0;
        this.xp_cap = 20;
    }

    public int getXp_cap() {
        return xp_cap;
    }
    public int getCurrent_xp() {
        return current_xp;
    }
    public int getLvl() {
        return lvl;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setCurrent_xp(int current_xp) {
        this.current_xp = current_xp;
    }

    public void setXp_cap(int xp_cap) {
        this.xp_cap = xp_cap;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    public void ajouterXP(Mob mob){
        this.current_xp += (mob.getAttack() + mob.getDefense()) * mob.getMultiplicator();
        lvlCheck();
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void lvlCheck(){
        if(current_xp >= xp_cap){
            lvl++;
            System.out.println("Vous êtes monté niveau " + lvl);
            xp_cap = (int) (xp_cap + xp_cap * 0.2);
            setHp_cap(getHp_cap() + 5);
            setHp(getHp() + 5);
            System.out.println("Quel stat souhaitez-vous améliorer ?\n1) Attaque 2) Défense");
            Scanner s = new Scanner(System.in);
            int choix = s.nextInt();
            boolean loop = true;
            while(loop){
                if (choix > 0 && choix < 3){
                    if(choix == 1){
                        setAttack(getAttack() + 1);
                    }
                    if(choix == 2){
                        setDefense(getDefense() + 1);
                    }
                    loop = false;
                } else {
                    System.out.println("Erreur, entrez un nombre entre 1 et 2");
                    choix = s.nextInt();
                }
            }
        }
    }

    public static String buffer(String cheminFichier){
        String donnee = "";

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))){
            
            String ligne = br.readLine();
            while (ligne != null) {
                donnee += ligne + '\n';
                ligne = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return donnee;
    }
    public void afficherBarreDeVie() {
        int hpMax = getHp_cap();
        int hpActuel = getHp();
        int tailleBarre = 20;
    
        int pourcentage = (int) ((double) hpActuel / hpMax * tailleBarre);
        
        StringBuilder barre = new StringBuilder("[");
        for (int i = 0; i < tailleBarre; i++) {
            if (i < pourcentage) {
                barre.append("■"); // Rempli
            } else {
                barre.append(" "); // Vide
            }
        }
        barre.append("]");
    
        System.out.println(getName() + " : PV " + barre + " " + hpActuel + "/" + hpMax);
    }
    public void useItem(Potion potion){
        if(getHp() + potion.getHpRegenerated() > getHp_cap()){
            setHp(getHp_cap());
        } else {
            setHp(getHp() + potion.getHpRegenerated());
        }
    }

    static boolean SavePlayer(Joueur j) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("res/saves/save.csv", true))) {
            
            String ligne = String.join(",", j.getName(),String.valueOf(j.getHp_cap()), String.valueOf(j.getLvl()),String.valueOf(j.getCurrent_xp()), String.valueOf(j.getAttack()), String.valueOf(j.getDefense()), String.valueOf(j.getWeapon().getName()));
            writer.write(ligne);
            writer.newLine();
        }

        return true;
    }

    public static Joueur charger(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("res/saves/save.csv"));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] donnees = ligne.split(",");
                String nom = donnees[0];
                if(nom.equals(name)){
                    Joueur j = new Joueur(name);
                    j.setHp_cap(Integer.parseInt(donnees[1]));
                    j.setLvl(Integer.parseInt(donnees[2]));
                    j.setCurrent_xp(Integer.parseInt(donnees[3]));
                    j.setAttack(Integer.parseInt(donnees[4]));
                    j.setDefense(Integer.parseInt(donnees[5]));
                    return j;

                }


            
        }
        return null;
    }

    public static boolean estJoueur(String nom) throws IOException, InterruptedException, NameAlreadyExists{
        BufferedReader reader = new BufferedReader(new FileReader("res/saves/save.csv"));
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] donnees = ligne.split(",");
            if(nom.equals(donnees[0])){
                throw new NameAlreadyExists("Le joueur avec le nom '" + nom + "' existe déjà");
            }
       
    }
    return false;
}
public static void SaveExistingPlayer(Joueur j) throws IOException{
    BufferedReader reader = new BufferedReader(new FileReader("res/saves/save.csv"));
    // BufferedWriter writer = new BufferedWriter(new FileWriter("res/saves/save.csv", true)) ;
    ArrayList<String> noms = new ArrayList<String>();
    boolean nomExistant = false;
    String ligne;
    while((ligne = reader.readLine())!= null){
        String [] donnees = ligne.split(",");
        if(j.getName().equals(donnees[0])){
            nomExistant = true;
        }else{
            noms.add(ligne);
        }

    }
    reader.close();
    BufferedWriter writer = new BufferedWriter(new FileWriter("res/saves/save.csv"));
    for (String s : noms) {
        writer.write(s);
        writer.newLine();
          
    }
    writer.close();
    
    writer = new BufferedWriter(new FileWriter("res/saves/save.csv", true));
    String Newligne = String.join(",", j.getName(),String.valueOf(j.getHp_cap()), String.valueOf(j.getLvl()),String.valueOf(j.getCurrent_xp()), String.valueOf(j.getAttack()), String.valueOf(j.getDefense()), String.valueOf(j.getWeapon().getName()));
    writer.write(Newligne);
    writer.newLine();
    writer.close();
    
}
}