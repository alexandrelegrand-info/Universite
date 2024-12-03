package fr.univlille.g2.sae302.controller;

import fr.univlille.g2.sae302.utils.ChargementDonnees;
import fr.univlille.g2.sae302.utils.DonneeBrut;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.LoadCSV;
import fr.univlille.g2.sae302.view.DataView;

import java.util.List;
import java.util.Set;
/**
 * Le contrôleur principal de l'application qui gère les interactions entre le modèle, la vue
 * et les événements utilisateur. Il gère la navigation entre les différentes scènes,
 * le chargement des données et l'ajout de nouveaux points au graphique.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class ApplicationController {

    private DataModel dm; 
    private DataView dv;    

    private Parent root;    
    private Scene scene;    
    private Stage stage;   

    private File selected;
    private String separator;
    private String[] separators = new String[]{",", "\t"};

    @FXML
    Label error;
    @FXML
    ComboBox<String> listAxisX;
    @FXML
    ComboBox<String> listAxisY;
    @FXML
    ComboBox<String> category;

    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    ScatterChart<Double, Double> scatter;

    /**
     * Constructeur de la classe ApplicationController.
     * 
     * @param dm Le modèle.
     * @param dv La vue.
     */
    public ApplicationController(DataModel dm, DataView dv) {
        this.dm = dm;
        this.dv = dv;
        dm.attach(dv);
    }

    /**
     * Constructeur par défaut qui initialise le modèle et la vue par défaut.
     */
    public ApplicationController() {        
        this(new DataModel(), new DataView(null));
    }

    /**
     * Switch vers la première scène (Chargement d'un fichier).
     * 
     * @param a L'événement ActionEvent.
     */
    public void switchScene1(ActionEvent a) {
        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("Explorateur_Fichier.fxml");
            root = FXMLLoader.load(fxmlFileUrl);

            DataModel.categoryValues.removeAll(DataModel.categoryValues);

            scene = new Scene(root);
            stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Classification");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Switch vers la seconde scène (Chargement des axes).
     * 
     * @param a L'événement ActionEvent.
     */
    public void switchScene2(ActionEvent a){
        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("Chargement_Axes.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
            Parent root = loader.load();
            ApplicationController applicationController = loader.getController();
            applicationController.setDm(this.dm);
            applicationController.setDv(this.dv);
            applicationController.setSeparator(this.separator);
            applicationController.setSelected(this.selected);
            applicationController.getDv().setAc(applicationController);
            applicationController.setListAxes();
            applicationController.setCategory();
            scene = new Scene(root);
            stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Classification");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Switch vers la troisième scène (Graphique).
     * 
     * @param a L'événement ActionEvent.
     */
    
    public void switchScene3(ActionEvent a) {
        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("Nuage_Point.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
            Parent root = loader.load();

            ApplicationController applicationController = loader.getController();
            applicationController.setDm(this.dm);
            applicationController.setDv(this.dv);
            applicationController.setSeparator(this.separator);
            applicationController.setSelected(this.selected);
            applicationController.getDv().setAc(applicationController);
            applicationController.setListAxesAction();
            applicationController.dv.draw();
        
            scene = new Scene(root);
            stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Classification");
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void setCategory(){
        category.setItems(FXCollections.observableArrayList(getDm().getCategoryColumns()));
        category.setOnAction(e -> {
            dm.setCategory(category.getValue());
        });
    }

    /**
     * Remplit les ComboBox pour la sélection des axes.
     */
    public void setListAxes() {
        listAxisX.setItems(FXCollections.observableArrayList(getDm().getNumericColumns()));
        listAxisY.setItems(FXCollections.observableArrayList(getDm().getNumericColumns()));
        listAxisX.setOnAction(e -> {
            dv.setxAxis(listAxisX.getValue());
        });
        listAxisY.setOnAction(e -> {
            dv.setyAxis(listAxisY.getValue());
        });
    }

    /**
     * Configure les actions des ComboBox pour la sélection des axes.
     */
    public void setListAxesAction() {
        setListAxes();
        listAxisX.setValue(dv.getxAxis());
        listAxisY.setValue(dv.getyAxis());

        listAxisX.setOnAction(e -> {
            dv.setxAxis(listAxisX.getValue());
            dv.update(dm);
        });
        listAxisY.setOnAction(e -> {
            dv.setyAxis(listAxisY.getValue());
            dv.update(dm);
        });
    }

    /**
     * Crée une nouvelle vue pour afficher un nouveau nuage de points.
     * 
     * @param e L'événement ActionEvent.
     */
    public void createAView(ActionEvent e) {
        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("Nouvelle_Vue.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
            Parent root = loader.load();

            ApplicationController applicationController = loader.getController();
            String x = dv.getxAxis();
            String y = dv.getyAxis();
            applicationController.setDm(this.dm);
            applicationController.setDv(new DataView(null));
            applicationController.getDv().setAc(applicationController);
            applicationController.setSeparator(this.separator);
            applicationController.setSelected(this.selected);
            applicationController.setListAxesAction();
            applicationController.getDv().setAxis(x, y);
            applicationController.listAxisX.setPromptText(x);
            applicationController.listAxisY.setPromptText(y);
            applicationController.getDm().attach(applicationController.getDv());
            applicationController.getDv().draw();
        
            newViewScene(root, applicationController.getDv());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Crée une nouvelle vue pour afficher un nouveau nuage de points.
     * 
     * @param root La racine de la scène.
     * @param newView La nouvelle vue à afficher.
     */
    private void newViewScene(Parent root, DataView newView){
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Classification - Nouvelle Vue");
        stage.show();

        stage.setOnCloseRequest(event -> {
            dm.detach(newView);
            System.out.println("Vue fermée et désabonnée");
        });
    }

    /**
     * Charge les données à partir d'un fichier CSV.
     * 
     * @param e L'événement ActionEvent.
     * @throws IOException Si une erreur se produit lors de la lecture du fichier.
     */
    public void loadData(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage s = new Stage();
        selected = fileChooser.showOpenDialog(s);
        Class<? extends DonneeBrut> c = null;
        for(int i = 0; i<separators.length; i++){
            List<String> l = LoadCSV.premièreLigneCSV(selected, separators[i]);
            if(l != null){
                separator = separators[i];
                break;
            }
        }
        try{
            DataModel.setAllColumns(LoadCSV.premièreLigneCSV(selected, separator));
            System.out.println("Toutes les colonnes : " + DataModel.getAllColumns().toString());
            c = ChargementDonnees.classFinder(DataModel.getAllColumns().toString());
            System.out.println(c);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if (c != null) {
            DataModel.setNumericColumns(LoadCSV.detectNumericColumns(selected, separator));
            System.out.println("Toutes les colonnes numériques : " + getDm().getNumericColumns().toString());
            DataModel.setStringColumns(LoadCSV.detectStringColumns(selected, separator));
            System.out.println("Toutes les catégories : " + getDm().getStringColumns().toString());
            List<DonneeBrut> list = ChargementDonnees.charger(selected, c);
            Set<Data> datas = ChargementDonnees.dataList(list);
            getDm().setDataList(datas);
            DataModel.setCategoryColumns(LoadCSV.detectCategoryColumns(getDm().getStringColumns(), dm.getDataList()));
            System.out.println("Toutes les catégories possibles : " + getDm().getCategoryColumns().toString());

            switchScene2(e);
        } else {
            error.setVisible(true);
        }
    }

    /**
     * Ajoute un nouveau point à partir des valeurs saisies dans les champs de texte.
     * 
     * @param a L'événement ActionEvent.
     */
    public void addNewPoint(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Ajout_Point.fxml"));
            Parent root = loader.load();
    
            AjoutPointController ajoutPointController = loader.getController();
            ajoutPointController.setDataModel(dm, selected.getName()); // Injection de l'instance DataModel
     
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajout d'un nouveau point");
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Erreur lors de l'ouverture du pop-up d'ajout de point : " + ex.getMessage());
        }
    }
    

    /**
     * Classe tous les points du modèle en leur ajoutant une variété.
     * 
     * @param e L'événement ActionEvent.
     */
    public void classifyAllPoints(ActionEvent e) {
        //pop up scene pour choisir la classification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Classifier_Point.fxml"));
            Parent root = loader.load();
    
            ClassifyController classifyController = loader.getController();
            classifyController.setDataModel(dm); // Injection de l'instance DataModel
     
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Classification des nouveaux points");
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Erreur lors de l'ouverture du pop-up de classification : " + ex.getMessage());
        }
    }

    public void randomClassifyPoint(){
        dm.randomClassifyPoint();
    }

    public DataModel getDm() {
        return dm;
    }

    public void setDm(DataModel dm) {
        this.dm = dm;
    }

    public DataView getDv() {
        return dv;
    }

    public void setDv(DataView dv) {
        this.dv = dv;
    }

    public ScatterChart<Double, Double> getScatter() {
        return scatter;
    }

    public void setScatter(ScatterChart<Double, Double> scatter) {
        this.scatter = scatter;
    }

    public NumberAxis getxAxis() {
        return xAxis;
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }

    public File getSelected() {
        return selected;
    }

    public void setSelected(File selected) {
        this.selected = selected;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}