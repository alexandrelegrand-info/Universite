package fr.univlille.g2.sae302.view;

import fr.univlille.g2.sae302.controller.ApplicationController;
import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.LoadCSV;
import fr.univlille.g2.sae302.utils.Observable;
import fr.univlille.g2.sae302.utils.Observer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe représentant la vue pour afficher les informations
 * des Iris sous forme de nuage de points. Cette classe
 * implémente le modèle Observer pour réagir aux mises à jour des données.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class DataView implements Observer {

    private ApplicationController applicationController;
    private String xAxis, yAxis;

    private String[] color;
    private String[] shape;
    private int cpt = 0;

    private Map<String, Integer> categoryColorMap = new HashMap<>();

    public DataView(ApplicationController ac) {
        this.applicationController = ac;
        this.color = generateDistinctColors();
        this.shape = new String[] { "-fx-shape: 'M 0,0 m -5,0 a 5,5 0 1,0 10,0 a 5,5 0 1,0 -10,0';",
                                    "-fx-shape: 'M -5,-5 L 5,-5 L 5,5 L -5,5 Z';" };
    }

    public static String[] generateDistinctColors() {
        return new String[] {
                "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF",
                "#800000", "#808000", "#008000", "#800080", "#008080",
                "#FFA500", "#A52A2A", "#5F9EA0", "#7FFF00", "#D2691E", "#6495ED",
                "#DC143C", "#00CED1", "#1E90FF", "#FFD700", "#ADFF2F", "#4B0082",
                "#F08080", "#90EE90", "#FFB6C1", "#B0C4DE", "#FFDAB9", "#7B68EE",
                "#6A5ACD", "#48D1CC", "#C71585", "#191970", "#FF6347", "#2E8B57",
                "#8B0000", "#DA70D6", "#EEE8AA", "#B22222", "#32CD32", "#FA8072",
                "#66CDAA", "#BA55D3", "#CD5C5C", "#FFA07A", "#20B2AA", "#9370DB"
        };
    }

    public void setAxis(String xAxis, String yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }


    /**
     * Configure le nuage de points.
     */
    private void setScatter() {
        applicationController.getScatter().getData().clear();
        applicationController.getScatter().setTitle(applicationController.getDm().getCategory());

        applicationController.getxAxis().setAutoRanging(false);
        applicationController.getxAxis().setLowerBound(Math.floor(DataModel.minimum.get(getxAxis())));
        applicationController.getxAxis().setUpperBound(Math.ceil(DataModel.maximum.get(getxAxis())));
        applicationController.getxAxis().setTickUnit((Math.ceil(DataModel.maximum.get(getxAxis())) - Math.floor(DataModel.minimum.get(getxAxis()))) / 10);
        applicationController.getxAxis().setLabel(getxAxis());

        applicationController.getyAxis().setAutoRanging(false);
        applicationController.getyAxis().setLowerBound(Math.floor(DataModel.minimum.get(getyAxis())));
        applicationController.getyAxis().setUpperBound(Math.ceil(DataModel.maximum.get(getyAxis())));
        applicationController.getyAxis().setTickUnit((Math.ceil(DataModel.maximum.get(getyAxis())) - Math.floor(DataModel.minimum.get(getyAxis()))) / 10);
        applicationController.getyAxis().setLabel(getyAxis());
    }

    /**
     * Exécute une tâche sur le thread de l'interface graphique.
     */
    private void runLater() {
        Platform.runLater(() -> {
            applicationController.getScatter().applyCss();
            applicationController.getScatter().layout();
            applyLegendStyles();
        });
    }

    /**
     * Ajoute une infobulle à un point de données.
     * 
     * @param dataPoint Le point de données.
     * @param data      Les données associées.
     */
    private void addTooltipToDataPoint(XYChart.Data<Double, Double> dataPoint, Data data) {
        Tooltip tooltip = new Tooltip();
        List<String> header = DataModel.getAllColumns();

        StringBuilder sb = new StringBuilder();

        for (String column : header) {
            String value = data.getValueFromString(column);
            sb.append(column)
                    .append(" : ")
                    .append(!value.equals("null") ? value : "NON CLASSIFIEE")
                    .append("\n");
        }

        tooltip.setText(sb.toString());
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setHideDelay(Duration.millis(200));
        Tooltip.install(dataPoint.getNode(), tooltip);
    }

    /**
     * Applique un style aux points de données en fonction de leur variété.
     * 
     * @param style   Le style CSS à appliquer.
     * @param series  La série de données à mettre à jour.
     * @param category La catégories des données.
     */

    private void drawSymbols(String style, XYChart.Series<Double, Double> series, String category) {
        for (XYChart.Data<Double, Double> dataPoint : series.getData()) {
            Node node = dataPoint.getNode();
            if (node != null) {
                if (category == "null") node.setStyle("-fx-background-color: grey; " + style);
                else node.setStyle("-fx-background-color: " + color[cpt] + "; " + style);
            }
            Data d = findDataByCoordinates(dataPoint.getXValue(), dataPoint.getYValue(), category);
            if (d != null) addTooltipToDataPoint(dataPoint, d);
        }
        if (cpt >= color.length - 1) cpt = 0;
    }

    /**
     * Trouve les données correspondant à des coordonnées spécifiques.
     * 
     * @param x        La coordonnée x.
     * @param y        La coordonnée y.
     * @param category La catégorie des données.
     * @return Les données correspondant aux coordonnées spécifiées.
     */
    private Data findDataByCoordinates(Double x, Double y, String category) {
        for (Data data : getAc().getDm().getDataList()) {
            if (applicationController.getDm().getValueOfCategory(data).equalsIgnoreCase(category)) {
                double xValue = Double.parseDouble(data.getValueFromString(getxAxis()));
                double yValue = Double.parseDouble(data.getValueFromString(getyAxis()));
                if (x.equals(xValue) && y.equals(yValue)) return data;
            }
        }
        for (Data data : getAc().getDm().getDataListAddedByUser()) {
            if (applicationController.getDm().getValueOfCategory(data).equalsIgnoreCase(category)) {
                double xValue = Double.parseDouble(data.getValueFromString(getxAxis()));
                double yValue = Double.parseDouble(data.getValueFromString(getyAxis()));
                if (x.equals(xValue) && y.equals(yValue)) return data;
            }
        }
        return null;
    }

    /**
     * Crée une série de données pour le nuage de points.
     * 
     * @param nom    Le nom de la série.
     * @param dataSet L'ensemble de données à utiliser.
     * @return La série de données créée.
     */

    private XYChart.Series<Double, Double> createSeries(String nom, Set<Data> dataSet) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        boolean empty = true;
        if(nom.isEmpty()) nom = "Aucun";
        series.setName(nom);
        String cleanNom = nom.replace(" (USER ADDED)", "");
        for (Data data : dataSet) {
            if (applicationController.getDm().getValueOfCategory(data).equalsIgnoreCase(cleanNom) || applicationController.getDm().getValueOfCategory(data).equalsIgnoreCase(cleanNom) || cleanNom.equals("Aucun") || applicationController.getDm().getValueOfCategory(data).equals("null") && cleanNom.equals("NON CLASSIFIEE")){
                series.getData().add(new XYChart.Data<>(Double.parseDouble(data.getValueFromString(getxAxis())),Double.parseDouble(data.getValueFromString(getyAxis()))));
                empty = false;
            }
        }
        if(empty && !nom.equals("NON CLASSIFIEE")) return null;
        return series;
    }

    /**
     * Récupère toutes les catégories d'une colonne.
     * 
     * @param columnName Le nom de la colonne.
     * @return Un ensemble de catégories.
     */

    public Set<String> allCategory(String columnName) throws IOException {
        Set<String> categories = new HashSet<>();
        String[][] data = LoadCSV.lireFichierCSV(applicationController.getSelected(), applicationController.getSeparator());

        List<String> headers = DataModel.getAllColumns();
        int columnIndex = 0;

        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).replace("\"", "").equalsIgnoreCase(columnName)) {
                columnIndex = i;
                break;
            }
        }

        for (String[] row : data) {
            if (columnIndex < row.length) categories.add(row[columnIndex].replace("\"", "").toUpperCase());
        }
        System.out.println(categories.toString());
        return categories;
    }

    /**
     * Dessine les données dans le nuage de points en fonction des variétés.
     */
    public void draw() throws IOException {
        if(DataModel.categoryValues.isEmpty()) DataModel.categoryValues.addAll(allCategory(applicationController.getDm().getCategory())); 
        setScatter();
        cpt = 0;
        for (String category : DataModel.categoryValues) {
            categoryColorMap.putIfAbsent(category, cpt);
            XYChart.Series<Double, Double> series = createSeries(category, getAc().getDm().getDataList());
            applicationController.getScatter().getData().add(series);
            drawSymbols(shape[0], series, category);

            XYChart.Series<Double, Double> addedSeries = createSeries(category + " (USER ADDED)", getAc().getDm().getDataListAddedByUser());
            if(addedSeries != null){
                applicationController.getScatter().getData().add(addedSeries);
                drawSymbols(shape[1], addedSeries, category);
            }
            cpt++;
        }
        XYChart.Series<Double, Double> series = createSeries("NON CLASSIFIEE", getAc().getDm().getDataListAddedByUser());
        if(series != null){
            applicationController.getScatter().getData().add(series);
            drawSymbols(shape[1], series, "null");
        }

        runLater();
    }

    /**
     * Dessine un point de données dans le nuage de points.
     * 
     * @param point Le point de données à dessiner.
     */
    private void draw(Data i) throws IOException {
        cpt = 0;
        double xValue = Double.parseDouble(i.getValueFromString(getxAxis()));
        double yValue = Double.parseDouble(i.getValueFromString(getyAxis()));

        if (xValue <= Math.floor(DataModel.minimum.get(getxAxis())) ||
            xValue >= Math.ceil(DataModel.maximum.get(getxAxis())) ||
            yValue <= Math.floor(DataModel.minimum.get(getyAxis())) ||
            yValue >= Math.ceil(DataModel.maximum.get(getyAxis()))) {

            draw();
        } else {
            XYChart.Series<Double, Double> targetSeries = null;
            for (XYChart.Series<Double, Double> series : applicationController.getScatter().getData()) {
                if (applicationController.getDm().getValueOfCategory(i).equals("null") && series.getName().equals("NON CLASSIFIEE")) {
                    targetSeries = series;
                    break;
                }
            }
            XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(xValue, yValue);
            if (targetSeries != null) {
                targetSeries.getData().add(dataPoint);
                Node node = dataPoint.getNode();
                if (node != null) {
                    node.setStyle("-fx-background-color: grey;" + shape[1]);
                    addTooltipToDataPoint(dataPoint, i);
                }
                runLater();
            }
        }
    }

    /**
     * Applique des styles aux symboles de légende en fonction des séries de
     * données.
     */
    private void applyLegendStyles() {
        ObservableList<XYChart.Series<Double, Double>> seriesList = applicationController.getScatter().getData();

        int seriesIndex = 0;
        for (Node legend : applicationController.getScatter().lookupAll(".chart-legend-item-symbol")) {
            if (legend instanceof Region) {
                Region legendSymbol = (Region) legend;

                String seriesName = seriesList.get(seriesIndex).getName().toUpperCase();
                String cleanName = seriesName.replace(" (USER ADDED)", "").toUpperCase();
                if (cleanName.equals("AUCUN")) {
                    cleanName = "";
                }
                
                String style;
                if (cleanName.equals("NON CLASSIFIEE")) {
                    style = "-fx-background-color: grey; " + shape[1];
                } else{
                    int colorIndex = categoryColorMap.get(cleanName);
                    if (seriesName.contains("USER ADDED")) {
                        style = "-fx-background-color: " + color[colorIndex % color.length] + "; " + shape[1];
                    } else {
                        style = "-fx-background-color: " + color[colorIndex % color.length] + "; " + shape[0];
                    }
                }
    
                legendSymbol.setStyle(style);
                seriesIndex++;
            }
        }
    }

    @Override
    public void update(Observable observable) {
        try {
            draw();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof Data d) {
            try {
                draw(d);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public String getyAxis() {
        return yAxis;
    }

    public String getxAxis() {
        return xAxis;
    }

    public ApplicationController getAc() {
        return applicationController;
    }

    public void setAc(ApplicationController ac) {
        this.applicationController = ac;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;

    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }
}