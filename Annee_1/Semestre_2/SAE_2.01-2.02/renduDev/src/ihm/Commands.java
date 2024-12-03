package ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import app.*;
import exception.*;
import fr.ulille.but.sae_s2_2024.*;
import graphes.Trajet;
import graphes.TypeCout;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * La classe Commands gère les interactions de l'interface utilisateur pour calculer des trajets et afficher l'historique.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class Commands {
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;
    @FXML
    Label titre;
    @FXML
    ComboBox<Lieu> depart = new ComboBox<>();
    @FXML
    ComboBox<Lieu> arrivee = new ComboBox<>();
    @FXML
    CheckBox train = new CheckBox();
    @FXML
    CheckBox avion = new CheckBox();
    @FXML
    CheckBox bus = new CheckBox();
    @FXML
    Slider s_CO2 = new Slider();
    @FXML
    Slider s_temps = new Slider();
    @FXML
    Slider s_prix = new Slider();

    Plateforme plateforme;

    final static String SEPARATOR = System.getProperty("file.separator");
    final static String FILENAME = "res" + SEPARATOR + "data.csv";
    final static String FILENAMEC = "res" + SEPARATOR + "correspondance.csv";

    ArrayList<String[]> ventilatedData;
    ArrayList<String[]> ventilatedCorrespondance;
    ArrayList<Trajet> trajet = new ArrayList<>();

    /**
     * Méthode d'initialisation de la classe Commands.
     * 
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture des fichiers.
     * @throws SameCityException Si les villes de départ et d'arrivée sont les mêmes.
     * @throws CSVFormatException Si le format des données CSV est invalide.
     * @throws RoadNotFoundException Si aucune route n'est trouvée entre les villes.
     */
    public void initialize() throws IOException, SameCityException, CSVFormatException, RoadNotFoundException {
        ventilatedData = Graphes.ventilation(FILENAME);
        ventilatedCorrespondance = Graphes.ventilation(FILENAMEC);
        if (Graphes.areCorrespondancesValid(ventilatedCorrespondance) && Graphes.areTranconsValid(ventilatedData)) {
            // Création d'une instance de Plateforme avec les données prédéfinies
            Graphes graphes = new Graphes(FILENAME, FILENAMEC);
            plateforme = new Plateforme(graphes);
            afficherVille();
            depart.setPromptText("Ville de départ");
            arrivee.setPromptText("Ville d'arrivée");
        } else {
            throw new CSVFormatException("Données invalides");
        }
        System.out.println("Initialisation...");
    }

    /**
     * Affiche les villes dans les ComboBox de départ et d'arrivée.
     */
    public void afficherVille() {
        depart.setItems(FXCollections.observableArrayList(plateforme.getGraphes().getCleanLieux()).sorted());
        arrivee.setItems(FXCollections.observableArrayList(plateforme.getGraphes().getCleanLieux()).sorted());
        System.out.println("Villes ajoutées");
    }

    /**
     * Récupère les modalités de transport sélectionnées par l'utilisateur.
     * 
     * @return Un HashSet contenant les modalités de transport sélectionnées.
     */
    public HashSet<ModaliteTransport> checkBox() {
        HashSet<ModaliteTransport> h = new HashSet<>();
        if (train.isSelected()) {
            h.add(ModaliteTransport.TRAIN);
        }
        if (avion.isSelected()) {
            h.add(ModaliteTransport.AVION);
        }
        if (bus.isSelected()) {
            h.add(ModaliteTransport.BUS);
        }
        return h;
    }

    /**
     * Calcule les trajets en fonction des critères définis par l'utilisateur.
     * 
     * @param e L'événement déclenché par l'utilisateur.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du calcul des trajets.
     */
    public void calculer(ActionEvent e) throws IOException {
        titre.setText("Liste des itinéraires répondant à vos critères");
        try {
            if (arrivee.getValue() == null || depart.getValue() == null) {
                throw new RoadNotFoundException();
            } else if (arrivee.getValue().equals(depart.getValue())) {
                throw new SameCityException();
            }
            if (plateforme.getGraphes().getGrapheCoeff().aretes().size() == 0) {
                Voyageur v = new Voyageur(arrivee.getValue(), depart.getValue());
                plateforme.setVoyageur(v);
                plateforme.getGraphes().genererGraphe(ventilatedData, ventilatedCorrespondance, checkBox());
                if (s_CO2.getValue() == 0 && s_prix.getValue() == 0 && s_temps.getValue() == 0) {
                    label1.setText("Entrez au moins un critère de tri");
                } else {
                    plateforme.getGraphes().createCoeffGraphe(((int) s_CO2.getValue()) / 100, ((int)s_temps.getValue()) / 100, ((int)s_prix.getValue()) / 100);
                    List<Chemin> c = plateforme.calculKPCC(plateforme.getGraphes().getGrapheCoeff(), v);
                    if (c.size() == 0) {
                        throw new RoadNotFoundException("Aucune route existante");
                    }
                    depart.setPromptText("Ville de départ");
                    arrivee.setPromptText("Ville d'arrivée");
                    Trajet t1 = new Trajet(c.get(0), plateforme.getPoidsChemin(c.get(0), TypeCout.CO2), plateforme.getPoidsChemin(c.get(0), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(0), TypeCout.TEMPS));
                    label1.setText(t1.toString());
                    if (c.size() > 1) {
                        Trajet t2 = new Trajet(c.get(1), plateforme.getPoidsChemin(c.get(1), TypeCout.CO2), plateforme.getPoidsChemin(c.get(1), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(1), TypeCout.TEMPS));
                        label2.setText(t2.toString());
                        if (c.size() > 2) {
                            Trajet t3 = new Trajet(c.get(2), plateforme.getPoidsChemin(c.get(2), TypeCout.CO2), plateforme.getPoidsChemin(c.get(2), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(2), TypeCout.TEMPS));
                            label3.setText(t3.toString());
                        } else {
                            label3.setText("");
                        }
                    } else {
                        label2.setText("");
                        label3.setText("");
                    }
                    trajet.add(t1);
                    System.out.println(trajet);
                    Trajet.savingTrajet(trajet);
                }
            } else {
                plateforme.getGraphes().resetGraphe();
                calculer(e);
            }
        } catch (SameCityException sc) {
            label1.setText("Les villes de départ et d'arrivée sont les mêmes");
            label2.setText("");
            label3.setText("");
        } catch (RoadNotFoundException r) {
            label1.setText("Aucune route existante");
            label2.setText("");
            label3.setText("");
        }
    }

    /**
     * Affiche l'historique des trajets précédemment calculés.
     * 
     * @param e L'événement déclenché par l'utilisateur.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de l'historique.
     */
    public void historique(ActionEvent e) throws IOException {
        List<Trajet> t = Trajet.loadingTrajets();
        if (t == null || t.size() == 0) {
            titre.setText("Aucun historique disponible");
            label1.setText("");
            label2.setText("");
            label3.setText("");
        } else {
            titre.setText("Historique des trajets : ");
            label1.setText(t.get(t.size() - 1).toString());
            if (t.size() >= 2) {
                label2.setText(t.get(t.size() - 2).toString());
                if (t.size() >= 3) {
                    label3.setText(t.get(t.size() - 3).toString());
                } else {
                    label3.setText("");
                }
            } else {
                label2.setText("");
                label3.setText("");
            }
        }
    }
}