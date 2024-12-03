package graphes;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.Plateforme;
import fr.ulille.but.sae_s2_2024.Chemin;

/**
 * La classe Trajet représente un trajet avec des informations sur le chemin, le CO2, le prix et le temps.
 * Cette classe implémente Serializable pour permettre la sauvegarde et le chargement des objets Trajet.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class Trajet implements Serializable {
    private static final long serialVersionUID = 8251635743923569684L;
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String FILENAME = "res" + SEPARATOR + "trajet.ser";

    private String chemin;
    private String co2;
    private String prix;
    private String temps;

    /**
     * Constructeur de la classe Trajet.
     * 
     * @param chemin Le chemin du trajet.
     * @param co2 La quantité de CO2 émise pour ce trajet.
     * @param prix Le prix du trajet.
     * @param temps Le temps nécessaire pour ce trajet.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la conversion du chemin.
     */
    public Trajet(Chemin chemin, String co2, String prix, String temps) throws IOException {
        this.chemin = Plateforme.OtherToString(chemin);
        this.co2 = co2;
        this.prix = prix;
        this.temps = temps;
    }

    /**
     * Retourne le chemin du trajet.
     * 
     * @return Le chemin du trajet sous forme de chaîne de caractères.
     */
    public String getChemin() {
        return chemin;
    }

    /**
     * Retourne la quantité de CO2 émise pour ce trajet.
     * 
     * @return La quantité de CO2.
     */
    public String getCo2() {
        return co2;
    }

    /**
     * Retourne le prix du trajet.
     * 
     * @return Le prix du trajet.
     */
    public String getPrix() {
        return prix;
    }

    /**
     * Retourne le temps nécessaire pour ce trajet.
     * 
     * @return Le temps du trajet.
     */
    public String getTemps() {
        return temps;
    }

    /**
     * Sauvegarde une liste de trajets dans le fichier res/trajet.ser.
     * 
     * @param trajet La liste de trajets à sauvegarder.
     */
    public static void savingTrajet(ArrayList<Trajet> trajet) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(trajet);
            oos.flush();
            System.out.println("Trajet saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge une liste de trajets depuis un fichier.
     * 
     * @return La liste de trajets chargés, ou null s'il y a une erreur.
     */
    @SuppressWarnings("unchecked")
    public static List<Trajet> loadingTrajets() {
        ArrayList<Trajet> traj = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            traj = (ArrayList<Trajet>) ois.readObject();
            System.out.println("Trajets loaded successfully.");
        } catch (EOFException e) {
            System.out.println("Historique vide");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return traj;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du trajet.
     * 
     * @return Une chaîne de caractères représentant le trajet.
     */
    @Override
    public String toString() {
        return chemin + "\n" + co2 + " / " + temps + " / " + prix;
    }
}
