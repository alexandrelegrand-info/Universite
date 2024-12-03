package fr.univlille.g2.sae302.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * La classe MéthodeKnn possède toutes les méthodes nécessaires aux calculs de k-NN et de la robustesse.
 * 
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class MethodeKnn {

    /**
     * Méthode K-NN qui calcule les k voisins les plus proches 
     * 
     * @param classificationAxes Les axes utilisés pour la comparaison
     * @param data Les données utilisées pour calculer la distance
     * @param k Le nombre de voisins plus proches voulus
     * @param newData La donnée à comparer avec les autres pour deviner sa catégorie
     * @param distanceName L'algorithme utilisé pour calculer la distance
     * @return Une Map des données plus proches et leurs distances
     */
    
    public static Map<Data, Double> knn(List<String> classificationAxes, Set<Data> data, int k, Data newData, String distanceName) {
        Map<Data, Double> nearestNeighbors = new HashMap<>();
        for (Data dataPoint : data) {
            double distance = computeDistance(newData, dataPoint, classificationAxes, distanceName);
            if (nearestNeighbors.size() < k) {
                nearestNeighbors.put(dataPoint, distance);
            } else {
                Data dataPointToRemove = null;
                double maxDistance = -Double.MAX_VALUE;
                for (Map.Entry<Data, Double> entry : nearestNeighbors.entrySet()) {
                    if (entry.getValue() > maxDistance) {
                        dataPointToRemove = entry.getKey();
                        maxDistance = entry.getValue();
                    }
                }
                if (distance < maxDistance) {
                    nearestNeighbors.remove(dataPointToRemove);
                    nearestNeighbors.put(dataPoint, distance);
                }
            }
        }
        // convert distances to weights
        for (Map.Entry<Data, Double> entry : nearestNeighbors.entrySet()) {
            Data dataPoint = entry.getKey();
            double weight = 1 / Math.pow(entry.getValue(), 2);
            nearestNeighbors.put(dataPoint, weight);
        }
        return nearestNeighbors;
    }

    /**
     * Renvoie vers la bonne méthode de calcul de distance
     * 
     * @param newData La donnée à comparer pour deviner sa catégorie
     * @param dataPoint La donnée utilisée pour la comparaison
     * @param classificationAxes Les axes utilisés pour la comparaison
     * @param distanceName L'algorithme utilisé pour calculer la distance
     * @return La distance entre les 2 points
     */
    private static double computeDistance(Data newData, Data dataPoint, List<String> classificationAxes, String distanceName) {
        switch (distanceName) {
            case "euclidean":
                return new DistanceEuclidienne(newData, dataPoint, classificationAxes).getDistance();
            case "manhattan":
                return new DistanceManhattan(newData, dataPoint, classificationAxes).getDistance();
            case "manhattanNormalisee":
                return new DistanceManhattanNormalisee(newData, dataPoint, classificationAxes).getDistance();
            case "euclideanNormalisee":
                return new DistanceEuclidienneNormalisee(newData, dataPoint, classificationAxes).getDistance();
            default:
                throw new IllegalArgumentException("Unknown distance: " + distanceName);
        }
    }

    /**
     * Calcule la robustesse de k-NN
     * 
     * @param data Les données utilisées pour calculer la robustesse
     * @param k Le nombre de voisins
     * @param classificationAxes Les axes utilisées pour la robustesse
     * @param cate La catégorie utilisée
     * @param distance L'algorithme utilisé
     * @return Le nombre de valeurs correctes 
     */
    public static int robustesseKnn(Set<Data> data, int k, List<String> classificationAxes, String cate, String distance) {
        List<Data> dataList = List.copyOf(data);
        //Collections.shuffle(dataList);
        int nbCorrect = 0;

        int size = dataList.size();
        int third = size / 3;

        List<Data> firstPart = dataList.subList(0, third);
        List<Data> secondPart = dataList.subList(third, 2 * third);
        List<Data> thirdPart = dataList.subList(2 * third, size);

        Set<Data> trainingData = new HashSet<>(secondPart);
        trainingData.addAll(thirdPart);

        // Tester le premier tiers
        nbCorrect += testKnn(firstPart, trainingData, k, classificationAxes, cate, distance);
        trainingData.removeAll(secondPart);
        trainingData.addAll(firstPart);

        // Tester le deuxième tiers
        nbCorrect += testKnn(secondPart, trainingData, k, classificationAxes, cate, distance);
        trainingData.removeAll(thirdPart);
        trainingData.addAll(secondPart);

        // Tester le troisième tiers
        nbCorrect += testKnn(thirdPart, trainingData, k, classificationAxes, cate, distance);

        return nbCorrect;
    }

    /**
     * Teste une partie des données sur le reste des données
     * Simulation du K-NN à grande échelle
     * 
     * @param testPart La partie de données qui sera testée
     * @param trainingData Le reste de données pour comparer les autres données
     * @param k Le nombre de voisins voulu
     * @param classificationAxes Les axes pour classifier
     * @param cate La catégorie choisie
     * @param distance L'algorithme de distance utilisé
     * @return Le nombre de valeurs justes
     */
    private static int testKnn(List<Data> testPart, Set<Data> trainingData, int k, List<String> classificationAxes,
            String cate, String distance) {
        int nbCorrect = 0;
        for (Data d : testPart) {
            Map<Data, Double> nearestNeighbors = knn(classificationAxes, trainingData, k, d, distance);
            int nbCorrectNeighbors = 0;
            for (Map.Entry<Data, Double> entry : nearestNeighbors.entrySet()) {
                if (entry.getKey().getValueFromString(cate).equals(d.getValueFromString(cate))) {
                    nbCorrectNeighbors++;
                }
            }
            if (nbCorrectNeighbors >= k / 2) {
                nbCorrect++;
            }
        }
        return nbCorrect;
    }
}