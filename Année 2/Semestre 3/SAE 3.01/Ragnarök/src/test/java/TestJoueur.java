import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJoueur {
    Joueur j1;
    Mob m1, m2;

    @BeforeEach 
    public void initialization(){
        j1 = new Joueur("j1");
        m1 = new Mob(null, "m1", 1, 2, 2, null);
        m2 = new Mob(null, "m2", 1, 8, 7, null);
    }

    @Test 
    public void joueurTest(){
        assertEquals(j1.getLvl(), 1);
        assertEquals(j1.getCurrent_xp(), 0);
        j1.ajouterXP(m1);
        assertEquals(j1.getCurrent_xp(), 4);
        j1.ajouterXP(m2);
        assertEquals(j1.getCurrent_xp(), 19);
        assertEquals(j1.getWeapon().listAttack(), "1) Attaque légère  2) Attaque lourde  ");
    }
}
