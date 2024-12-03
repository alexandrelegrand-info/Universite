class GenerateurSite extends Program {
    final char NEW_LINE = '\n';
    final int IDX_NOM = 0;
    final int IDX_DATE = 1;
    final int IDX_ENTREPRISE = 2;
    final int IDX_PRIX = 3;
    final int IDX_DESCRIPTION = 4;

    void testPermuterLignes() {
      String[][] t = new String[][]{{"a", "b", "c"},
                                    {"D", "E", "F"}};
      permuterLignes(t, 0, 1);
      assertArrayEquals(new String[][]{{"D", "E", "F"},
                                       {"a", "b", "c"}}, t);
    }
    void testTrierSurColonne() {
    String[][] t = new String[][]{{"a1", "b3", "c2"},
                                  {"a3", "b2", "c3"},
                                  {"a2", "b1", "c1"}};
    trierSurColonne(t, 0); // on trie sur la première colonne
    assertArrayEquals(new String[][]{{"a1", "b3", "c2"},
                                     {"a2", "b1", "c1"},
                                     {"a3", "b2", "c3"}}, t);
    trierSurColonne(t, 1); // on trie sur la deuxième colonne
    assertArrayEquals(new String[][]{{"a2", "b1", "c1"},
                                     {"a3", "b2", "c3"},
                                     {"a1", "b3", "c2"}}, t);
    trierSurColonne(t, 2); // on trie sur la troisième colonne
    assertArrayEquals(new String[][]{{"a2", "b1", "c1"},
                                     {"a1", "b3", "c2"},
                                     {"a3", "b2", "c3"}}, t);
    }
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
      String[] ALLPRODUITS = getAllFilesFromDirectory(repertoire);
      String[][] Produit = new String [length(ALLPRODUITS)+1][5];
      String nomFichier = "";
      for (int i = 0; i<length(ALLPRODUITS); i++){
        nomFichier= repertoire + "/" + prefixe + (i+1) + ".txt";
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
        produit5="        <li><a href=\"produit"+ (i) +".html\">" + produits[i-1][0] +"</a></li>" + NEW_LINE;
        if (i<length(produits, 1)-1){
          produit4="        <li><a href=\"produit"+ (i+1) +".html\">"+ produits[i][0] +"</a></li>" + NEW_LINE;
          if (i<length(produits, 1)-2){
            produit3="        <li><a href=\"produit"+ (i+2) +".html\">"+ produits[i+1][0] +"</a></li>" + NEW_LINE;
            if (i<length(produits, 1)-3){
              produit2="        <li><a href=\"produit"+ (i+3) +".html\">"+ produits[i+2][0] +"</a></li>" + NEW_LINE;
              if (i<length(produits, 1)-4){
                produit1="        <li><a href=\"produit"+ (i+4) +".html\">" + produits[i+3][0] + "</a></li>" + NEW_LINE;
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
      produit1 + NEW_LINE +
      "<li><a href=\"produits-nom.html\">Produits</a></li>" + NEW_LINE +              
      "      </ul>" + NEW_LINE + 
      "    </nav>" + NEW_LINE + 
      "    <main>" + NEW_LINE + 
      "      <section>" + NEW_LINE + 
      "        <h2>" + produits[i-1][IDX_NOM] + " (" + produits[i-1][IDX_ENTREPRISE] + ")</h2>" + NEW_LINE + 
      "        <h3>" + produits[i-1][IDX_PRIX] + " (Sortie en " + produits[i-1][IDX_DATE] + ")</h3>" + NEW_LINE + 
      "        <p>" + NEW_LINE + 
      produits[i-1][IDX_DESCRIPTION] + NEW_LINE + 
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
        produit5="        <li><a href=\"produit"+ (i) +".html\">" + produits[i-1][0] +"</a></li>" + NEW_LINE;
        if (1<length(produits, 1)){
          produit4="        <li><a href=\"produit"+ (i+1) +".html\">"+ produits[i][0] +"</a></li>" + NEW_LINE;
          if (2<length(produits, 1)){
            produit3="        <li><a href=\"produit"+ (i+2) +".html\">"+ produits[i+1][0] +"</a></li>" + NEW_LINE;
            if (3<length(produits, 1)){
              produit2="        <li><a href=\"produit"+ (i+3) +".html\">"+ produits[i+2][0] +"</a></li>" + NEW_LINE;
              if (4<length(produits, 1)){
                produit1="        <li><a href=\"produit"+ (i+4) +".html\">" + produits[i+3][0] + "</a></li>" + NEW_LINE;
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
      produit1 + NEW_LINE +
      "<li><a href=\"produits-nom.html\">Produits</a></li>" + NEW_LINE +
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
    String[][] permuterLignes(String [][] produits, int c0, int c1){
      for (int i=0; i<length(produits, 2); i++){
        String produit = produits[c0][i];
        produits[c0][i]=produits[c1][i];
        produits[c1][i]=produit;
      }
      return produits;
    }
    void trierSurColonne(String[][] produits, int IndexTri){
      int borneMax = length((produits),1)-1;
      boolean permutation = true;
      while (permutation){
        permutation = false;
        for (int i=0; i<borneMax-1; i++){
          if (compare(produits[i][IndexTri], produits[i+1][IndexTri])>0){
            permuterLignes(produits,i, i+1);
            permutation=true;
          }
          if (IndexTri==2){
            if(compare(produits[i][IndexTri], produits[i+1][IndexTri])==0){
              if (compare(produits[i][IDX_DATE], produits[i+1][IDX_DATE])>0){
                permuterLignes(produits, i, i+1);
                permutation=true;
              }
            }
            else if(compare(produits[i][IndexTri], produits[i+1][IndexTri])==0){
              if (compare(produits[i][IDX_NOM], produits[i+1][IDX_NOM])>0){
                permuterLignes(produits, i, i+1);
                permutation=true;
              }
            }
          }
          if (IndexTri==1){
            if(compare(produits[i][IndexTri], produits[i+1][IndexTri])==0){
              if (compare(produits[i][IDX_NOM], produits[i+1][IDX_NOM])>0){
                permuterLignes(produits, i, i+1);
                permutation=true;
              }
            }
          } 
        }
        borneMax--;
      }
    }
    String CorpsTrié(String[][] produits){ 
      String resultat = "            <table>" + NEW_LINE;
      for (int i = 0; i<length(produits, 1)-1; i++){
        resultat = resultat + 
        "              <tr>" + NEW_LINE +
        "                <td>" + produits[i][IDX_NOM] + "</td><td>" + produits[i][IDX_DATE] + "</td><td>" + produits[i][IDX_PRIX] + "</td><td>" + produits[i][IDX_ENTREPRISE] + "</td><td>" + produits[i][IDX_DESCRIPTION] + "</td>" + NEW_LINE +
        "              </tr>" + NEW_LINE;
      }
      resultat = resultat + "            </table>" + NEW_LINE;
      return resultat;
    }
    String ecritureProduitTri(String[][] produits, int tri){
      trierSurColonne(produits, tri);
      String resultat = "<!DOCTYPE html>" + NEW_LINE +
      "<html lang=\"fr\">" + NEW_LINE +
      "  <head>" + NEW_LINE +
      "    <title>Ordinateurs mythiques</title>" + NEW_LINE +
      "    <meta charset=\"utf-8\">" + NEW_LINE +
      "    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">" + NEW_LINE +
      "  </head>" + NEW_LINE +
      "  <body>" + NEW_LINE +
      "    <header>" + NEW_LINE +
      "      <h1>Ordinateurs mythiques</h1>" + NEW_LINE +
      "    </header>" + NEW_LINE +
      "    <nav>" + NEW_LINE +
      "      <ul>" + NEW_LINE +
      "        <li><a href=\"index.html\">Accueil</a></li>" + NEW_LINE +
      "<li><a href=\"produits-nom.html\">Produits</a></li>" + NEW_LINE +
      "      </ul>" + NEW_LINE +
      "    </nav>" + NEW_LINE +
      "    <main>" + NEW_LINE +
      "      <section>" + NEW_LINE +
      "        <h2>Liste de l'ensemble des ordinateurs</h2>" + NEW_LINE +
      "          <p>" + NEW_LINE +
      "Trier sur : <a href=\"produits-nom.html\">NOM</a>, <a href=\"produits-date.html\">DATE</a>, <a href=\"produits-prix.html\">PRIX</a>, <a href=\"produits-entreprise.html\">ENTREPRISE</a>, <a href=\"produits-description.html\">DESCRIPTION</a>." + NEW_LINE +
      CorpsTrié(produits) +
      "          </p>" + NEW_LINE +
      "      </section>" + NEW_LINE +
      "    </main>" + NEW_LINE +
      "  </body>" + NEW_LINE +
      "</html>" + NEW_LINE +
      NEW_LINE;
      return resultat;
    }


    void algorithm(){
      int i = 1;
      String[][] produits = chargerProduits("data", "produit");
      stringAsFile("output/index.html", ecritureIndex(produits, i));
      while(i<length(produits, 1)){
        stringAsFile("output/produit" + i + ".html", genererProduit(produits, i));
        i++;
      }
      stringAsFile("output/produits-date.html", ecritureProduitTri(produits, 1));
      stringAsFile("output/produits-description.html", ecritureProduitTri(produits, 4));
      stringAsFile("output/produits-entreprise.html", ecritureProduitTri(produits, 2));
      stringAsFile("output/produits-nom.html", ecritureProduitTri(produits, 0));
      stringAsFile("output/produits-prix.html", ecritureProduitTri(produits, 3));
    }
}
