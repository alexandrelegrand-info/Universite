package fr.univlille.g2.sae302.model;

import java.util.List;


/**
 * Interface utilisée pour les 4 algorithmes de calculs de distance.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public interface Distance {
    public double distance(Data d1, Data d2, List<String> classificationAxes);
} 
