package fr.univlille.g2.sae302.model;

/**
 * Interface contenant la méthode de création de données
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public interface Factory {
    public Data dataFactory(String nomCSV, String... args);
}
