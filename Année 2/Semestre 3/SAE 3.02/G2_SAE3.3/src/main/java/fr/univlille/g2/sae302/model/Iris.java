package fr.univlille.g2.sae302.model;

import java.util.*;

/**
 * La classe Iris modélise une fleur en intégrant ses caractéristiques 
 * physiques telles que la longueur et la largeur des pétales, ainsi que la  
 * longueur et la largeur des sépales.
 * Elle permet également d'identifier la variété de la fleur (Setosa, Versicolor, Virginica).
 * 
 * Cette classe est utilisée pour stocker et manipuler des données liées aux 
 * dans un cadre de classification et d'analyse de données.
 * Elle prend en charge la comparaison entre différentes fleurs d'en fonction 
 * de leurs caractéristiques et de leur variété, permettant ainsi de les ordonner.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */

public class Iris implements Data, Comparable<Object> {

    private Variety variety;
    private double petalLength;
    private double petalWidth;
    private double sepalLength;
    private double sepalWidth;

    

    /**
     * Constructeur de la classe Iris.
     * 
     * @param petalLength longueur des pétales
     * @param petalWidth largeur des pétales
     * @param sepalLength longueur des sépales
     * @param sepalWidth largeur des sépales
     * @param variety la variété
     */
    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth,  Variety variety) {
        this.variety = variety;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
    }

    /**
     * Récupère la valeur d'un attribut en fonction de son nom.
     * 
     * @param str le nom de l'attribut
     * @return la valeur de l'attribut correspondant, ou 0 si le nom ne correspond pas
     */
    public String getValueFromString(String str) {
        switch (str) {
            case "sepal.length": return String.valueOf(sepalLength);
            case "sepal.width": return String.valueOf(sepalWidth);
            case "petal.length": return String.valueOf(petalLength);
            case "petal.width": return String.valueOf(petalWidth);
            case "variety" : return getCategory(""+variety);
            default : return "";
        }
    }

    /**
     * Modifie la valeur d'un attribut en fonction de son nom.
     * 
     * @param str le nom de l'attribut
     * @param value la nouvelle valeur de l'attribut
     */
    public void setValueFromString(String str, String value) {
        switch (str) {
            case "sepal.length": sepalLength = Double.parseDouble(value); break;
            case "sepal.width": sepalWidth = Double.parseDouble(value); break;
            case "petal.length": petalLength = Double.parseDouble(value); break;
            case "petal.width": petalWidth = Double.parseDouble(value); break;
            case "variety" : setVariety(Variety.valueOf(value)); break;
        }
    }

    /**
     * Attribue une variété aléatoire à la fleur.
     */
    public void randomVariety() {
        Random r = new Random();
        int i = r.nextInt(3);
        switch (i) {
            case 0:
                setVariety(Variety.SETOSA);
                break;
            case 1:
                setVariety(Variety.VERSICOLOR);
                break;
            case 2:
                setVariety(Variety.VIRGINICA);
                break;
        }
    }

    /**
     * Vérifie si deux Iris sont égales en comparant leurs attributs.
     * 
     * @param o l'objet à comparer
     * @return true si les attributs des deux sont les mêmes, false sinon
     */
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Iris i = (Iris) o;
        return Double.compare(petalLength, i.petalLength) == 0 && Double.compare(petalWidth, i.petalWidth) == 0 
            && Double.compare(sepalLength, i.sepalLength) == 0 && Double.compare(sepalWidth, i.sepalWidth) == 0 
            && Objects.equals(variety, i.variety);
    }

    /**
     * Retourne une représentation sous forme de String de l'
     * 
     * @return une chaîne de caractères représentant l'     */
    @Override
    public String toString() {
        return "Iris [variety=" + variety + ", petalLength=" + petalLength + ", petalWidth=" + petalWidth
                + ", sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + "]";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Iris that) {
            if (this.getCategory(""+variety) == "null" && that.getCategory(""+variety) == "null") {
                // pass
            } else if (this.getCategory(""+variety) == "null") {
                return -1;
            } else if (that.getCategory(""+variety) == "null") {
                return 1;
            } else {
                int varietyComparison = this.getCategory(""+variety).compareTo(that.getCategory(""+variety));
                if (varietyComparison != 0) {
                    return varietyComparison < 0 ? -1 : 1;
                }
            }

            if (this.getPetalLength() != that.getPetalLength()) {
                return (this.getPetalLength() < that.getPetalLength() ? -1 : 1);
            }

            if (this.getPetalWidth() != that.getPetalWidth()) {
                return (this.getPetalWidth() < that.getPetalWidth() ? -1 : 1);
            }

            if (this.getSepalLength() != that.getSepalLength()) {
                return (this.getSepalLength() < that.getSepalLength() ? -1 : 1);
            }

            if (this.getSepalWidth() != that.getSepalWidth()) {
                return (this.getSepalWidth() < that.getSepalWidth() ? -1 : 1);
            }
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(variety, petalLength, petalWidth, sepalLength, sepalWidth);
    }

    public String getCategory(String s) {
        if(s == "") return "null";
        return s;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public void setPetalLength(double petalLength) {
        this.petalLength = petalLength;
    }

    public void setPetalWidth(double petalWidth) {
        this.petalWidth = petalWidth;
    }

    public void setSepalLength(double sepalLength) {
        this.sepalLength = sepalLength;
    }

    public void setSepalWidth(double sepalWidth) {
        this.sepalWidth = sepalWidth;
    }
}