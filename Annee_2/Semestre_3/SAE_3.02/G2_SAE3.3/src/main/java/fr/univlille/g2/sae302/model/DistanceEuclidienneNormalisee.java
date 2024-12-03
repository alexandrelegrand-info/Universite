package fr.univlille.g2.sae302.model;

import java.util.List;
import java.util.Map;

/**
 * La classe DistanceEuclidienneNormalisée permet de calculer la distance euclidienne normalisée entre 2 points.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class DistanceEuclidienneNormalisee implements Distance {
    private double dist;
    
    public DistanceEuclidienneNormalisee(Data d1, Data d2, List<String> classificationAttributs) {
        dist = distance(d1, d2, classificationAttributs);
    }

    /**
     * Calcule la distance euclidienne normalisée entre 2 points.
     * 
     * @param d1 Le premier point.
     * @param d2 Le deuxième point.
     * @param classificationAttributs Les attributs de classification.
     * @return La distance euclidienne normalisée entre les 2 points.
     */
    
    public double distance(Data d1, Data d2, List<String> classificationAttributs) {
        double distanceTotal = 0;
        Map<String, Double> minValues = DataModel.minimum;
        Map<String, Double> maxValues = DataModel.maximum;
        for (String attribut : classificationAttributs) {
            double amplitude = maxValues.get(attribut) - minValues.get(attribut);
            double value1Normalised = (Double.parseDouble(d1.getValueFromString(attribut))-minValues.get(attribut))/amplitude;
            double value2Normalised = (Double.parseDouble(d2.getValueFromString(attribut))-minValues.get(attribut))/amplitude;            
            double normalizedDifference = (value1Normalised - value2Normalised);
            distanceTotal += Math.pow(normalizedDifference, 2);
        }
        return Math.sqrt(distanceTotal);
    }

    public double getDistance(){return dist;}
}