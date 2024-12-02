package fr.univlille.g2.sae302;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.univlille.g2.sae302.model.Pokemon;

public class testPokemon {
    
    Pokemon p;

    @Before
    public void setup(){
        p = new Pokemon(null, 0, 0, 0, 0,
         0, 0, 0, 0, null, null, 0, "false");
    }

    @Test
    public void test_get_value_from_string(){
        assertEquals("0", p.getValueFromString("attack"));
        assertEquals("false", p.getValueFromString("is_legendary"));
        assertEquals("null", p.getValueFromString("name"));
    }
}
