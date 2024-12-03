import java.util.Random;

public class Attack {
    private String name;
    private Status status;
    private int damage;

    public Attack(String name, Status status, int damage){
        this.name = name;
        this.status = status;
        this.damage = damage;
    }

    public int getDamage(double niveau) {
        Random r = new Random();
        if(damage/niveau<=10){
            int alea = r.nextInt(100);
            if(alea<=10){
                System.out.println("l'attaque " + getName() +  " à échoué !");
                return 0;
            }
            return damage;
        }
        if(damage/niveau<=20){
            int alea = r.nextInt(100);
            if(alea<=13){
                System.out.println("l'attaque " + getName() +  " à échoué !");
                return 0;
            }
            return damage;
        }
        if(damage/niveau<=30){
            int alea = r.nextInt(100);
            if(alea<=16){
                System.out.println("l'attaque " + getName() +  " à échoué !");
                return 0;
            }
            return damage;
        }
        int alea = r.nextInt(100);
        if(alea<=20){
            System.out.println("l'attaque " + getName() +  " à échoué !");
            return 0;
        }
        return damage;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }
}
