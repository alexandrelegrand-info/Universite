package graphes;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;

/**
 * La classe Troncon représente un tronçon dans un réseau de transport, reliant
 * un lieu de départ à un lieu d'arrivée.
 * Elle implémente l'interface Trancon pour permettre son utilisation dans
 * différents contextes.
 * Cette classe est utilisée pour modéliser les segments de liaison entre les
 * lieux dans un réseau de transport.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class Troncon implements Trancon {
    private Lieu depart; // Lieu de départ du tronçon
    private Lieu arrivee; // Lieu d'arrivée du tronçon
    private ModaliteTransport modalite; // Modalité de transport du tronçon

    /**
     * Constructeur de la classe Troncon.
     * Crée un nouveau tronçon avec les lieux de départ et d'arrivée spécifiés,
     * ainsi que la modalité de transport.
     * 
     * @param depart   Le lieu de départ du tronçon.
     * @param arrivee  Le lieu d'arrivée du tronçon.
     * @param modalite La modalité de transport du tronçon.
     */
    public Troncon(Lieu depart, Lieu arrivee, ModaliteTransport modalite) {
        this.depart = depart; // Attribution du lieu de départ
        this.arrivee = arrivee; // Attribution du lieu d'arrivée
        this.modalite = modalite; // Attribution de la modalité de transport
    }

    /**
     * Constructeur de la classe Troncon prenant des noms de lieux et une modalité
     * de transport.
     * Crée un nouveau tronçon avec les noms de lieux de départ et d'arrivée
     * spécifiés, ainsi que la modalité de transport.
     * 
     * @param sdepart  Le nom du lieu de départ du tronçon.
     * @param sarrivee Le nom du lieu d'arrivée du tronçon.
     * @param modalite La modalité de transport du tronçon.
     */
    public Troncon(String sdepart, String sarrivee, ModaliteTransport modalite) {
        this(new MonLieu(sdepart), new MonLieu(sarrivee), modalite); // Appel du premier constructeur avec des objets
                                                                     // MonLieu
    }

    /**
     * Constructeur de la classe Troncon prenant des noms de lieux et une modalité
     * de transport sous forme de chaîne de caractères.
     * Crée un nouveau tronçon avec les noms de lieux de départ et d'arrivée
     * spécifiés, ainsi que la modalité de transport.
     * 
     * @param sdepart  Le nom du lieu de départ du tronçon.
     * @param sarrivee Le nom du lieu d'arrivée du tronçon.
     * @param modalite La modalité de transport du tronçon sous forme de chaîne de
     *                 caractères.
     */
    public Troncon(String sdepart, String sarrivee, String modalite) {
        this(new MonLieu(sdepart), new MonLieu(sarrivee), ModaliteTransport.valueOf(modalite)); // Appel du premier
                                                                                                // constructeur avec des
                                                                                                // objets MonLieu et la
                                                                                                // modalité de transport
                                                                                                // convertie en
                                                                                                // énumération
    }

    /**
     * Retourne le lieu d'arrivée du tronçon.
     * 
     * @return Le lieu d'arrivée du tronçon.
     */
    public Lieu getArrivee() {
        return arrivee;
    }

    /**
     * Retourne le lieu de départ du tronçon.
     * 
     * @return Le lieu de départ du tronçon.
     */
    public Lieu getDepart() {
        return depart;
    }

    /**
     * Retourne la modalité de transport du tronçon.
     * 
     * @return La modalité de transport du tronçon.
     */
    public ModaliteTransport getModalite() {
        return modalite;
    }

    /**
     * Crée un nouveau tronçon inversé avec le lieu de départ et d'arrivée échangés.
     * @return Un nouveau tronçon avec le lieu de départ et d'arrivée échangés.
     */
    public Trancon inversTrancon() {
        Trancon t = new Troncon(arrivee, depart, getModalite()); // Création d'un nouveau tronçon avec le lieu de départ et d'arrivée échangés
        return t;
    }

    public boolean isSameCity(){
        return ((MonLieu) this.getDepart()).cleanName().equals(((MonLieu) this.getArrivee()).cleanName());
    }

/**
     * Retourne une représentation textuelle du tronçon.
     * @return Une chaîne de caractères représentant les détails du tronçon.
     */
    @Override
    public String toString() {
        return "Troncon { Depart = " + depart + " ,Arrivée = " + arrivee + " ,Modalite = " + modalite +" }";  
    }
}
