package fr.univlille.g2.sae302.utils;

import com.opencsv.bean.CsvBindByName;
import fr.univlille.g2.sae302.model.Data;
import fr.univlille.g2.sae302.model.DataModel;
import fr.univlille.g2.sae302.model.Iris;
import fr.univlille.g2.sae302.model.Variety;

/**
 * Représente une donnée brute extraite d'un fichier CSV contenant Iris.
 * Cette classe est utilisée pour stocker les caractéristiques de chaque fleur.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public class IrisDonneeBrut implements DonneeBrut{

    @CsvBindByName(column = "sepal.length")
    private Double sepallength;  
    @CsvBindByName(column = "sepal.width")
    private Double sepalwidth;   
    @CsvBindByName(column = "petal.length")
    private Double petallength;  
    @CsvBindByName(column = "petal.width")
    private Double petalwidth;   
    @CsvBindByName(column = "variety")
    private String variety;

    /**
     * Constructeur par défaut de la classe DonneeBrut.
     */
    public IrisDonneeBrut() {}

    /**
     * Convertit une DonneeBrut en une Iris.
     * 
     * @param d L'objet DonneeBrut à convertir.
     * @return Un objet Iris représentant les données converties.
     */
    
    public Data convert() {
        Iris iris = new Iris(getPetallength(), getPetalwidth(), getSepallength(), getSepalwidth(), getVariety());
        DataModel.updateMinAndMax(iris);
        return (Data) iris;
    }

    public double getSepallength() {
        return sepallength;
    }

    public double getSepalwidth() {
        return sepalwidth;
    }

    public double getPetallength() {
        return petallength;
    }

    public double getPetalwidth() {
        return petalwidth;
    }

    public Variety getVariety() {
        return Variety.valueOf(variety.toUpperCase());
    }
}