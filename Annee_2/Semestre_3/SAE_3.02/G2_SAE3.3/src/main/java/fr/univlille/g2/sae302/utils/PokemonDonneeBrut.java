package fr.univlille.g2.sae302.utils;

import com.opencsv.bean.CsvBindByName;

import fr.univlille.g2.sae302.model.*;

public class PokemonDonneeBrut implements DonneeBrut {

    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "attack")
    private int attack;
    @CsvBindByName(column = "base_egg_steps")
    private int baseEggSteps;
    @CsvBindByName(column = "capture_rate")
    private double captureRate;
    @CsvBindByName(column = "defense")
    private int defense;
    @CsvBindByName(column = "experience_growth")
    private int experienceGrowth;
    @CsvBindByName(column = "hp")
    private int hp;
    @CsvBindByName(column = "sp_attack")
    private int spAttack;
    @CsvBindByName(column = "sp_defense")
    private int spDefense;
    @CsvBindByName(column = "type1")
    private String type1;
    @CsvBindByName(column = "type2")
    private String type2;
    @CsvBindByName(column = "speed")
    private double speed;
    @CsvBindByName(column = "is_legendary")
    private String isLegendary;

    /**
     * Constructeur par défaut de la classe PokemonDonneeBrut.
     */
    public PokemonDonneeBrut() {}

    /**
     * Convertit un PokemonDonneeBrut en un objet Pokemon.
     *
     * @return Un objet Pokemon représentant les données converties.
     */
    
    public Data convert() {
        Pokemon pokemon = new Pokemon(
            getName(),
            getAttack(),
            getBaseEggSteps(),
            getCaptureRate(),
            getDefense(),
            getExperienceGrowth(),
            getHp(),
            getSpAttack(),
            getSpDefense(),
            getType1(),
            getType2(),
            getSpeed(),
            isLegendary()
        );
        DataModel.updateMinAndMax(pokemon);
        return (Data) pokemon;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getBaseEggSteps() {
        return baseEggSteps;
    }

    public double getCaptureRate() {
        return captureRate;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperienceGrowth() {
        return experienceGrowth;
    }

    public int getHp() {
        return hp;
    }

    public int getSpAttack() {
        return spAttack;
    }

    public int getSpDefense() {
        return spDefense;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public double getSpeed() {
        return speed;
    }

    public String isLegendary() {
        return isLegendary;
    }

    
}