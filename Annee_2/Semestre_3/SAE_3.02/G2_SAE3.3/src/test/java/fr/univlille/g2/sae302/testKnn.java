package fr.univlille.g2.sae302;

import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.Iris;
import fr.univlille.g2.sae302.model.LoadCSV;
import fr.univlille.g2.sae302.model.MethodeKnn;
import fr.univlille.g2.sae302.model.Variety;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class testKnn {
    @Test
    public void test_methode_knn() throws IOException {
        Data data = new Iris(5.2, 6.2, 2.3, 0.5, null);
        File file = new File("csv/iris.csv");
        String[][] csv = LoadCSV.lireFichierCSV(file, ",");
        DataModel.minimum = new HashMap<String, Double>();
        DataModel.maximum = new HashMap<String, Double>();
        DataModel.minimum.put("sepal.length", 0.0);
        DataModel.minimum.put("sepal.width", 0.0);
        DataModel.minimum.put("petal.length", 0.0);
        DataModel.minimum.put("petal.width", 0.0);
        DataModel.maximum.put("sepal.length", 8.0);
        DataModel.maximum.put("sepal.width", 8.0);
        DataModel.maximum.put("petal.length", 8.0);
        DataModel.maximum.put("petal.width", 8.0);
        Set<Data> iris = new HashSet<>();
        for (int i = 0; i < csv.length; i++) {
            iris.add(new Iris(Double.parseDouble(csv[i][0]), Double.parseDouble(csv[i][1]), Double.parseDouble(csv[i][2]), Double.parseDouble(csv[i][3]), Variety.valueOf((csv[i][4]).substring(1,(csv[i][4].length()-1)).toUpperCase())));
        }
        Map<Data, Double> kVoisins = MethodeKnn.knn(List.of("sepal.length", "sepal.width", "petal.length", "petal.width"), iris,3, data, "manhattan");
        assertEquals(3, kVoisins.size());
    }
}
