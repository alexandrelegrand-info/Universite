public class Mob extends Entity {
    private Weapon holdWeapon;
    private int multiplicator;
    private String charadesign;

    public Mob(Weapon holdWeapon, String name, int hp, int attack, int defense, String charadesign){
        setName(name); 
        setHp(hp);
        setHp_cap(hp);
        setAttack(attack);
        setDefense(defense);
        this.holdWeapon = holdWeapon;
        this.multiplicator = 1;
        this.charadesign = charadesign;
    }
    public Weapon getHoldWeapon() {
        return holdWeapon;
    }
    public int getMultiplicator() {
        return multiplicator;
    }
    public String getCharadesign() {
        return charadesign;
    }
    public void setMultiplicator(int multiplicator) {
        this.multiplicator = multiplicator;
    }
    public void afficherBarreDeVie() {
        int hpMax = getHp_cap();
        int hpActuel = getHp();
        int tailleBarre = 20;
    
        int pourcentage = (int) ((double) hpActuel / hpMax * tailleBarre);
        
        StringBuilder barre = new StringBuilder("[");
        for (int i = 0; i < tailleBarre; i++) {
            if (i < pourcentage) {
                barre.append("■"); // Rempli
            } else {
                barre.append(" "); // Vide
            }
        }
        barre.append("]");
    
        System.out.println( getName() + " : PV " + barre + " " + hpActuel + "/" + hpMax);
    }
}
