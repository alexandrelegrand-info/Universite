package fr.univlille.g2.sae302.model;

import java.util.List;


/**
 * La classe DistanceEuclidienne permet de calculer la distance euclidienne entre 2 points.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class DistanceEuclidienne implements Distance {
    private double dist;
    
    public DistanceEuclidienne(Data d1, Data d2, List<String> classificationAttributs) {
        dist = distance(d1, d2, classificationAttributs);
    }

    /**
     * Calcule la distance euclidienne entre 2 points.
     * 
     * @param d1 Le premier point.
     * @param d2 Le deuxième point.
     * @param classificationAttributs Les attributs de classification.
     * @return La distance euclidienne entre les 2 points.
     */
    
    public double distance(Data d1, Data d2, List<String> classificationAttributs) {
        double distance = 0;
        for (String attribut:classificationAttributs){
            distance += Math.pow(Double.parseDouble(d1.getValueFromString(attribut)) -(Double.parseDouble(d2.getValueFromString(attribut))), 2);
        }
        return Math.sqrt(distance);
    }

    public double getDistance(){return dist;}
}