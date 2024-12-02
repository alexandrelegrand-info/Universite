package fr.univlille.g2.sae302.utils;

import com.opencsv.bean.CsvBindByName;

import fr.univlille.g2.sae302.model.*;

public class JoueursRugbyDonneeBrut implements DonneeBrut {
    @CsvBindByName(column = "Equipe")
    private String equipe;
    @CsvBindByName(column = "Nom")
    private String nom;
    @CsvBindByName(column = "Poste")
    private String poste;
    @CsvBindByName(column = "Type poste")
    private String typePoste;
    @CsvBindByName(column = "Date de naissance")
    private String dateNaissance;
    @CsvBindByName(column = "Taille (en cm)")
    private int taille;
    @CsvBindByName(column = "Poids (en kg)")
    private int poids;

    /**
     * Convertit une DonneeBrut en joueur de rugby.
     * 
     * @param d L'objet DonneeBrut à convertir.
     * @return Un objet Iris représentant les données converties.
     */
    
    @Override
    public Data convert() {
        JoueursRugby joueur = new JoueursRugby(getEquipe(), getNom(), getPoste(), getTypePoste(), getDateNaissance(),
                getTaille(), getPoids());
        DataModel.updateMinAndMax(joueur);
        return (Data) joueur;
    }

    public String getEquipe() {
        return equipe;
    }

    public String getNom() {
        return nom;
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

}