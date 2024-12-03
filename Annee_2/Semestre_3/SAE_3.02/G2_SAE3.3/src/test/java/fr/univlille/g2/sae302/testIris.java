package fr.univlille.g2.sae302;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import fr.univlille.g2.sae302.model.Iris;
import fr.univlille.g2.sae302.model.Variety;

public class testIris {
    
    Iris i;

    @Before
    public void setup(){
        i = new Iris(0, 2.3, 4.1, 3.6, null);
    }

    @Test
    public void test_get_value_from_string(){
        assertEquals("4.1", i.getValueFromString("petal.length"));
        assertEquals("3.6", i.getValueFromString("petal.width"));
        assertEquals("0.0", i.getValueFromString("sepal.length"));
        assertEquals("2.3", i.getValueFromString("sepal.width"));
        assertEquals("null", i.getValueFromString("variety"));
        assertEquals("", i.getValueFromString("autres"));

        i.setVariety(Variety.SETOSA);
        assertEquals("SETOSA", i.getValueFromString("variety"));
    }

    @Test
    public void test_set_value_from_string(){
        assertEquals("4.1", i.getValueFromString("petal.length"));
        i.setValueFromString("petal.length", "2.0");
        assertEquals("2.0", i.getValueFromString("petal.length"));

        assertEquals("null", i.getCategory(""+i.getVariety()));
        i.setValueFromString("variety", "SETOSA");
        assertEquals("SETOSA", i.getCategory(""+i.getVariety()));
    }

    @Test
    public void test_random_variety(){
        assertEquals("null", i.getCategory(""+i.getVariety()));
        i.randomVariety();
        assertNotEquals("null", i.getCategory(""+i.getVariety()));
    }
}
