public abstract class Entity {
    private String name;
    private int hp;
    private int hp_cap;
    private int attack;
    private int defense;


    // Get
    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
    public int getHp() {
        return hp;
    }
    public String getName() {
        return name;
    }
    public int getHp_cap() {
        return hp_cap;
    }
    

    // Set
    public void setName(String name) {
        this.name = name;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setHp_cap(int hp_cap) {
        this.hp_cap = hp_cap;
    }
}

