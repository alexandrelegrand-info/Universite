class GenerateurSite extends Program {
    final char NEW_LINE = '\n';
    final int IDX_NOM = 0;
    final int IDX_DATE = 1;
    final int IDX_ENTREPRISE = 2;
    final int IDX_PRIX = 3;
    final int IDX_DESCRIPTION = 4;

    String rechercherValeur(String texte, String key){
        int i = 0;
        while(i < length(texte) && !equals(substring(texte, i, i + length(key)), key)){
            i=i+1;
        }
        int j = i;
        while(j < length(texte) && charAt(texte, j) != '\n'){
            j=j+1;
        }
        String chaine = substring(texte, i + length(key) + 3, j);
        return chaine;
    }

    String AfficherSection(int Indice) {
        String section = "";
        String nomFichier = "";
        nomFichier="data/produit" + Indice + ".txt";
        println(nomFichier);
        if(fileExist(nomFichier)){
            String texte = fileAsString(nomFichier);
            String nom = rechercherValeur(texte, "nom");
            String entreprise = rechercherValeur(texte, "entreprise");
            String prix = rechercherValeur(texte, "prix");
            String date = rechercherValeur(texte, "date");
            String description = rechercherValeur(texte, "description");
            section="        <h2>" + nom + " " + "(" + entreprise + ")" + "</h2>\n        <h3>" + prix + " (Sortie en " + date + ")</h3>\n        <p>\n" + description + "\n        </p>\n";

        }
        else{
            error("Le fichier n'existe pas !");
        }
        return section;
    }

    String[][] chargerProduits(String repertoire, String prefixe) {
      String[] AllProduits = getAllFilesFromDirectory(repertoire);
      String[][] Produit = new String [length(AllProduits)+1][5];
      String nomFichier = "";
      Produit[0] = new String[] {"NOM", "DATE", "ENTREPRISE", "PRIX", "DESCRIPTION"};
      for (int i = 1; i<=length(AllProduits); i++){
        nomFichier= repertoire + "/" + prefixe + i + ".txt";
        String texte = fileAsString(nomFichier);
        String Nom = rechercherValeur(texte, "nom");
        String Date = rechercherValeur(texte, "date");
        String Entreprise = rechercherValeur(texte, "entreprise");
        String Prix = rechercherValeur(texte, "prix");
        String Description = rechercherValeur(texte, "description");
        Produit [i][IDX_NOM] = Nom ;
        Produit [i][IDX_DATE] = Date ;
        Produit [i][IDX_ENTREPRISE] = Entreprise ;
        Produit [i][IDX_PRIX] = Prix ;
        Produit [i][IDX_DESCRIPTION] = Description ;
      }
      return Produit; 
    }

    String genererProduit(String [][] produits, int i){
      String produit1 = "";
      String produit2 = "";
      String produit3 = "";
      String produit4 = "";
      String produit5 = "";
      println(i);
      if (i<length(produits, 1)){
        produit5="        <li><a href=\"produit"+ (i) +".html\">" + produits[i][0] +"</a></li>" + NEW_LINE;
        if (i<length(produits, 1)-1){
          produit4="        <li><a href=\"produit"+ (i+1) +".html\">"+ produits[i+1][0] +"</a></li>" + NEW_LINE;
          if (i<length(produits, 1)-2){
            produit3="        <li><a href=\"produit"+ (i+2) +".html\">"+ produits[i+2][0] +"</a></li>" + NEW_LINE;
            if (i<length(produits, 1)-3){
              produit2="        <li><a href=\"produit"+ (i+3) +".html\">"+ produits[i+3][0] +"</a></li>" + NEW_LINE;
              if (i<length(produits, 1)-4){
                produit1="        <li><a href=\"produit"+ (i+4) +".html\">" + produits[i+4][0] + "</a></li>" + NEW_LINE;
              }
            }
          }
        }
      }
      String page="<!DOCTYPE html>\n<html lang=\"fr\">" + NEW_LINE +
      "  <head>" + NEW_LINE +
      "    <title>Ordinateurs mythiques</title>" + NEW_LINE + 
      "    <meta charset=\"utf-8\">" + NEW_LINE +
      "    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">"+ NEW_LINE +
      "  </head>" + NEW_LINE + 
      "  <body>"+ NEW_LINE +
      "    <header>"+ NEW_LINE +
      "      <h1>Ordinateurs mythiques</h1>"+ NEW_LINE +
      "    </header>"+ NEW_LINE +
      "    <nav>"+ NEW_LINE +
      "      <ul>"+ NEW_LINE +
      "        <li><a href=\"index.html\">Accueil</a></li>"+ NEW_LINE +
      produit5 + 
      produit4 + 
      produit3 + 
      produit2 + 
      produit1 +               
      "      </ul>" + NEW_LINE + 
      "    </nav>" + NEW_LINE + 
      "    <main>" + NEW_LINE + 
      "      <section>" + NEW_LINE + 
      "        <h2>" + produits[i][0] + " (" + produits[i][2] + ")</h2>" + NEW_LINE + 
      "        <h3>" + produits[i][3] + " (Sortie en " + produits[i][1] + ")</h3>" + NEW_LINE + 
      "        <p>" + NEW_LINE + 
      produits[i][4] + NEW_LINE + 
      "        </p>" + NEW_LINE + 
      "      </section>" + NEW_LINE + 
      "    </main>" + NEW_LINE + 
      "  </body>" + NEW_LINE +
      "</html>" + NEW_LINE;
      return page; 
    }
    String ecritureIndex(String[][] produits ,int i){
      String produit1 = "";
      String produit2 = "";
      String produit3 = "";
      String produit4 = "";
      String produit5 = "";
      if (0<length(produits, 1)){
        produit5="        <li><a href=\"produit"+ (i) +".html\">" + produits[i][0] +"</a></li>" + NEW_LINE;
        if (1<length(produits, 1)){
          produit4="        <li><a href=\"produit"+ (i+1) +".html\">"+ produits[i+1][0] +"</a></li>" + NEW_LINE;
          if (2<length(produits, 1)){
            produit3="        <li><a href=\"produit"+ (i+2) +".html\">"+ produits[i+2][0] +"</a></li>" + NEW_LINE;
            if (3<length(produits, 1)){
              produit2="        <li><a href=\"produit"+ (i+3) +".html\">"+ produits[i+3][0] +"</a></li>" + NEW_LINE;
              if (4<length(produits, 1)){
                produit1="        <li><a href=\"produit"+ (i+4) +".html\">" + produits[i+4][0] + "</a></li>" + NEW_LINE;
              }
            }
          }
        }
      }
      String resultat="<!DOCTYPE html>"+ NEW_LINE + 
      "<html lang=\"fr\">"+ NEW_LINE + 
      "  <head>"+ NEW_LINE + 
      "    <title>Ordinateurs mythiques</title>"+ NEW_LINE + 
      "    <meta charset=\"utf-8\">"+ NEW_LINE + 
      "    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">"+ NEW_LINE + 
      "  </head>"+ NEW_LINE + 
      "  <body>"+ NEW_LINE + 
      "    <header>"+ NEW_LINE + 
      "      <h1>Ordinateurs mythiques</h1>"+ NEW_LINE + 
      "    </header>"+ NEW_LINE + 
      "    <nav>"+ NEW_LINE + 
      "      <ul>"+ NEW_LINE + 
      "        <li><a href=\"index.html\">Accueil</a></li>"+ NEW_LINE + 
      produit5 + 
      produit4 + 
      produit3 + 
      produit2 + 
      produit1 +               
      "      </ul>"+ NEW_LINE + 
      "    </nav>"+ NEW_LINE + 
      "    <main>"+ NEW_LINE + 
      "      <section>"+ NEW_LINE + 
      "        <h2>Tout ce que vous avez toujours voulu savoir sur les vieux ordis sans jamais avoir osé le demander !</h2>"+ NEW_LINE + 
      "          <p>"+ NEW_LINE + 
      "Bienvenue dans le musée virtuel d'ordinateurs mythiques de l'histoire de l'informatique. Vous trouverez ici des éléments sur quelques machines qui ont marqué l'histoire de l'informatique que cela soit par leurs caractéristiques techniques ou l'impact commercial qu'elles ont eu et qui ont contribué au développement du secteur informatique."+ NEW_LINE + 
      "          </p>"+ NEW_LINE + 
      "      </section>"+ NEW_LINE + 
      "    </main>"+ NEW_LINE + "  </body>"+ NEW_LINE + 
      "</html>"+ NEW_LINE + NEW_LINE;
      return resultat;
    }
    void algorithm(){
      int i = 1;
      stringAsFile("output/index.html", ecritureIndex(chargerProduits("data", "produit"), i));
      while(i<=30){
        stringAsFile("output/produit" + i + ".html", genererProduit(chargerProduits("data", "produit"), i));
        i++;
      }
    }
}
