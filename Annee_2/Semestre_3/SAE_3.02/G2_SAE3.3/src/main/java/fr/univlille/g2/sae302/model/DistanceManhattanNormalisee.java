package fr.univlille.g2.sae302.model;

import java.util.List;
import java.util.Map;

/**
 * La classe DistanceManhattanNormalisée permet de calculer la distance de manhattan normalisée entre 2 points.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class DistanceManhattanNormalisee implements Distance {
    private double dist;
    
    public DistanceManhattanNormalisee(Data d1, Data d2, List<String> classificationAttributs) {
        dist = distance(d1, d2, classificationAttributs);
    }


    /**
     * Calcule la distance de Manhattan normalisée entre 2 points.
     * 
     * @param d1 Le premier point.
     * @param d2 Le deuxième point.
     * @param classificationAttributs Les attributs de classification.
     * @return La distance de Manhattan normalisée entre les 2 points.
     */
    
    public double distance(Data d1, Data d2, List<String> classificationAttributs) {
        double distanceTotal = 0;
        Map<String, Double> minValues = DataModel.minimum;
        Map<String, Double> maxValues = DataModel.maximum;

        for (String attribut : classificationAttributs) {
            double amplitude = maxValues.get(attribut) - minValues.get(attribut);
            double value1Normalised = (Double.parseDouble(d1.getValueFromString(attribut))-minValues.get(attribut))/amplitude;
            double value2Normalised = (Double.parseDouble(d2.getValueFromString(attribut))-minValues.get(attribut))/amplitude;            
            double normalizedDifference = Math.abs(value1Normalised - value2Normalised);
            distanceTotal += normalizedDifference;
        }
        return distanceTotal;
    }

    public double getDistance(){return dist;}
}