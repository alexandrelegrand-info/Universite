package exception;

/**
 * Cette exception est levée pour signaler qu'une route spécifique n'a pas été trouvée.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 * @version 1.0
 */
public class RoadNotFoundException extends Exception {

    /**
     * Construit une nouvelle exception de route non trouvée sans message spécifique.
     */
    public RoadNotFoundException() {
        super();
    }

    /**
     * Construit une nouvelle exception de route non trouvée avec un message spécifique.
     * 
     * @param message Le message détaillant la raison pour laquelle la route n'a pas été trouvée.
     */
    public RoadNotFoundException(String message) {
        super(message);
    }
}
