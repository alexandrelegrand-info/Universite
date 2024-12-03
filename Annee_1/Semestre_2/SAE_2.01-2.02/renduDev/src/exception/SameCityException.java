package exception;

/**
 * Cette exception est levée pour signaler qu'un voyage implique le départ et l'arrivée dans la même ville.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class SameCityException extends Exception {

    /**
     * Construit une nouvelle exception indiquant un voyage impliquant le départ et l'arrivée dans la même ville, sans message spécifique.
     */
    public SameCityException() {
        super();
    }

    /**
     * Construit une nouvelle exception indiquant un voyage impliquant le départ et l'arrivée dans la même ville, avec un message spécifique.
     * 
     * @param message Le message détaillant la raison pour laquelle le voyage implique le départ et l'arrivée dans la même ville.
     */
    public SameCityException(String message) {
        super(message);
    }
}
