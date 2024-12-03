import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Random;


public class Main {
    static final String clean = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    static double niveauDiff = 0;
    static public void MenuPrincipale(){
       
        System.out.println(Joueur.buffer("res/menu.txt"));
        System.out.println("            1) Commencer une nouvelle partie                                                                                                       2) Charger une partie");        
    }
// if(Joueur.estJoueur(nom)){
        // System.out.println("Pseudo déjà pris");
        // TimeUnit.SECONDS.sleep(2);
        // System.out.println(clean);
        // }
    public static void LancementPartie() throws InterruptedException, IOException, NameAlreadyExists {
        
        String nom = "";
        boolean find = false;
        System.out.println(clean);
        niveauDiff = menuLVL();
        System.out.println(clean);
    while(find == false){
        try{
        System.out.println("Entrez le nom de votre personnage");
        Scanner s = new Scanner(System.in);
        nom = s.nextLine();
        Joueur.estJoueur(nom);
        find = true;
        Joueur j = new Joueur(nom);
        Joueur.SavePlayer(j);
        System.out.println(clean);
        System.out.println("Il fait froid....");
        TimeUnit.SECONDS.sleep(3);
        System.out.println(clean);
        System.out.println("*Vous vous réveillez sur un gigantesque arbre, ressemblant à celui des histoires vikings*");
        TimeUnit.SECONDS.sleep(3);
        System.out.println(clean);
        System.out.println("*Vous avez à peine le temps de vous remettre de vos émotions que vous vous faites attaquer par un monstre !*");
        TimeUnit.SECONDS.sleep(3);
        ArrayList<Attack> a = new ArrayList<>();
        a.add(new Attack("Coup d'épée", null, 5));
        combat(j, new Mob(new Weapon("Epée en bois", a), "Créature Inconnue", 30,1, 1, "res/ascii-mob1.txt"));
        j.setHp(j.getHp_cap());
        yggdrasil(j);
        
    }catch(NameAlreadyExists e){
        System.out.println(e.getMessage());
        TimeUnit.SECONDS.sleep(2);
        System.out.println(clean);
    }
}

    }

static public double menuLVL() throws InterruptedException{
        int choix=0;
        while (choix<1 || choix>3) {
            System.out.println(Joueur.buffer("res/choix-lvl.txt"));
            System.out.println("veuillez entrer 1, 2 ou 3 pour choisir un niveau de difficulté");
            Scanner s = new Scanner(System.in);
            choix = s.nextInt();
            if (choix==1){
                return 2;
            }if(choix==2){
                return 1.5;
            }if (choix==3){
                return 1;
            }
            System.out.println("Veuillez entrer un niveau valide");
            System.out.println("veuillez entrer 1, 2 ou 3 pour choisir un niveau de difficulté");
            TimeUnit.SECONDS.sleep(3);
            System.out.println(clean);
        }
        return 1;
}

    public static void afficherMob(Mob m, Joueur j) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(m.getCharadesign()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        m.afficherBarreDeVie();
        System.out.println();

        j.afficherBarreDeVie();
        
    }

