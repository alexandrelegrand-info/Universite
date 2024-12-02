import java.util.ArrayList;

public class Weapon extends Item{
    private ArrayList<Attack> attacks;

    public Weapon(String name, ArrayList<Attack> attacks){
        super(name);
        this.attacks = attacks;
    }
    public ArrayList<Attack> getAttacks() {
        return attacks;
    }
    public String getName() {
        return super.getName();
    }
    public String listAttack(){
        String s = "";
        int i = 1;
        for(Attack a : attacks){
            s += i + ") " + a.getName() + "  ";
            ++i;
        }
        return s;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if (obj instanceof Weapon) {
            Weapon w = (Weapon) obj;
            return getName().equals(w.getName());
        }
        return false;
    }
}
