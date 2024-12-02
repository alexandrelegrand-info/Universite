package fr.univlille.g2.sae302.model;

/**
 * La classe Data fournit des méthodes utilitaires pour valider les saisies
 * de données par l'utilisateur pour la classification des Iris.
 * Elle sert également de "classe générique" pour les données Iris et
 * pour les données qui pourraient arriver plus tard.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */

public interface Data {
    public String getValueFromString(String str);
    public void setValueFromString(String str, String value);
    public String getCategory(String s);
    public int compareTo(Object arg0);
    public int hashCode();
    public boolean equals(Object o);
}
