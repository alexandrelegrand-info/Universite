package fr.univlille.g2.sae302.controller;

import fr.univlille.g2.sae302.model.DataModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


/**
 * Le contrôleur pour la classification d'un point.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class ClassifyController {

    @FXML
    private GridPane axesGrid;

    @FXML
    private Label k;

    @FXML
    private ComboBox<String> comboDistance;

    @FXML
    private TextField kValue;

    private DataModel dataModel;
    private Map<String, CheckBox> checkFields = new HashMap<>();

    /**
     * Injecte le modèle
     * 
     * @param dataModel Modèle de données
     * 
     */ 
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        setupForm();
    }

    /**
     * Crée dynamiquement les checkbox pour les axes numériques
     */
    private void setupForm() {
        comboDistance.setItems(FXCollections.observableArrayList(DataModel.liste_distances));
        comboDistance.getSelectionModel().selectFirst();
        setDistance();
        int row = 0;
        for (String axis : dataModel.getNumericColumns()) { 
            CheckBox checkField = new CheckBox(axis);
            checkField.setSelected(true);
            axesGrid.add(checkField, 0, row);

            dataModel.getAxesClassification().add(axis);
            checkFields.put(axis, checkField);
            row++;
        }
        dataModel.meilleurK();
        majLabel();
        getCheckAxes();
    }

    /**
     * Met à jour l'affichage du label
     */
    private void majLabel(){
        k.setText("Le meilleur k pour l'algo " + dataModel.getAlgo() + " est " + dataModel.getRobustesse().getKey() + " ("+ String.format("%.02f", dataModel.getRobustesse().getValue()) +"%)");
    }

    /**
     * Met à jour le label et l'algo choisi en temps réel
     */
    private void setDistance(){
        dataModel.setAlgo(comboDistance.getValue());
        comboDistance.setOnAction(e -> {
            dataModel.setAlgo(comboDistance.getValue());
            dataModel.meilleurK();
            majLabel();
        });
    }

    /**
     * Met à jour l'attribut k du DataModel
     * @return true si l'opération a été effectué ou false sinon
     */
    private boolean setKValue(){
        if(isInt(kValue.getText())) {
            dataModel.setK(Integer.parseInt(kValue.getText()));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Vérifie si le paramètre peut être caster en int
     * 
     * @param s L'entrée utilisateur
     * @return vrai si c'est un int, false sinon
     */
    private static boolean isInt(String s){
        boolean res = false;
        try{
            Integer.parseInt(s);
            res = true;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return res;
    }

    /**
     * Met à jour le label selon si la checkbox est cochée ou non
     */
    private void getCheckAxes() {        
        for (Map.Entry<String, CheckBox> entry : checkFields.entrySet()) {
            String fieldName = entry.getKey();
            CheckBox checkBox = entry.getValue();

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    if (!dataModel.getAxesClassification().contains(fieldName)) {
                        dataModel.getAxesClassification().add(fieldName);
                    }
                } else {
                    dataModel.getAxesClassification().remove(fieldName);
                }
                dataModel.meilleurK();
                majLabel();
            });
        }
    }

    /**
     * Appelle la méthode de classification des points si les entrées utilisateurs
     * sont toutes valides.
     * La méthode appelle un pop up d'erreur dans le cas contraire.
     */
    
    @FXML
    private void classifyPoint(){
        if(setKValue() && dataModel.getAxesClassification().size() != 0){ 
            if(!dataModel.getDataListAddedByUser().isEmpty()){
                dataModel.classifyPoints();
                Stage stage = (Stage) axesGrid.getScene().getWindow();
                stage.close();
            } else {
                AjoutPointController.showError("Aucun point à classifier");
            }
        }
        else AjoutPointController.showError("N'oubliez pas de vérifier que vous avez bien coché au moins une case et que K soit un entier");
    }
}
