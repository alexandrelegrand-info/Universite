package fr.univlille.g2.sae302;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.Iris;
import fr.univlille.g2.sae302.model.LoadCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class testLoadCSV {

    private File testFile;

    @BeforeEach
    public void setUp() {
        testFile = new File("csv/iris.csv");
        DataModel.minimum.clear();
        DataModel.maximum.clear();
    }

    @Test
    public void testPremièreLigneCSV() throws IOException {
        ArrayList<String> expected = new ArrayList<>(List.of("sepal.length", "sepal.width", "petal.length",  "petal.width", "variety"));
        ArrayList<String> result = LoadCSV.premièreLigneCSV(testFile, ",");
        assertEquals(expected, result);
        assertTrue(DataModel.minimum.containsKey("petal.length"));
        assertTrue(DataModel.maximum.containsKey( "petal.width"));
    }

    @Test
    public void testLireFichierCSV() throws IOException {
        String[][] result = LoadCSV.lireFichierCSV(testFile, ",");
        assertEquals(150, result.length);
    }

    @Test
    public void testDetectStringColumns() throws IOException {
        List<String> expected = List.of("variety");
        DataModel.getAllColumns().addAll(List.of("sepal.length", "sepal.width", "petal.length",  "petal.width", "variety"));
        List<String> result = LoadCSV.detectStringColumns(testFile, ",");
        assertEquals(expected, result);
    }

    @Test
    public void testDetectCategoryColumns() {
        Set<Data> dataList = new HashSet<>();
        dataList.add(new Iris(0, 0, 0, 0, null));
        dataList.add(new Iris(1, 1, 1, 1, null));
        dataList.add(new Iris(2, 2, 2, 2, null));

        List<String> stringColumns = List.of("variety");
        List<String> expected = List.of("variety");

        List<String> result = LoadCSV.detectCategoryColumns(stringColumns, dataList);
        assertEquals(expected, result);
    }
}