public class Potion extends Item{
    private int hpRegenerated;

    public Potion(int hpRegenerated){
        super("Potion de soin");
        this.hpRegenerated = hpRegenerated;
    }
    public int getHpRegenerated() {
        return hpRegenerated;
    }
}
