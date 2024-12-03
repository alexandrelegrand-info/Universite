package fr.univlille.g2.sae302.model;

/**
 * La classe DataFactory permet de créer les bons objets selon le nom du fichier appelé
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */

public class DataFactory implements Factory {

    public Data dataFactory(String nomCSV, String... args) {
        switch (nomCSV.toLowerCase()) {
            case "iris.csv":
                Variety v = null;
                if(args[4] != null) v = Variety.valueOf(args[4]); 
                return new Iris(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]), v);
            case "pokemon_train.csv":
                return new Pokemon(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                        Double.parseDouble(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]),
                        Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), args[9],
                        args[10], Double.parseDouble(args[11]), args[12]);
            case "joueurstop14_train.csv":
                return new JoueursRugby(args[0], args[1], args[2], args[3], args[4], Integer.parseInt(args[5]),
                        Integer.parseInt(args[6]));
            //case nomfichier.csv :
                //return etc
            default:
                throw new IllegalArgumentException("TypeCSV inconnu : " + nomCSV);
        }
    }
}
