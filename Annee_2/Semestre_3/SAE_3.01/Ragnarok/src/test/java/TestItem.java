import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestItem {
    Item i1, i2;
    Weapon w1;
    Potion p1;
    Joueur j1;

    @BeforeEach
    public void initialization(){
        j1 = new Joueur("j1");
        j1.setHp_cap(50);
        j1.setHp(30);
        p1 = new Potion(20);
        ArrayList<Attack> a = new ArrayList<>();
        Attack a1 = new Attack("a1", null, 20);a.add(a1);
        w1 = new Weapon("w1", a);
        j1.setWeapon(w1); 
    }

    @Test
    public void potionTest(){
        j1.useItem(p1);
        assertTrue(j1.getHp() == j1.getHp_cap());
        assertTrue(p1 instanceof Item);
    }

    @Test
    public void weaponTest(){
        assertTrue(w1 instanceof Item);
        assertEquals(w1.listAttack(), "1) a1  ");
    }
}
