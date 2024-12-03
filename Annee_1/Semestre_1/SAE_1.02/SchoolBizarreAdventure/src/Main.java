import extensions.CSVFile;
import extensions.File;

class Main extends Program {

    final String ESPACE_AFFICHAGE = "               |";
    final String FIN_AFFICHAGE = "                ________________________________________________________________________________________________________________________________________________________________\n";

    //<<<Tests>>>

    void testTrierScore(){
        String[][] pseudos = new String[][]{{"Pablo","5"},{"Henry","8"},{"Marc","2"}};
        String[][] tri = new String[][]{{"Henry","8"},{"Pablo" , "5"},{"Marc","2"}};

        
        // Appel de la fonction à tester
        pseudos = trierScore(pseudos);
        
         
        // Comparaison avec le tableau trié attendu
        for(int i = 0;i<length(pseudos,1);i++){
            for(int y =0;y<length(pseudos,2);y++){
                assertEquals(tri[i][y],pseudos[i][y]);
            }
        }
    }

    void testpseudoUtilisé(){
        assertEquals(true,pseudoUtilisé(randomPseudo()));
        assertEquals(false,pseudoUtilisé("Pierre Henry Paul Marc Michel Jean"));
    }

    void testThemeValide(){
        assertEquals(true,themeValide(randomTheme().nom));
        assertEquals(false,themeValide("azertyuiop"));
    }





















    //<<<Affichage>>>
    //Affichage de la partie supérieure de l'ATH
    void afficherATH(int score, int vie){
        String barreVie = "";
        for(int i=0; i<vie; i++){
            barreVie = barreVie + "❤";
        }
        println(FIN_AFFICHAGE);          
        println(ESPACE_AFFICHAGE + "     " +          ANSI_RED + barreVie + ANSI_RESET + "                                                                                                                                        SCORE : " + score);
        for (int i = 0; i<15; i++){
            println(ESPACE_AFFICHAGE);
        }
    }
    //Affichage de la partie inférieure de l'ATH
    void finATH(){
        for(int i = 0; i<10; i++){
            println(ESPACE_AFFICHAGE);
        }
        println(FIN_AFFICHAGE);
    }









    //<<<Fonctions utilitaires>>>

    //Chargement ASCII ART
    String readFile (String fichier) {
        extensions.File file = newFile(fichier);
        String res = "";
        for (int i = 0; i < 32; i++){
            res = res + readLine(file) + '\n';
        }
        return res;
    }
    //Fonction qui crée de la latence pour laisser un temps d'affichage
    void attendreTemps(int temps){
        long initTime = getTime();
        long res=0;
        while(getTime()- initTime < temps){
            res++;
        }
    }
    //Fonction qui empêche l'utilisateur d'inscrire un nombre non présent entre 2 bornes en paramètres
    int choixValide(int borneInf, int borneSup){
        String choix = readString();
        boolean fin = false;
        while(!fin){
            for (int i=borneInf; i<=borneSup; i++){
            String res = "" + i;
            if (equals(res, choix)){
                fin = true;
            }
            }
            if(!fin){
                println("Ce choix n'est pas proposé.");
                choix = readString();
            }
        }
        return stringToInt(choix);
    }













    //<<<Menu du logiciel>>>