    public static boolean combat(Joueur j, Mob m) throws IOException, InterruptedException{
        boolean fin = false;
        while (!fin) {
            afficherMob(m,j);
            System.out.println("1) Attaques  2) Inventaire");
            boolean boole = false;
            while(!boole){
                Scanner s = new Scanner(System.in);
                int choix = s.nextInt();
                if(choix == 2){
                    Item itemSelectionne = j.getInventaire().ouvertureInventaire();
                    boole = true;
                    if(itemSelectionne != null && itemSelectionne instanceof Potion potion){
                        j.useItem(potion);
                        j.getInventaire().retirerItem(potion);
                    }else if(itemSelectionne != null && itemSelectionne instanceof Weapon){
                        Weapon armeJoueur = j.getWeapon();
                        j.setWeapon((Weapon) itemSelectionne);
                        j.getInventaire().ajouterItem(armeJoueur);
                        j.getInventaire().retirerItem(itemSelectionne);
                    }
                    System.out.println(clean);
                    afficherMob(m,j);
                    if(itemSelectionne instanceof Weapon){
                        System.out.println("Vous avez changé d'arme pour " + itemSelectionne.getName());
                    }
                } if (choix == 1){
                    boole = true;
                }
            }
            System.out.println(j.getWeapon().listAttack());
            System.out.println();
            boolean bool = false;
            Attack a = null;
            while(!bool){
                Scanner s = new Scanner(System.in);
                int choix = s.nextInt();
                if(choix > 0 && choix <= j.getWeapon().getAttacks().size()){
                    a = j.getWeapon().getAttacks().get(choix - 1);
                    bool = true;
                }
            }            

            System.out.println("Vous décidez d'attaquer avec " + a.getName());
            int damage = 0;
            if(j.getAttack() > m.getDefense()){
                damage = a.getDamage(niveauDiff) + j.getAttack();
                m.setHp(m.getHp() - damage);
            } else {
                damage = a.getDamage(niveauDiff);
                m.setHp(m.getHp() - damage);
            }
            if(a.getStatus() != null){
                System.out.println("Vous infligez des dégats de " + a.getStatus().toString());
                if(a.getStatus().equals(Status.GLACE)){
                    m.setHp(m.getHp() - 3);
                    damage = damage + 3;
                } 
                if(a.getStatus().equals(Status.POISON)){
                    m.setHp(m.getHp() - 6);
                    damage = damage + 6;
                }
                if(a.getStatus().equals(Status.FEU)){
                    m.setHp(m.getHp() - 9);
                    damage = damage + 9;
                }
                if(a.getStatus().equals(Status.PARALYSIE)){
                    m.setHp(m.getHp() - 7);
                    damage = damage + 7;
                }
            }
            System.out.println("Vous avez infligé " + damage + " à " + m.getName());
            TimeUnit.SECONDS.sleep(2);
            if(m.getHp() <= 0){
                fin = true;
                System.out.println("Vous avez triomphé de " + m.getName() + ", il vous a rapporté " + (m.getAttack() + m.getDefense()) * m.getMultiplicator() + "XP");
                j.ajouterXP(m);
                Random r = new Random();
                if(r.nextInt(3) == 2){
                    j.getInventaire().ajouterItem(m.getHoldWeapon());
                }
                if(r.nextInt(5) == 4){
                    int taillePotion = r.nextInt(3);
                    if(taillePotion == 0){
                        j.getInventaire().ajouterItem(new Potion(20));
                    }else if(taillePotion == 1){
                        j.getInventaire().ajouterItem(new Potion(40));
                    }else if(taillePotion == 2){
                        j.getInventaire().ajouterItem(new Potion(60));
                    }
                }
                return true;
            } else {
                Random r = new Random();
                int n = r.nextInt(m.getHoldWeapon().getAttacks().size());
                Attack mobAttack = m.getHoldWeapon().getAttacks().get(n);
                if(m.getAttack() > j.getDefense()){
                    damage =(int) ((mobAttack.getDamage(niveauDiff) + m.getAttack()) * niveauDiff);
                    j.setHp(j.getHp() - damage);
                } else {
                    damage = (int) (mobAttack.getDamage(niveauDiff) * niveauDiff);
                    j.setHp(j.getHp() - damage);
                }
                System.out.println(m.getName() + " vous attaque avec " + mobAttack.getName());
                TimeUnit.SECONDS.sleep(2);
                if(mobAttack.getStatus() != null){
                    System.out.println("Vous subissez des dégats de " + mobAttack.getStatus().toString());
                    if(mobAttack.getStatus().equals(Status.GLACE)){
                        j.setHp(j.getHp() - 3);
                        damage = damage + 3;
                    } 
                    if(mobAttack.getStatus().equals(Status.POISON)){
                        j.setHp(j.getHp() - 6);
                        damage = damage + 6;
                    }
                    if(mobAttack.getStatus().equals(Status.FEU)){
                        j.setHp(j.getHp() - 9);
                        damage = damage + 9;
                    }
                    if(mobAttack.getStatus().equals(Status.PARALYSIE)){
                        j.setHp(j.getHp() - 7);
                        damage = damage + 7;
                    }
                }
                System.out.println("Cette attaque vous a infligé " + damage);
                TimeUnit.SECONDS.sleep(2);
                if(j.getHp() <= 0){
                    fin = true;
                    System.out.println(clean);
                    System.out.println(Joueur.buffer("res/Mort.txt"));
                    j.setInventaire(new Inventaire(Joueur.buffer("res/lettre-inventaire.txt")));
                    TimeUnit.SECONDS.sleep(3);
                    return false;
                }
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println(clean);
        }
        return true;
    }

    public static void yggdrasil(Joueur j) throws IOException, InterruptedException{
        j.setHp(j.getHp_cap());
        System.out.println(Joueur.buffer("res/ascii-yggdrasil.txt"));
        System.out.println("Vous vous réveillez dans Yggdrasil... " + j.getName()  + ", où voulez vous aller ?");
        System.out.println(j.getName() + " : Niveau " + j.getLvl() + ", Attaque : " + j.getAttack() + ", Defense : " + j.getDefense());
        System.out.println("1) Midgard 2) Asgard 3) Helheim 4) Sauvegarder & Quitter");
        boolean fin = false;
        while(!fin){
            Scanner s = new Scanner(System.in);
            int choix = 0;
            choix = s.nextInt();
            if(choix > 0 && choix < 5){
                if(choix == 1){
                    midgard(j);
                }
                if(choix == 2){
                    asgard(j);
                }
                if(choix == 3){
                    helheim(j);
                }
                if(choix == 4){
                    Joueur.SaveExistingPlayer(j);
                    System.exit(0);
                }

            }
        }
    }

    public static void midgard(Joueur j) throws IOException, InterruptedException{
        System.out.println("Vous prenez le portail vers Midgard.. Une région froide vous attend");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("À peine vous arrivez qu'un Draugr vous attaque, préparez-vous au combat !");
        TimeUnit.SECONDS.sleep(2);
        ArrayList<Attack> a = new ArrayList<>();
        a.add(new Attack("Tranche", Status.POISON, 10));
        a.add(new Attack("Assassinat", Status.POISON, 5));
        if(combat(j, new Mob(new Weapon("Griffes acérées", a), "Draugr", (int) (30*niveauDiff), 4, 4, "res/ascii-mob3.txt"))){
            TimeUnit.SECONDS.sleep(2);
            System.out.println(clean);
            System.out.println("Le Vieux Roi des Draugr vous attaque, le combat est inévitable !");
            TimeUnit.SECONDS.sleep(2);
            if(combat(j, new Mob(new Weapon("Griffes acérées", a), "Le Vieux Roi des Draugr", (int) (10*niveauDiff), 10, 10, "res/ascii-mob3.txt"))){
                System.out.println("L'ancien est complètement finito, vraiment trop simple");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(clean);
                System.out.println("Ceci dit, le serpent monde n'a pas trop apprécié...");
                TimeUnit.SECONDS.sleep(3);
                ArrayList<Attack> b = new ArrayList<>();
                b.add(new Attack("Tsunami Glacé", Status.GLACE, 20));
                b.add(new Attack("Charge d'attaque", null, 5));
                b.add(new Attack("Lance Flamme", Status.FEU, 25));
                if(combat(j, new Boss("Jǫrmungandr", new Weapon("Arme Jǫrmungandr", b),  "res/ascii-boss2.txt", 3 ,(int) (60*niveauDiff), 6, 4))){
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(clean);
                    System.out.println(Joueur.buffer("res/victoire.txt"));
                    TimeUnit.SECONDS.sleep(3);
                }
            }
        }
        yggdrasil(j);
    }

    public static void asgard(Joueur j) throws IOException, InterruptedException{
        System.out.println("Vous prenez le portail vers Asgard.. La terre des Dieux vous attend de l'autre côté");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("La région semble vide, jusqu'à ce qu'un serpent sorte de terre !");
        TimeUnit.SECONDS.sleep(2);
        ArrayList<Attack> a = new ArrayList<>();
        a.add(new Attack("Morsure", Status.POISON, 10));
        a.add(new Attack("Lancer venimeux", Status.POISON, 15));
        if(combat(j, new Mob(new Weapon("Dents empoisonnées", a), "Serpent Gardien", (int) (60*niveauDiff), 7, 8, "res/ascii-mob4.txt"))){
            TimeUnit.SECONDS.sleep(2);
            System.out.println(clean);
            System.out.println("Ce combat était éprouvant... Des éclairs surgissent au loin : c'est Thor !!");
            TimeUnit.SECONDS.sleep(2);
            ArrayList<Attack> b = new ArrayList<>();
            b.add(new Attack("Mjölnir", Status.PARALYSIE, 30));
            b.add(new Attack("Foudre du gardien", Status.PARALYSIE, 20));
            b.add(new Attack("Chargement", null, 5));
            if(combat(j, new Boss("Thor, gardien d'Asgard", new Weapon("Mjölnir", b),  "res/ascii-boss3.txt", 5 , (int) (100*niveauDiff), 10, 10))){
                System.out.println("Pff.. c'est ça le fils d'Odin");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(clean);
                System.out.println("*Thor se relève, on est reparti pour un tour*");
                TimeUnit.SECONDS.sleep(3);
                if(combat(j, new Boss("Thor, fils d'Odin", new Weapon("Mjölnir", b),  "res/ascii-boss3.txt", 6 , (int) (100*niveauDiff), 12, 12))){
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(clean);
                    System.out.println(Joueur.buffer("res/victoire.txt"));
                    TimeUnit.SECONDS.sleep(3);
                }
            }
        }
        yggdrasil(j);
    }
    
    public static void helheim(Joueur j) throws IOException, InterruptedException{
        System.out.println("Vous prenez le portail vers Helheim.. Le royaume des morts");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("La région est froide et brumeuse, des âmes vagabondent...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Un loup blanc surgit d'un nuage, il vous observe puis vous attaque !");
        TimeUnit.SECONDS.sleep(2);
        ArrayList<Attack> a = new ArrayList<>();
        a.add(new Attack("Iceberg", Status.GLACE, 10));
        a.add(new Attack("Crocs glace", Status.GLACE, 5));
        if(combat(j, new Mob(new Weapon("Epée de la louve", a), "Fils de Fenrìr", (int) (40*niveauDiff), 6, 6, "res/ascii-mob2.txt"))){
            TimeUnit.SECONDS.sleep(2);
            System.out.println(clean);
            System.out.println("Vous apprenez que le loup est un des fils de Fenrìr, gardien de Hel");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Vous suivez les âmes et arrivez au bout du chemin, Fenrìr vous y attend");
            TimeUnit.SECONDS.sleep(2);
            ArrayList<Attack> b = new ArrayList<>();
            b.add(new Attack("Blizzard de Hel", Status.GLACE, 30));
            b.add(new Attack("Anti-chambre", Status.GLACE, 5));
            b.add(new Attack("Morsure de glace", Status.GLACE, 20));
            if(combat(j, new Boss("Fenrìr, gardien de Hel", new Weapon("Hache Léviathan", b),  "res/ascii-boss1.txt", 4 , (int) (70*niveauDiff), 7, 7))){
                System.out.println("Fenrìr n'est plus, le royaume de Hel n'a plus de gardien. Les âmes sont ainsi libérées.");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(clean);
                System.out.println(Joueur.buffer("res/victoire.txt"));
                TimeUnit.SECONDS.sleep(3);
            }
        }
        yggdrasil(j);
    }

    public static void main(String[] args) throws InterruptedException, IOException, NameAlreadyExists {
        boolean find = false;
        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        int choix = 0;

        //test
        //pp


        while(!find){
            System.out.println(clean);
            MenuPrincipale();

            choix = s.nextInt();
            if(choix==2 || choix ==1){
                find = true;
            }
        
        }
        if (choix == 1){
            LancementPartie();
        }

        if(choix == 2){
            System.out.println("Entrez le nom de votre personnage");
            String nom = s2.nextLine();
            Joueur j = Joueur.charger(nom);
            niveauDiff = menuLVL();
            yggdrasil(j);

        }
    }
}

