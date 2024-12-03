class GenerateurProduit extends Program {
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

    void algorithm() {
        String nomFichier = argument(0);
        if(fileExist(nomFichier)){
            String texte = fileAsString(nomFichier);
            String nom = rechercherValeur(texte, "nom");
            String entreprise = rechercherValeur(texte, "entreprise");
            String prix = rechercherValeur(texte, "prix");
            String date = rechercherValeur(texte, "date");
            String description = rechercherValeur(texte, "description");
            println("<!DOCTYPE html>\n<html lang=\"fr\">\n  <head>\n    <title>" + nom + "</title>\n    <meta charset=\"utf-8\">\n  </head>\n  <body>\n    <h1>" + nom + " (" + entreprise + ")</h1>\n    <h2>" + prix + " (Sortie en " + date + ")</h2>\n    <p>\n" + description + "\n    </p>\n  </body>\n</html>");

        }
        else{
            error("Le fichier n'existe pas !");
        }
    }
}
