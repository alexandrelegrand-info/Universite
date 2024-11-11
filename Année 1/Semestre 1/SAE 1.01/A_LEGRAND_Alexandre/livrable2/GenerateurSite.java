class GenerateurSite extends Program {
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

    String ecritureProduit(int i){
      String resultat="<!DOCTYPE html>\n<html lang=\"fr\">\n  <head>\n    <title>Ordinateurs mythiques</title>\n    <meta charset=\"utf-8\">\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\n  </head>\n  <body>\n    <header>\n      <h1>Ordinateurs mythiques</h1>\n    </header>\n    <nav>\n      <ul>\n        <li><a href=\"index.html\">Accueil</a></li>\n        <li><a href=\"produit1.html\">Produit 1</a></li>\n        <li><a href=\"produit2.html\">Produit 2</a></li>\n        <li><a href=\"produit3.html\">Produit 3</a></li>\n        <li><a href=\"produit4.html\">Produit 4</a></li>\n        <li><a href=\"produit5.html\">Produit 5</a></li>\n      </ul>\n    </nav>\n    <main>\n      <section>\n" + AfficherSection(i) + "      </section>\n    </main>\n  </body>\n</html>";
      return resultat;
    }
    String ecritureIndex(){
      String resultat="<!DOCTYPE html>\n<html lang=\"fr\">\n  <head>\n    <title>Ordinateurs mythiques</title>\n    <meta charset=\"utf-8\">\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\n  </head>\n  <body>\n    <header>\n      <h1>Ordinateurs mythiques</h1>\n    </header>\n    <nav>\n      <ul>\n        <li><a href=\"index.html\">Accueil</a></li>\n        <li><a href=\"produit1.html\">Produit 1</a></li>\n        <li><a href=\"produit2.html\">Produit 2</a></li>\n        <li><a href=\"produit3.html\">Produit 3</a></li>\n        <li><a href=\"produit4.html\">Produit 4</a></li>\n        <li><a href=\"produit5.html\">Produit 5</a></li>\n      </ul>\n    </nav>\n    <main>\n      <section>\n        <h2>Tout ce que vous avez toujours voulu savoir sur les vieux ordis sans jamais avoir osé le demander !</h2>\n          <p>\nBienvenue dans le musée virtuel d'ordinateurs mythiques de l'histoire de l'informatique. Vous trouverez ici des éléments sur quelques machines qui ont marqué l'histoire de l'informatique que cela soit par leurs caractéristiques techniques ou l'impact commercial qu'elles ont eu et qui ont contribué au développement du secteur informatique.\n          </p>\n      </section>\n    </main>\n  </body>\n</html>\n";
      return resultat;
    }
    void algorithm(){
      stringAsFile("output/index.html", ecritureIndex());
      for (int i=1; i<6; i++){
        stringAsFile("output/produit" + i + ".html", ecritureProduit(i));
      }
    }
}
