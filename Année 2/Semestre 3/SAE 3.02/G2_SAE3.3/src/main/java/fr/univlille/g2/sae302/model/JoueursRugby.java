package fr.univlille.g2.sae302.model;

import java.util.Objects;

public class JoueursRugby implements Data, Comparable<Object>{
    private String equipe;
    private String nom;
    private String poste;
    private String typePoste;
    private String dateNaissance;
    private int taille;
    private int poids;

    public JoueursRugby(String equipe, String nom, String poste, String typePoste, String dateNaissance, int taille, int poids) {
        this.equipe = equipe;
        this.nom = nom;
        this.poste = poste;
        this.typePoste = typePoste;
        this.dateNaissance = dateNaissance;
        this.taille = taille;
        this.poids = poids;
    }

    /**
     * Récupère la valeur d'un attribut en fonction de son nom.
     * 
     * @param str le nom de l'attribut
     * @return la valeur de l'attribut correspondant, ou 0 si le nom ne correspond pas
     */
    
    @Override
    public String getValueFromString(String str) {
        switch (str) {
            case "Equipe": return getCategory(equipe);
            case "Nom": return getCategory(nom);
            case "Poste": return getCategory(poste);
            case "Type poste": return getCategory(typePoste);
            case "Date de naissance": return getCategory(dateNaissance);
            case "Taille (en cm)": return String.valueOf(taille);
            case "Poids (en kg)": return String.valueOf(poids);
            default: return "";
        }    
    }

    /**
     * Modifie la valeur d'un attribut en fonction de son nom.
     * 
     * @param str le nom de l'attribut
     * @param value la nouvelle valeur de l'attribut
     */

    @Override
    public void setValueFromString(String str, String value) {
        switch (str) {
            case "Equipe": equipe = value; break;
            case "Nom": nom = value; break;
            case "Poste": poste = value; break;
            case "Type poste": typePoste = value; break;
            case "Date de naissance": dateNaissance = value; break;
            case "Taille (en cm)": taille = Integer.parseInt(value); break;
            case "Poids (en kg)": poids = Integer.parseInt(value); break;
        }
    }

    @Override
    public String getCategory(String s) {
        if(s == null) return "null";
        return s;
    }

    @Override
    public int compareTo(Object arg0) {
        if (arg0 instanceof JoueursRugby that) {
            if (this.getNom() == null && that.getNom() == null) {
                // pass
            } else if (this.getNom() == null) {
                return -1;
            } else if (that.getNom() == null) {
                return 1;
            } else {
                int nameComparison = this.getNom().compareTo(that.getNom());
                if (nameComparison != 0) {
                    return nameComparison;
                }
            }
    
            if (this.getEquipe() != that.getEquipe()) {
                return this.getEquipe().compareTo(that.getEquipe());
            }
    
            if (this.getPoste() != that.getPoste()) {
                return this.getPoste().compareTo(that.getPoste());
            }
    
            if (this.getTaille() != that.getTaille()) {
                return Integer.compare(this.getTaille(), that.getTaille());
            }
    
            if (this.getPoids() != that.getPoids()) {
                return Integer.compare(this.getPoids(), that.getPoids());
            }
    
            if (this.getDateNaissance() != that.getDateNaissance()) {
                return this.getDateNaissance().compareTo(that.getDateNaissance());
            }
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipe, nom, poste, typePoste, dateNaissance, taille, poids);
    }

    public String getEquipe() {
        return equipe;
    }

    public String getNom() {
        return nom;
    }

    public String getPoste() {
        return poste;
    }

    public String getTypePoste() {
        return typePoste;
    }


    public String getDateNaissance() {
        return dateNaissance;
    }

    public int getTaille() {
        return taille;
    }

    public int getPoids() {
        return poids;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setTypePoste(String typePoste) {
        this.typePoste = typePoste;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }


    public String toString() {
        return "JoueursRugby [equipe= " + equipe + ", nom= " + nom + ", poste= " + poste + ", typePoste= " + typePoste + ", dateNaissance= " + dateNaissance + ", taille= " + taille + ", poids= " + poids + "]";
    }
    
}
