package fr.univlille.g2.sae302;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.Iris;
import fr.univlille.g2.sae302.model.Variety;
public class testDataModel {
    DataModel dm;

    @Before
    public void setup(){
        dm = new DataModel();
    }
    
    @Test
    public void test_ajout_point(){
        assertEquals(0, dm.getDataListAddedByUser().size());
        dm.addNewPoint(new Iris(0, 0, 0, 0, null));
        assertEquals(1, dm.getDataListAddedByUser().size());
    }

    @Test
    public void test_update_min_max(){
        List<String> num = new ArrayList<>();
        num.add("sepal.length");num.add("sepal.width");
        num.add("petal.length");num.add("petal.width");
        DataModel.setNumericColumns(num);

        DataModel.minimum.put("sepal.length", 1.0);
        DataModel.minimum.put("sepal.width", 1.0);
        DataModel.minimum.put("petal.length", 1.0);
        DataModel.minimum.put("petal.width", 1.0);
        DataModel.maximum.put("sepal.length", 8.0);
        DataModel.maximum.put("sepal.width", 8.0);
        DataModel.maximum.put("petal.length", 8.0);
        DataModel.maximum.put("petal.width", 8.0);
        
        double oldMin = DataModel.minimum.get("petal.length");
        double oldMax = DataModel.maximum.get("sepal.length");
        Iris i = new Iris(10, 0, 0, 0, null);
        DataModel.updateMinAndMax(i);
        double newMin = DataModel.minimum.get("petal.length");
        double newMax = DataModel.maximum.get("sepal.length");
        
        assertNotEquals(oldMin, newMin);
        assertNotEquals(oldMax, newMax);
    }

    @Test
    public void test_get_value_from_category(){
        dm.setCategory("variety");
        Iris i1 = new Iris(10, 0, 0, 0, Variety.VERSICOLOR);
        assertEquals("VERSICOLOR", dm.getValueOfCategory(i1));
        Iris i2 = new Iris(10, 0, 0, 0, null);
        assertEquals("null", dm.getValueOfCategory(i2));
        Iris i3 = new Iris(10, 0, 0, 0, Variety.SETOSA);
        assertEquals("SETOSA", dm.getValueOfCategory(i3));
    }

    @Test
    public void test_random_category(){
        Iris i1 = new Iris(10, 0, 0, 0, null);
        DataModel.categoryValues = new HashSet<>();
        DataModel.categoryValues.add("VERSICOLOR");
        DataModel.categoryValues.add("SETOSA");
        dm.setCategory("variety");
        dm.addNewPoint(i1);
        assertNull(i1.getVariety());
        dm.randomClassifyPoint();
        assertNotNull(i1.getVariety());
    }

    @Test
    public void test_most_common_category(){
        Map<String, Double> poidsPourChaqueCategorie = new HashMap<>();
        poidsPourChaqueCategorie.put("Setosa", 3.0);
        poidsPourChaqueCategorie.put("Virginica", 5.0);
        poidsPourChaqueCategorie.put("Versicolor", 2.0);
         
        String resultat = dm.mostCommonCategory(poidsPourChaqueCategorie);
        assertEquals("Virginica", resultat); 
    }
}
