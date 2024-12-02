public class Boss extends Mob {
    public Boss(String name, Weapon weapon, String charadesign, int multiplicator, int hp, int attack, int defense){
        super(weapon, name, hp, attack, defense, charadesign);
        setMultiplicator(multiplicator);
    }
}
