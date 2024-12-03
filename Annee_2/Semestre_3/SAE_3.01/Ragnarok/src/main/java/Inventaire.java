import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Inventaire {
    private ArrayList<Item> inventaire;
    private String lettreAffichage;
    private String[][] chiffreAffichage;

    public Inventaire(String lettreAffichage){
        this.inventaire = new ArrayList<Item>();
        this.lettreAffichage = lettreAffichage;
        this.chiffreAffichage = importDonneeTableauChiffre();
        ajouterItem(new Potion(20));
    }

    public static String[][] importDonneeTableauChiffre(){
        String[][] donnee = new String[4][5];

        for(int i=1; i<5; i++){
            try (BufferedReader br = new BufferedReader(new FileReader("res/"+i+".txt"))){
                String ligne = br.readLine();
                for(int j=0; j<5; j++){
                    donnee[i-1][j] = ligne;
                    ligne = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return donnee;
    }

    public void ajouterItem(Item item){
        boolean itemPresent = false;
        for (Item i : this.inventaire) {
            if (i instanceof Weapon) {
                Weapon w = (Weapon) i;
                if (w.equals(item)) {
                    itemPresent = true;
                }
            }
            
        }
        if(!itemPresent){
            this.inventaire.add(item);
        }
    }

    public void retirerItem(Item item){
        this.inventaire.remove(item);
    }

    public Item ouvertureInventaire(){
        int numeroPageActive = 0;
        Scanner sc = new Scanner(System.in);
        boolean inventaireOuvert = true;

        System.out.println(this.lettreAffichage);
        System.out.println(afficherPage(numeroPageActive));
        System.out.println(afficherCommande(numeroPageActive));

        while (inventaireOuvert) {
            String saisie = sc.nextLine();
            if(saisie.equals("R")){
                inventaireOuvert = false;
            }else if(saisie.equals("P") && numeroPageActive>0){
                numeroPageActive--;
            }else if(saisie.equals("S") && numeroPageActive > this.inventaire.size()%24){
                numeroPageActive++;
            }else{
                if(saisie.length() == 2 && Character.isLetter(saisie.charAt(0)) && Character.isDigit(saisie.charAt(1))){
                    if(Character.toUpperCase(saisie.charAt(0)) >= 'A' && Character.toUpperCase(saisie.charAt(0)) <= 'F' && Integer.parseInt("" + saisie.charAt(1)) < 5 && Integer.parseInt("" + saisie.charAt(1)) > 0){
                        int numeroArme = (((Integer.parseInt("" + saisie.charAt(1))-1)*6)+((int) saisie.charAt(0)-65));
                        if(numeroArme <= this.inventaire.size()){
                            return this.inventaire.get(numeroArme);
                        }else{
                            System.out.println(this.lettreAffichage);
                            System.out.println(afficherPage(numeroPageActive));
                            System.out.println("il n'y a pas d'item dans cette case");
                            System.out.println(afficherCommande(numeroPageActive));
                            
                        }
                    }
                }
            }
        }
        return null;
    }

    public String afficherCommande(int numeroPageActive){
        String res = "R: retour";
        if(numeroPageActive>0){
            res += ", P: page précédente";
        }
        if(numeroPageActive > this.inventaire.size()%24){
            res += ", S: page suivante";
        }

        return res +'\n';
    }


    public String afficherPage(int numeroPage){
        String haut = " ----------------   ----------------   ----------------   ----------------   ----------------   ----------------  \n";
        String milieu = "|                | |                | |                | |                | |                | |                | ";
        String bas = " ----------------   ----------------   ----------------   ----------------   ----------------   ----------------  \n";

        String res = "";
        for(int i=0; i<4; i++){
            res += haut;
            for(int j=0; j<2; j++){
                res += milieu;
                if(j==0){
                    res += '\n';
                }else{
                    res+= this.chiffreAffichage[i][0]+'\n';
                }
            }
            String nomArme = "";
            for(int k=numeroPage*24+i*6; k<numeroPage*24+i*6+6; k++){
                if(k < this.inventaire.size()){
                    int tailleEspace = (int) (16-this.inventaire.get(k).getName().length())/2;
                    if(tailleEspace < 0){
                        nomArme += "|" + this.inventaire.get(k).getName().substring(0, 13) + "...| ";
                    }else{
                        String espacement = "";
                        for(int size=0; size<tailleEspace; size++){
                            espacement += " ";
                        }

                        nomArme += "|" + espacement + this.inventaire.get(k).getName() + espacement;

                        if (this.inventaire.get(k).getName().length()%2==0) {
                            nomArme += "| ";
                        }else{
                            nomArme += " | ";
                        }
                    }
                }else{
                    nomArme += "|                | ";
                }
            }

            res += nomArme + this.chiffreAffichage[i][1] + '\n';
            res += milieu + this.chiffreAffichage[i][2] + '\n';

            String statArme = "";

            for(int k=numeroPage*24+i*6; k<numeroPage*24+i*6+6; k++){
                if(k < this.inventaire.size()){
                    String stat = "";
                    if(this.inventaire.get(k) instanceof Weapon){
                        Weapon arme = (Weapon) this.inventaire.get(k);
                        stat = " Att: " + arme.getAttacks().get(0).getDamage(1.0);
                    }else if(this.inventaire.get(k) instanceof Potion){
                        Potion arme = (Potion) this.inventaire.get(k);
                        stat = " Soin: " + arme.getHpRegenerated();
                    }
                    
                    int tailleFin = 16-stat.length();
                    String espacementFin = "";
                    for(int size=0; size<tailleFin; size++){
                        espacementFin += " ";
                    }

                    statArme += "|" + stat + espacementFin + "| ";
                }else{
                    statArme += "|                | ";
                }
            }

            res += statArme + this.chiffreAffichage[i][3] + '\n';

            res += milieu + this.chiffreAffichage[i][4] + '\n';
            res += milieu + '\n';
            res += bas;
            
        }
        return res;
    }

    
}
