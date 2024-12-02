package fr.univlille.g2.sae302;

import java.io.IOException;
import java.net.URL;

import fr.univlille.g2.sae302.controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe Main qui sert à l'exécution de notre application
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Méthode qui permet de lancer l'application
     * 
     * @param primaryStage la fenêtre principale de l'application
     */
    
    public void start(Stage primaryStage) {

        ApplicationController controller = new ApplicationController();
        controller.getDv().setAc(controller);

        try {
            FXMLLoader loader = new FXMLLoader();

            URL fxmlFileUrl = getClass().getClassLoader().getResource("Explorateur_Fichier.fxml");
            if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
            }
            loader.setLocation(fxmlFileUrl);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setTitle("Classification");
            primaryStage.show();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