    //Lancement de la partie
    void lancement(){
        clearScreen();
        String pseudo = "";
        menuConnexion(pseudo);
    }
    //Affichage du menu de connexion 
    void menuConnexion(String pseudo){
        println(readFile("res/ascii/1-.txt"));
        println(ANSI_RED + "                                                                            Connectez vous ou créez un nouveau compte" + ANSI_RESET);
        println(ANSI_YELLOW + "                                                                   1 - Se connecter                   2 - Créer un nouveau compte" + ANSI_RESET);
        int choix = choixValide(1,2);
        if(choix == 1){
            pseudo = connexionCompte();
        }else if(choix == 2){
            pseudo = creationCompte();
        }
        menuJeu(pseudo);
    }
    //Affichage du menu du jeu
    void menuJeu(String pseudo){
        clearScreen();
        println(readFile("res/ascii/1-.txt"));
        println(ANSI_RED + "                                                                                        Bienvenue " + pseudo + " !" + ANSI_RESET);
        println(ANSI_YELLOW + "1 - Lancer une partie                         2 - Leaderboard                         3 - Règles                         4 - Proposer une question                          5 - Se déconnecter" + ANSI_RESET);
        int choix = choixValide(1,5);
        if(choix == 1){
            Jeu(pseudo, choix);     
        }else if(choix == 2){
            afficherLeaderBoard(pseudo);
        }
        else if(choix == 3) {
            afficherRegles(pseudo);
        }else if(choix == 4){
            ajouterQuestion(pseudo);
        }else if(choix == 5){
            quitterPartie(pseudo);
        }
    }
    void quitterPartie(String pseudo){
        clearScreen();
        lancement();
    }
    //Fonction qui s'occupe d'afficher les règles contenue dans le README.md
    void afficherRegles(String pseudo){
        clearScreen();
        println(readFile("README.md"));
        println(ANSI_RED + "                                                                                        Tapez 1 pour retourner au menu" + ANSI_RESET);
        int choix = choixValide(1,1);
        if (choix == 1){
            menuJeu(pseudo);
        }
    }











    //<<<Pseudos>>>

    //charge les pseudos dans un tableau de String avec leurs scores respectifs
    String[][] chargerPseudo(){
        CSVFile fichier = loadCSV("res/Pseudo.csv",',');
        String[][] Pseudo = new String[rowCount(fichier)][2];
        for(int i =0;i<rowCount(fichier);i++){
            Pseudo [i][0]= getCell(fichier,i,0);
            Pseudo [i][1]= getCell(fichier,i,1);
        }
        return Pseudo;
    }

    //teste si le pseudo est trouvé dans le fichier pseudo.csv et donc s'il existe déjà
    boolean pseudoUtilisé(String s){
        String[][] Pseudo = chargerPseudo();
        int cpt = 0;
        while(cpt<length(Pseudo,1)){
            if(equals(toLowerCase(Pseudo[cpt][0]),toLowerCase(s))){
                return true;
            }
            cpt++;
        }
        return false;
    }
    //Fonction qui ajoute un nouveau pseudo ainsi qu'un score à 0 dans le fichier Pseudo.csv
    void ajouterCompte(String pseudo){

        String[][] pseudos = chargerPseudo();
        String[][] newPseudos = new String[length(pseudos,1)+1][2];
        for(int i = 0;i<length(pseudos);i++){
            newPseudos[i][0] = pseudos[i][0];
            newPseudos[i][1] = pseudos[i][1];
        }
        newPseudos[length(newPseudos)-1][0] = pseudo;
        newPseudos[length(newPseudos)-1][1] = 0 + "";
        saveCSV(newPseudos,"res/Pseudo.csv");
    }
    //Fonction qui choisi un pseudo au hasard
    String randomPseudo(){
        String[][] pseudos = chargerPseudo();
        int rand = (int) (random()*length(pseudos,1));
        return pseudos[rand][0];
    }
    //Portail de connexion au logiciel
    String connexionCompte(){
        String pseudo = demandePseudo();
        while(!pseudoUtilisé(pseudo)){
            println(ANSI_RED + "                                                                                        Le compte est introuvable" + ANSI_RESET);
            attendreTemps(1500);
            pseudo = readString();
        }
        return pseudo;
    }
    //Fonction qui s'occupe de l'affichage de demande de pseudo
    String demandePseudo(){
        clearScreen();
        println(readFile("res/ascii/1-.txt"));
        println(ANSI_RED + "                                                                                        Entrez votre pseudo" + ANSI_RESET);
        String pseudo = readString();
        return pseudo;
    }
    //Fonction qui permet de créer un compte
    String creationCompte(){
        String pseudo = demandePseudo();
        if(pseudoUtilisé(pseudo)){
            println(ANSI_RED + "                                                                                        Ce pseudo est déjà utilisé \n                                                                                          Essayez en un autre" + ANSI_RESET);
            attendreTemps(1500);
            pseudo = readString();
        }
        println("Création du compte");
        attendreTemps(1500);
        ajouterCompte(pseudo);
        return pseudo;
    }


















