package exception;

/**
 * La classe CSVFormatException est une exception personnalisée qui est lancée lorsqu'il y a un problème avec le format des fichiers CSV.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class CSVFormatException extends Exception {

    /**
     * Constructeur par défaut pour CSVFormatException.
     * Appelle le constructeur par défaut de la classe Exception.
     */
    public CSVFormatException() {
        super();
    }

    /**
     * Constructeur pour CSVFormatException avec un message d'erreur.
     * 
     * @param message Le message détaillant la raison de l'exception.
     */
    public CSVFormatException(String message) {
        super(message);
    }
}
