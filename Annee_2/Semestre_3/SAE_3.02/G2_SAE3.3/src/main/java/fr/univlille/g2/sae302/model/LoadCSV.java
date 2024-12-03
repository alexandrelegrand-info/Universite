package fr.univlille.g2.sae302.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * La classe LoadCSV permet de charger, de vérifier et de lire des fichiers CSV.
 * Elle offre des méthodes pour récupérer la première ligne du fichier
 * et pour lire l'intégralité des données.
 * Elle gère également la mise à jour des bornes minimum et maximum
 * pour chaque colonne dans le cadre du traitement de données Iris.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class LoadCSV {

    /**
     * Lit la première ligne d'un fichier CSV donné et enregistre les colonnes comme clés
     * dans les Map minimum et maximum de la classe {@link Iris}, en initialisant
     * les valeurs minimales et maximales pour chaque colonne.
     * 
     * @param file le fichier CSV à lire
     * @return une ArrayList contenant les éléments de la première ligne du fichier
     * @throws IOException si une erreur survient lors de la lecture du fichier
     */
    public static ArrayList<String> premièreLigneCSV(File file, String separator) throws IOException {
        ArrayList<String> ligne1 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] chaine = reader.readLine().split(separator);
            if(chaine.length == 1) return null;
            for (String element : chaine) {
                element = element.replace("\"", "");
                ligne1.add(element);
                if(DataModel.minimum.get(element) == null || DataModel.minimum.get(element) == Double.MAX_VALUE) putMinAndMax(element);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ligne1;
    }

    private static void putMinAndMax(String element){
        DataModel.minimum.put(element, Double.MAX_VALUE);
        DataModel.maximum.put(element, Double.MIN_VALUE);
    }

    /**
     * Lit un fichier CSV et retourne les données sous forme de tableau à deux dimensions.
     * Ce tableau contient toutes les lignes du fichier sauf la première.
     * 
     * @param file le fichier CSV à lire
     * @return un tableau de deux dimensions de chaînes de caractères représentant les données du fichier
     * @throws IOException si une erreur survient lors de la lecture du fichier
     */
    public static String[][] lireFichierCSV(File file, String separator) throws IOException {
        int nbLigne = 0;
        int nbColonne = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] chaine = reader.readLine().split(separator);
            while (reader.readLine() != null) {
                nbLigne++;
            }
            nbColonne = chaine.length;
        }
        String[][] lignes = new String[nbLigne][nbColonne];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            for (int i = 0; i < nbLigne; i++) {
                String[] chaine = reader.readLine().split(separator);
                for (int j = 0; j < chaine.length; j++) {
                    lignes[i][j] = chaine[j];
                }
            }
        }
        return lignes;
    }

    /**
     * Renvoie la liste des colonnes numériques présentes dans le CSV
     * 
     * @param file le fichier à analyser
     * @return la liste des colonnes numériques
     */
    public static List<String> detectNumericColumns(File file, String separator) throws IOException {
        String[][] contenucsv = lireFichierCSV(file, separator);

        List<String> numericColumns = new ArrayList<>();
        for (int i = 0; i < DataModel.getAllColumns().size(); i++) {
            boolean isNumeric = true;
            try {
                Double.parseDouble(contenucsv[0][i]);
            } catch (NumberFormatException e) {
                isNumeric = false;
            }
            if (isNumeric) {
                numericColumns.add(DataModel.getAllColumns().get(i));
            }
        }
        return numericColumns;
    }

    /**
     * Renvoie la liste des colonnes non numériques présentes dans le CSV
     * 
     * @param file le fichier à analyser
     * @return la liste des colonnes non numériques
     */
    public static List<String> detectStringColumns(File file, String separator) throws IOException {
        List<String> headers = new ArrayList<>();
        headers.addAll(DataModel.getAllColumns());
        headers.removeAll(detectNumericColumns(file, separator));
        return headers;
    }
    
    /**
     * Renvoie les colonnes qui peuvent être des catégories 
     * 
     * @param stringColumns Les colonnes non numériques
     * @param dataList Les données
     * @return Les colonnes non numériques qui semblent pertinentes
     */
    public static List<String> detectCategoryColumns(List<String> stringColumns, Set<Data> dataList) {
        List<String> categoryColumns = new ArrayList<>();
        for (String column : stringColumns) {
            List<String> differentValues = new ArrayList<>();
            int valeurDifferentes = 0;
            for (Data data : dataList) {
                String value = data.getValueFromString(column);
                if (!differentValues.contains(value)) {
                    differentValues.add(value);
                    valeurDifferentes++;
                }
            }
            if (valeurDifferentes < dataList.size()/2 || valeurDifferentes < 20){
                categoryColumns.add(column);
            }
        }
        return categoryColumns;
    }
}