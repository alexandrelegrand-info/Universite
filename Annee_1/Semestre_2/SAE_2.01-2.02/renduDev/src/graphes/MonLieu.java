package graphes;

import fr.ulille.but.sae_s2_2024.Lieu;

/**
 * La classe MonLieu représente un lieu dans un système de transport.
 * Elle implémente l'interface Lieu pour permettre son utilisation dans
 * différents contextes.
 * Cette classe est utilisée pour modéliser des points d'arrêt dans un réseau de
 * transport.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class MonLieu implements Lieu {
    private String nom;

    /**
     * Constructeur de la classe MonLieu.
     * 
     * @param nom Le nom du lieu(de la ville).
     */
    public MonLieu(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le nom du lieu.
     * 
     * @return Le nom du lieu.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Compare l'objet actuel avec un autre objet pour déterminer leur égalité.
     * 
     * @param obj L'objet à comparer avec l'objet actuel.
     * @return true si les deux objets sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MonLieu))
            return false;
        MonLieu other = (MonLieu) obj;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        return true;
    }

    /**
     * Retourne une représentation textuelle du lieu.
     * 
     * @return Une chaîne de caractères représentant le nom du lieu.
     */
    @Override
    public String toString() {
        return this.nom;
    }

    /**
     * Nettoie le nom en retirant toute partie après une virgule.
     * 
     * @return Le nom nettoyé, c'est-à-dire la partie avant la virgule.
     */
    public String cleanName() {
        return this.getNom().split(",")[0];
    }
}
