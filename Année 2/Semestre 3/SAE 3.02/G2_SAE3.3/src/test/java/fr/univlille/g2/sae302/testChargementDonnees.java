package fr.univlille.g2.sae302;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import fr.univlille.g2.sae302.utils.ChargementDonnees;
import fr.univlille.g2.sae302.utils.DonneeBrut;
import fr.univlille.g2.sae302.utils.IrisDonneeBrut;
import fr.univlille.g2.sae302.utils.PokemonDonneeBrut;

public class testChargementDonnees {
    
    @Test
    public void test_class_finder(){
        assertEquals(null, ChargementDonnees.classFinder("test"));
        assertEquals(IrisDonneeBrut.class, ChargementDonnees.classFinder("[sepal.length, sepal.width, petal.length, petal.width, variety]"));
        assertEquals(PokemonDonneeBrut.class, ChargementDonnees.classFinder("[name, attack, base_egg_steps, capture_rate, defense, experience_growth, hp, sp_attack, sp_defense, type1, type2, speed, is_legendary]"));
    }  
    
    @Test
    public void test_charger() throws IOException{
        List<DonneeBrut> ldI = ChargementDonnees.charger(new File("csv/iris.csv"), IrisDonneeBrut.class); 
        List<DonneeBrut> ldP = ChargementDonnees.charger(new File("csv/pokemon_train.csv"), PokemonDonneeBrut.class);
        assertEquals(150, ldI.size());
        assertEquals(507, ldP.size());

        assertEquals(149, ChargementDonnees.dataList(ldI).size());
        assertEquals(507, ChargementDonnees.dataList(ldP).size());
    }
}
