package app;

import fr.ulille.but.sae_s2_2024.Lieu;

/**
 * La classe Voyageur représente un voyageur effectuant un voyage entre deux
 * lieux.
 * Elle est utilisée pour suivre les détails du voyage d'un individu, tels que
 * le lieu de départ et le lieu d'arrivée.
 * Chaque voyageur est identifié par un identifiant unique.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 2.0
 */
public class Voyageur {
    private final int ID; // Identifiant unique du voyageur
    private Lieu arrivee; // Lieu d'arrivée du voyageur
    private Lieu depart; // Lieu de départ du voyageur
    private static int cpt = 0; // Compteur utilisé pour attribuer des identifiants uniques

    /**
     * Constructeur de la classe Voyageur.
     * Crée un nouveau voyageur avec les lieux de départ et d'arrivée spécifiés.
     * 
     * @param arrivee Le lieu d'arrivée du voyageur.
     * @param depart  Le lieu de départ du voyageur.
     */
    public Voyageur(Lieu arrivee, Lieu depart) {
        this.ID = ++cpt; // Attribution d'un nouvel identifiant unique
        this.arrivee = arrivee; // Attribution du lieu d'arrivée
        this.depart = depart; // Attribution du lieu de départ
    }

    /**
     * Crée un nouvel objet Voyageur avec les lieux de départ et d'arrivée à null.
     */
    public Voyageur() {
        this(null, null);
    }

    /**
     * Retourne le lieu d'arrivée du voyageur.
     * 
     * @return Le lieu d'arrivée de type Lieu.
     */
    public Lieu getArrivee() {
        return arrivee;
    }

    /**
     * Retourne le lieu de départ du voyageur.
     * 
     * @return Le lieu de départ de type Lieu.
     */
    public Lieu getDepart() {
        return depart;
    }

    /**
     * Retourne l'identifiant du voyageur.
     * 
     * @return L'identifiant du voyageur de type int.
     */
    public int getID() {
        return ID;
    }

    /**
     * Définit le lieu d'arrivée du voyageur.
     * 
     * @param arrivee Le lieu d'arrivée de type Lieu à définir.
     */
    public void setArrivee(Lieu arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * Définit le lieu de départ du voyageur.
     * 
     * @param depart Le lieu de départ de type Lieu à définir.
     */
    public void setDepart(Lieu depart) {
        this.depart = depart;
    }

    /**
     * Définit le voyageur pour ce trajet.
     *
     * @param voyageur Le voyageur à associer à ce trajet.
     */
    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }
}
