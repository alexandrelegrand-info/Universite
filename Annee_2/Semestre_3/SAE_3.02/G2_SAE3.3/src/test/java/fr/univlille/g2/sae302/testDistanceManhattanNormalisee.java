package fr.univlille.g2.sae302;

import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.DistanceManhattanNormalisee;
import fr.univlille.g2.sae302.model.Iris;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testDistanceManhattanNormalisee {
    @Test
    public void test_distance_manhattan_normalisee() throws IOException {
        Map<String, Double> mini = new HashMap<String, Double>();
        Map<String, Double> maxi = new HashMap<String, Double>();
        mini.put("sepal.length", 0.0);
        mini.put("sepal.width", 0.0);
        mini.put("petal.length", 0.0);
        mini.put("petal.width", 0.0);        
        maxi.put("sepal.length", 8.0);
        maxi.put("sepal.width", 8.0);
        maxi.put("petal.length", 8.0);
        maxi.put("petal.width", 8.0);
        DataModel.minimum = mini;
        DataModel.maximum = maxi;
        Data data = new Iris(5, 6, 3, 2, null);
        Data data2 = new Iris(5, 6, 2, 1, null);
        double distance = new DistanceManhattanNormalisee(data, data, List.of("sepal.length", "sepal.width", "petal.length", "petal.width")).getDistance();
        assertEquals(0.0, distance, 0.0);
        assertEquals(0.25, new DistanceManhattanNormalisee(data2, data, List.of("sepal.length", "sepal.width", "petal.length", "petal.width")).getDistance(), 0.0);
    }
}
