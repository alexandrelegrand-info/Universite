package fr.univlille.g2.sae302;

import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DistanceManhattan;
import fr.univlille.g2.sae302.model.Iris;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

public class testDistanceManhattan {
    @Test
    public void test_distance_manhattan() throws IOException {
        Data data = new Iris(5, 6, 3, 2, null);
        Data data2 = new Iris(5, 6, 2, 1, null);
        double distance = new DistanceManhattan(data, data, List.of("sepal.length", "sepal.width", "petal.length", "petal.width")).getDistance();
        assertEquals(0.0, distance, 0.0);
        assertEquals(2.0, new DistanceManhattan(data2, data, List.of("sepal.length", "sepal.width", "petal.length", "petal.width")).getDistance(), 0.0);
    }
}
