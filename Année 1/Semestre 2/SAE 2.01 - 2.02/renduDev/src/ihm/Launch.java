package ihm;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * La classe Launch est le point d'entrée de l'application JavaFX.
 * Elle charge l'interface utilisateur définie dans le fichier FXML et l'affiche dans une fenêtre.
 *  
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class Launch extends Application {

    /**
     * Démarre l'application JavaFX.
     * 
     * @param stage La fenêtre principale de l'application.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement du fichier FXML.
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("IHMV1.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Application");
        stage.show();
    }

    /**
     * Le point d'entrée principal de l'application.
     * 
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
