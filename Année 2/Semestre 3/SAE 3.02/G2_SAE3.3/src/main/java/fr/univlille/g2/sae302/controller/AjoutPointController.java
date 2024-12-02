package fr.univlille.g2.sae302.controller;

import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataFactory;
import fr.univlille.g2.sae302.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


/**
 * Le contrôleur pour l'ajout d'un point.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class AjoutPointController {

    @FXML
    private GridPane axesGrid;

   


    private DataModel dataModel;
    private String csvName;
    private Map<String, TextField> inputFields = new HashMap<>();

    /**
     * Injecte le modèle et le nom du fichier CSV
     * 
     * @param dataModel Modèle de données
     * @param csvName Nom du fichier CSV
     * 
     */ 
    public void setDataModel(DataModel dataModel, String csvName) {
        this.dataModel = dataModel;
        this.csvName = csvName;
        setupForm();
    }

    /**
     * Crée dynamiquement les champs de saisie pour les axes numériques
     */
    private void setupForm() {
        int row = 0;
        for (String axis : dataModel.getNumericColumns()) { 
            Label label = new Label(axis + " :");
            TextField inputField = new TextField();

            axesGrid.add(label, 0, row);
            axesGrid.add(inputField, 1, row);

            inputFields.put(axis, inputField);
            row++;
        }

    }

    /**
     * Ajoute les champs des TextFields dans un tableau
     * 
     * @return le tableau des valeurs
     */
    private String[] ajoutChamps(){
        String[] values = new String[dataModel.getNumericColumns().size()];
        int i = 0;
        for (String axis : dataModel.getNumericColumns()) {
            String value = inputFields.get(axis).getText();
            if (value.isEmpty() || !isDouble(value)) showError("Valeur invalide pour l'axe : " + axis);
            values[i] = value;
            i++;  
        }
        return values;
    }

    /**
     * Vérifie si les données sont communes (positives et dans les bornes)
     * 
     * @return un booléen
     */
    private boolean areValuesNormales(){
        boolean afficher_confirmation = false;
        for (String axis : dataModel.getNumericColumns()) {
            String value = inputFields.get(axis).getText();
            if(!isValidEntry(value, axis)) afficher_confirmation = true;
        }
        return afficher_confirmation;
    }

    /**
     * Vérifie si le point est normal, si non il affiche une pop up de confirmation avant d'ajouter ou non le point
     * et de fermer la fenêtre d'ajout du point
     * 
     */
    public void validerPoint() {
        String[] values = ajoutChamps();
        boolean afficher_confirmation = areValuesNormales();

        if(afficher_confirmation) {
            Alert a  = new Alert(Alert.AlertType.CONFIRMATION, "Une des données saisies est négative ou anormalement basse/haute !\n Confirmez-vous l'ajout ?", ButtonType.YES, ButtonType.NO);
            a.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) ajoutPoint(values);
                else a.close();
            });
        } else ajoutPoint(values);
        Stage stage = (Stage) axesGrid.getScene().getWindow();
        stage.close();
    }

    /**
     * Ajoute un point et met à jour le modèle
     * 
     * @param values les valeurs numériques des TextFields
     * 
     */
    private void ajoutPoint(String[] values){
        DataFactory factory = new DataFactory();
        Data newPoint = factory.dataFactory(csvName, remplissageValeurs(values));
        DataModel.updateMinAndMax(newPoint);
        dataModel.addNewPoint(newPoint);
    }

    /**
     * Remplie un tableau selon l'ordre des colonnes en ajoutant en plus
     * des données numériques, les valeurs non numériques (null)
     * 
     * @param values Les valeurs numériques des TextFields
     * @return Un tableau des valeurs complètes des objets
     */
    private String[] remplissageValeurs(String[] values) {
        String[] st = new String[DataModel.getAllColumns().size()];
        int i = 0;
        int pos = 0;

        for(String column : DataModel.getAllColumns()){
            if(dataModel.getStringColumns().contains(column)){
                st[i] = null;
            } else {
                st[i] = values[pos];
                pos++;
            }
            i++;
        }
        return st;
     }

    /**
     * Affiche un pop up d'erreur
     * 
     * @param message Le message à afficher
     */
    protected static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Vérifie si la chaîne de caractères donnée peut être convertie en double.
     * 
     * @param s la chaîne à tester
     * @return true si la chaîne peut être convertie en double, false sinon
     */
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si l'entrée donnée est valide en fonction de l'axe spécifié. La méthode compare
     * la valeur saisie par l'utilisateur aux limites (minimum et maximum) définies pour cet axe.
     * Si la valeur est hors des bornes ou est négative, un message d'alerte est affiché, et une
     * confirmation est demandée à l'utilisateur.
     * 
     * @param s la chaîne représentant la valeur saisie
     * @param axe l'axe auquel la valeur se réfère
     * @return true si l'entrée est valide, false sinon
     */
    
    private boolean isValidEntry(String s, String axe) {
        double test =  Double.parseDouble(s);
        double d = DataModel.maximum.get(axe);
        double m = DataModel.minimum.get(axe);
        if (test <= 0 || test > d || test < m) {
            return false;
        } else {
            return true;
        }
    }
}
