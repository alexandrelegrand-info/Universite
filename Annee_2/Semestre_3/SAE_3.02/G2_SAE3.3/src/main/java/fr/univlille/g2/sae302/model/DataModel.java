package fr.univlille.g2.sae302.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.univlille.g2.sae302.utils.Observable;

/**
 * La classe DataModel représente le modèle qui contient un ensemble
 * de points de données. Elle permet d'ajouter un point et de classer les points
 * non classifiés.
 * 
 * Cette classe hérite d'Observable, ce qui permet de signaler les modifications
 * de l'état du modèle aux observateurs.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class DataModel extends Observable {

    /**
     * Liste des données ajoutées grâce au CSV.
     */
    private Set<Data> dataList;

    /**
     * Liste des données ajoutées par l'utilisateur.
     */
    private Set<Data> dataListAddedByUser;

    /**
     * Liste des axes.
     */
    private static List<String> allColumns = new ArrayList<>();

    /**
     * Liste des axes numériques.
     */
    private static List<String> numericColumns = new ArrayList<>();
    
    /**
     * Liste des axes non numériques.
     */
    private static List<String> stringColumns = new ArrayList<>();

    /**
     * Liste des catégories.
     */
    private static List<String> categoryColumns = new ArrayList<>();

    /**
     * Contient les valeurs minimales pour chaque attribut.
     */
    public static Map<String, Double> minimum = new HashMap<String, Double>();
    
    /**
     * Contient les valeurs maximales pour chaque attribut.
     */
    public static Map<String, Double> maximum = new HashMap<String, Double>();

    /*
     * Catégorie utilisée pour l'affichage
     */
    private String category;

    /*
     * Liste des algorithmes pour les distances
     */
    public static List<String> liste_distances = setListDistances();

    /*
     * Les différentes valeurs possibles pour la catégorie choisie
     * pour la classification
     */
    public static Set<String> categoryValues = new HashSet<>();
    
    /*
     * L'algorithme choisi
     */
    private String algo;

    /*
     * Le k choisi
     */
    private int k;

    /*
     * Le k conseillé avec sa robustesse
     */
    private Entry<Integer, Double> robustesse;

    /*
     * Les axes pour la classification du point par K-NN
     */
    private List<String> axesClassification = new ArrayList<>();


    /**
     * Initialise les ensembles de données
     * des éléments.
     */
    public DataModel() {
        this.dataList = new TreeSet<>();
        this.dataListAddedByUser = new TreeSet<>();
    }

    /**
     * Ajoute un nouveau point de données à la liste des données ajoutées par l'utilisateur.
     * 
     * @param d le point à ajouter
     */
    public void addNewPoint(Data d) {
        dataListAddedByUser.add(d);
        notifyObservers(d); 
    }

    /**
     * Met à jour les valeurs minimales et maximales des attributs des Iris
     * dans les Map minimum et maximum.
     * 
     * @param d le point qui met les données à jour
     */
    public static void updateMinAndMax(Data d){
        for(String axes : numericColumns){
            double value;
            if(d.getValueFromString(axes).isEmpty()) value = 0;
            else value = Double.parseDouble(d.getValueFromString(axes)); 
            if (value < DataModel.minimum.get(axes))
                DataModel.minimum.replace(axes, value);
            if (value > DataModel.maximum.get(axes))
                DataModel.maximum.replace(axes, value);
        }
    }

    /**
     * Permet de récupérer la catégorie d'un point
     * 
     * @param d le point à qui il faut récupérer la catégorie
     */
    public String getValueOfCategory(Data d) {
        return d.getValueFromString(category);
    }

    /**
     * Classe tous les points aléatoirement
     */
    public void randomClassifyPoint(){
        if(!getDataListAddedByUser().isEmpty()){
            for (Data d : getDataListAddedByUser()) {
                int random = (int) (Math.random() * categoryValues.size());
                List<String> listValues = List.copyOf(categoryValues);
                d.setValueFromString(category, listValues.get(random));
            }
            notifyObservers();
        }
    }

    /**
     * Classe tous les points ajoutés par l'utilisateur grâce a l'algorithme
     * K-nn
     */
    
    public void classifyPoints() {
        meilleurK();
        System.out.println(axesClassification.toString());
        for (Data d : getDataListAddedByUser()) {
            Map<Data, Double> nearestNeighbors = new HashMap<>();
            Map<String, Double> poidsPourChaqueCategorie = new HashMap<>();
            nearestNeighbors = MethodeKnn.knn(axesClassification, dataList, k, d, algo);
            for (Map.Entry<Data, Double> entry : nearestNeighbors.entrySet()) {
                if (poidsPourChaqueCategorie.containsKey(entry.getKey().getValueFromString(category))) {
                    poidsPourChaqueCategorie.put(entry.getKey().getValueFromString(category), poidsPourChaqueCategorie.get(entry.getKey().getValueFromString(category)) + entry.getValue());
                } else {
                    poidsPourChaqueCategorie.put(entry.getKey().getValueFromString(category), entry.getValue());
                }
            }
            d.setValueFromString(category, mostCommonCategory(poidsPourChaqueCategorie));
        }
        notifyObservers();
    }

    /**
     * Renvoie la catégorie la plus lourde parmi les k-NN
     * 
     * @param poidsPourChaqueCategorie Les k catégories avec leur poids
     * @return La catégorie la plus lourde
     */
    public String mostCommonCategory(Map<String, Double> poidsPourChaqueCategorie) {
        return poidsPourChaqueCategorie.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /**
     * Affiche le meilleur K possible et sa robustesse pour la distance choisie
     */
    public void meilleurK(){
        if(axesClassification.size() != 0){
            Map<Integer, Double> lesKEtLeursValeurs = new TreeMap<>();
            for (int k = 3; k <= 15; k+=2) {
                double nbCorrect = MethodeKnn.robustesseKnn(getDataList(), k, axesClassification, getCategory(), algo);
                lesKEtLeursValeurs.put(k, nbCorrect* 100 / getDataList().size());
            }
            /*
            ***permet d'afficher les pourcentages de réussites dans le terminal
                for(Map.Entry<Integer, Double> entry : lesKEtLeursValeurs.entrySet()){
                    System.out.println(entry.getValue().toString().replace(".", ","));
                }
            */
            Optional<Entry<Integer, Double>> res = lesKEtLeursValeurs.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(1)
                .findFirst();

                robustesse = res.get();
        } else {
            robustesse = new AbstractMap.SimpleEntry<Integer, Double>(0, 0.0);
        }
    }

    /**
     * Ajoute les différentes distances possibles dans une liste
     * @return La liste des distances possibles
     */
    public static List<String> setListDistances(){
        ArrayList<String> distances = new ArrayList<>();
        distances.add("euclidean");
        distances.add("euclideanNormalisee");
        distances.add("manhattan");
        distances.add("manhattanNormalisee");
        return distances;
    }


    public Set<Data> getDataList() {
        return dataList;
    }

    public void setDataList(Set<Data> dataList) {
        this.dataList = dataList;
    }

    public Set<Data> getDataListAddedByUser() {
        return dataListAddedByUser;
    }

    public List<String> getNumericColumns() {
        return numericColumns;
    }

    public List<String> getStringColumns() {
        return stringColumns;
    }

    public List<String> getCategoryColumns() {
        return categoryColumns;
    }

    public static void setNumericColumns(List<String> numericColumns) {
        DataModel.numericColumns = numericColumns;
    }

    public static void setStringColumns(List<String> stringColumns) {
        DataModel.stringColumns = stringColumns;
    }

    public static void setCategoryColumns(List<String> categoryColumns) {
        DataModel.categoryColumns = categoryColumns;
    }

    public String getCategory() {
        return category;
    }
    
    public static List<String> getAllColumns() {
        return allColumns;
    }

    public static void setAllColumns(List<String> allColumns) {
        DataModel.allColumns = allColumns;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getCategoryValues() {
        return categoryValues;
    }

    public String getAlgo() {
        return algo;
    }

    public int getK() {
        return k;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }
    
    public Entry<Integer, Double> getRobustesse() {
        return robustesse;
    }

    public List<String> getAxesClassification() {
        return axesClassification;
    }

    public void setAxesClassification(List<String> axesClassification) {
        this.axesClassification = axesClassification;
    }

    public void setK(int k) {
        this.k = k;
    }
}