    //<<<Score>>>

    //MAJ du score dans res/Pseudos.csv
    void majScore(String pseudo, int score){
        String[][] pseudos = chargerPseudo();
        String[][] newPseudos = new String[length(pseudos,1)][2];
        for(int i = 0;i<length(pseudos);i++){
            if (equals(pseudo, pseudos[i][0])){
                newPseudos[i][0]= pseudos[i][0];
                if (score>stringToInt(pseudos[i][1])){
                    String res = "" + score;
                    newPseudos[i][1] = res;    
                } else {
                    newPseudos[i][0] = pseudos[i][0];
                    newPseudos[i][1] = pseudos[i][1];
                }
            } else {
                newPseudos[i][0] = pseudos[i][0];
                newPseudos[i][1] = pseudos[i][1];
            }
        }
        saveCSV(newPseudos,"res/Pseudo.csv");
    }
    //Affichage du LeaderBoard avec les différents scores des joueurs
    void afficherLeaderBoard(String pseudo){
        clearScreen();
        String[][] pseudos = trierScore(chargerPseudo());
        print(FIN_AFFICHAGE);
        for(int i =0;i<length(pseudos,1);i++){
            println(ESPACE_AFFICHAGE + "                 " + ANSI_RED + (i+1) +" - " + pseudos[i][0] + " : " + pseudos[i][1] + ANSI_RESET);
        }
        println(FIN_AFFICHAGE);
        println(ANSI_RED + "                                                                                        Tapez 1 pour retourner au menu" + ANSI_RESET);
        int choix = choixValide(1,1);
        if (choix == 1){
            menuJeu(pseudo);
        }
    }
    //Fonction qui trie tous les scores 
    String[][] trierScore(String[][]pseudos){
        boolean permutationFin = false;
        while(!permutationFin){
            permutationFin = true;
            for(int i = 0;i < length(pseudos,1)-1;i++){
                if(stringToInt(pseudos[i][1]) < stringToInt(pseudos[i+1][1])){
                    String tmp = pseudos[i][1];
                    pseudos[i][1] = pseudos[i+1][1];
                    pseudos[i+1][1] = tmp;
                    tmp = pseudos[i][0];
                    pseudos[i][0] = pseudos[i+1][0];
                    pseudos[i+1][0] = tmp;
                    permutationFin=false;
                }
            }
        }
        return pseudos;
    }



















    //<<<Ajout CSV>>>

    // Fonction retournant true si le nom du thème passé en paramètre existe dans le répertoire res/themes
    boolean themeValide(String nomTheme){
        if(idxTheme(nomTheme) >=0){
            return true;
        }else {
        return false;
        }
    }

    // Fonction retournant l'indice du nom du fichier dans le tableau des noms de fichiers
    int idxTheme(String nomTheme){
        String[] nomFich = getAllFilesFromDirectory("res/themes");
        for(int i = 0;i<length(nomFich);i++){
            if(equals(nomFich[i],nomTheme+".csv")){
                return i;
            }
        }
        return -1;
    }

