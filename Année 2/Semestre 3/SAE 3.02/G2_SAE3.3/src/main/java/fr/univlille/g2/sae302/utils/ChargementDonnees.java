package fr.univlille.g2.sae302.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.opencsv.bean.CsvToBeanBuilder;

import fr.univlille.g2.sae302.model.Data;

/**
 * Classe utilitaire pour le chargement et la manipulation des Iris
 * à partir d'un fichier CSV. Cette classe contient des méthodes pour
 * charger les données, convertir des objets de type DonneeBrut en Iris,
 * et mettre à jour les valeurs minimales et maximales des différents attributs.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class ChargementDonnees {

    /**
     * Crée un Set de Data à partir d'une liste de DonneeBrut.
     * 
     * @param donnees La liste de DonneeBrut à convertir en Data.
     * @return Un ensemble de Data contenant les données converties.
     */
    public static Set<Data> dataList(List<DonneeBrut> donnees) {
        Set<Data> iris = new TreeSet<>();
        for (DonneeBrut fm : donnees) {
            Data toadd = fm.convert();
            iris.add(toadd);
        }
        return iris;
    }

    /**
     * Charge les données à partir d'un fichier CSV.
     * 
     * @param file Le fichier CSV à charger.
     * @return Une liste d'objets DonneeBrut contenant les données lues.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture du fichier.
     */

   public static char detectSeparator(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line.contains(",")) {
                return ',';
            } else if (line.contains(";")) {
                return ';';
            } else if (line.contains("\t")) {
                return '\t';
            } else {
                return ' ';
            }
        }
    }

    /**
     * Charge les données à partir d'un fichier CSV.
     * 
     * @param file Le fichier CSV à charger.
     * @param c La classe héritant de DonneeBrut correspondant aux données à charger.
     * @return Une liste d'objets DonneeBrut contenant les données lues.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture du fichier.
     */

    public static List<DonneeBrut> charger(File file, Class<? extends DonneeBrut> c) throws IOException {
        char separator = detectSeparator(file); // Détection du séparateur
        return new CsvToBeanBuilder<DonneeBrut>(Files.newBufferedReader(file.toPath()))
                .withSeparator(separator) // Utilisation du séparateur détecté
                .withType(c)
                .build()
                .parse();
    }

    /**
     * Permet de retrouver une classe ) à partir de la 1ère ligne de son CSV
     * 
     * @param firstLine La première ligne du CSV.
     * @return La classe héritant de DonneeBrut.
     */
    
    public static Class<? extends DonneeBrut> classFinder(String firstLine) {
        switch (firstLine) {
            case "[sepal.length, sepal.width, petal.length, petal.width, variety]":
                return IrisDonneeBrut.class;
            case "[name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, is_legendary]":
                return PokemonDonneeBrut.class;
            // case toString de la 1ere ligne du fichier CSV
            case "[Equipe, Nom, Poste, Type poste, Date de naissance, Taille (en cm), Poids (en kg)]":
                return JoueursRugbyDonneeBrut.class;
            default:
                return null;
        }
    }
}