    // Fonction permettant d'ajouter une question dans les fichiers csv dans le répertoire res/themes
    void ajouterQuestion(String pseudo){
        String nomTheme = "";
        String[] nomFich = getAllFilesFromDirectory("res/themes");
        String listeTheme = "";
        for (int i=0; i<length(nomFich);i++){
            listeTheme = listeTheme + substring(nomFich[i], 0, length(nomFich[i])-4) + ", ";
        }
        clearScreen();
        while(!themeValide(nomTheme)){
            println(ANSI_YELLOW + "                                                    Choisissez le thème parmi : " + substring(listeTheme, 0, length(listeTheme)-2) + ANSI_RESET);
            nomTheme = readString();
        }
        int idxT = idxTheme(nomTheme);
        CSVFile file = loadCSV("res/themes/" +nomFich[idxT],',');
        String[][] Questions = new String[rowCount(file)+1][3];
        for(int y = 0;y<length(Questions,1)-1;y++){
            Questions[y][0] = getCell(file,y,0);
            Questions[y][1] = getCell(file,y,1);
            Questions[y][2] = getCell(file,y,2);
        }
        println("Choisissez la difficulté : (1<2<3))");
        int diff = choixValide(1,3);
        println("Question :");
        String question = readString();
        println("Réponse :");
        String reponse = readString();
        clearScreen();
        println(ANSI_RED + "                                                                                        Récapitulatif : \n\n\n                                                                                        Difficulté : " + diff + " \n                                                                                        Question : " + question + "\n                                                                                        Réponse : " + reponse + "\n\n\n\n\n\n\n\n\n\n\n");
        println("                                     Tapez \"1\" pour valider et retourner au menu ou tapez autre chose pour retourner au menu sans soumettre votre question" + ANSI_RESET);
        String choix = readString();
        if(equals(choix, "1")){
            Questions[length(Questions,1)-1][0] = "" + diff;
            Questions[length(Questions,1)-1][1] = question;
            Questions[length(Questions,1)-1][2] = reponse;
            saveCSV(Questions,"res/themes/" + nomFich[idxT]);
            clearScreen();
            println(ANSI_RED + "                                                                           Merci de contribuer à School Bizarre Adventure !\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + ANSI_RESET);
            attendreTemps(3000);
        }
        menuJeu(pseudo);
    }














    //<<<Theme>>>

    Theme newTheme(String nom,String[][]Questions){
        Theme theme = new Theme();
        theme.nom = nom;
        theme.Questions = Questions;
        return theme;
    }
    Theme[] chargerThemes(){
        String[] nomFich = getAllFilesFromDirectory("res/themes");
        Theme[] Themes= new Theme[length(nomFich)];
        for(int i =0;i<length(nomFich);i++){
            Theme theme;
            CSVFile file = loadCSV("res/themes/" +nomFich[i],',');
            String[][] Questions = new String[rowCount(file)][3];
            for(int y = 0;y<length(Questions,1);y++){
                Questions[y][0] = getCell(file,y,0);
                Questions[y][1] = getCell(file,y,1);
                Questions[y][2] = getCell(file,y,2);
            }
            theme = newTheme(substring(nomFich[i],0,length(nomFich[i])-4),Questions);
            Themes[i] = theme;
        }
        return Themes;
    }
    //Fonction qui sort un theme au hasard parmi les différents thèmes disponibles dans le dossier res/themes/
    Theme randomTheme(){
        Theme[] themes = chargerThemes();
        int alea = (int) (random()*(length(themes)));
        return themes[alea];
    }





















    //<<<Joueurs & Jeu>>>
    
    //Créer un nouveau joueur
    Player newPlayer(String nom){
        Player p = new Player();
        p.nom = nom;
        p.score = 0;
        p.vie = 5;
        p.tour = 1;
        return p;
    }

    //Fonction qui prend en compte le thème voulu par l'utilisateur, sort une question et analyse la réponse pour la comparer.
    Player affichageQuestion(Player p, Theme theme, int choix, int LimiteQuestions){
        for (int NombreQuestions = 0; NombreQuestions<LimiteQuestions; NombreQuestions = NombreQuestions + 1){
            if(p.vie > 0){
                afficherATH(p.score, p.vie);
                int idxQuestions = (int) (random()*length(theme.Questions, 1));
                println(ESPACE_AFFICHAGE + "     " + theme.Questions[idxQuestions][1]);
                finATH();
                String reponse = readString();
                clearScreen();
                if (equals(toLowerCase(reponse), toLowerCase(theme.Questions[idxQuestions][2]))){
                    p.score = p.score + (p.tour*stringToInt(theme.Questions[idxQuestions][0]))*100;
                    afficherATH(p.score, p.vie);
                    println(ESPACE_AFFICHAGE + "     Bravo");
                    finATH();
                    attendreTemps(1500);
                    clearScreen();
                } else {
                    p.vie = p.vie - 1;
                    afficherATH(p.score, p.vie);
                    println(ESPACE_AFFICHAGE + "     Trop bête la loose, la réponse était : " + theme.Questions[idxQuestions][2]);
                    finATH();
                    attendreTemps(1500);
                    clearScreen();
                }
                p.tour++;
                }
            }
        clearScreen();
        return p;
    }
    //Fonction qui s'occupe du chargement de lancement de partie
    void lancementPartie(){
        clearScreen();
        println(readFile("res/ascii/1-.txt"));
        println(ANSI_YELLOW + "                                                                            Lancement d'une nouvelle partie...\n\n" + ANSI_RESET);
        attendreTemps(1500);
    }
    //Fonction qui fait les combats de base
    Player jouerCombat(Player p, int choix){
        int tour = 0;
        clearScreen();
        while(p.vie > 0 && tour<3){
            Theme Theme1 = randomTheme();
            Theme Theme2 = randomTheme();
            afficherATH(p.score, p.vie);
            println(ESPACE_AFFICHAGE + "     Choisissez votre thème parmi 1 - " + Theme1.nom + " ou 2 - " + Theme2.nom);
            finATH();
            choix = choixValide(1,2);
            clearScreen();
            if (choix==1){
                p = affichageQuestion(p, Theme1, choix, 3);           
            }
            if (choix==2){
                p = affichageQuestion(p, Theme2, choix, 3);
            }
            tour++;
            clearScreen();
        }
        return p;
    }
    //Fonction qui fait affronter un Champion
    Player jouerChampion(Player p, int choix){
        if(p.vie > 0){
            clearScreen();
            afficherATH(p.score, p.vie);
            println(ESPACE_AFFICHAGE + "     Félicitations vous êtes arrivé face aux Champions, préparez-vous !");
            println(ESPACE_AFFICHAGE + "     Vous affronterez le Champion " + randomPseudo());
            finATH();
            attendreTemps(4000);
            int tour = 0;
            while(tour < 10 && p.vie > 0){
                Theme theme = randomTheme();
                p = affichageQuestion(p, theme, choix, 1);
                tour ++;
            }
            if(p.vie > 0){
                afficherATH(p.score, p.vie);
                println(ESPACE_AFFICHAGE + "     Félicitations vous avez vaincu l'un des Champions, cependant ce n'est pas fini !"); 
                finATH();
                attendreTemps(1500);
            }
        }   
        return p;
    }
    //Fonction prinipale du jeu 
    void Jeu(String pseudo, int choix){
        Player player = newPlayer(pseudo);
        lancementPartie();
        while (player.vie>0){
            player = jouerCombat(player, choix);
            player = jouerChampion(player, choix);
            clearScreen();
        }
        afficherATH(player.score, player.vie);
        println(ESPACE_AFFICHAGE + "     " + pseudo + " ne peut plus se battre ! \n" + ESPACE_AFFICHAGE + "     Vous avez marqué un score de " + player.score);
        finATH();
        attendreTemps(6000);
        majScore(pseudo, player.score);
        menuJeu(pseudo);
    }









    //<<<Algorithme principal>>>
    void algorithm(){
        lancement();
    }
}   
