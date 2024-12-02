package fr.univlille.g2.sae302.model;

import java.util.Objects;

/**
 * La classe Pokémon modélise un pokémon avec ses différentes statistiques d'attaque ou de défense,
 * il possède également des attributs non numériques comme ses types ou s'il est légendaire.
 * 
 * Cette classe est utilisée pour stocker et manipuler des données liées aux 
 * dans un cadre de classification et d'analyse de données.
 * Elle prend en charge la comparaison entre différents pokémons d'en fonction 
 * de leurs statistiques et de leur spécificités, permettant ainsi de les ordonner.
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */

public class Pokemon implements Data, Comparable<Object> {
    private String name;
    private int attack;
    private int baseEggSteps;
    private double captureRate;
    private int defense;
    private int experienceGrowth;
    private int hp;
    private int spAttack;
    private int spDefense;
    private String type1;
    private String type2;
    private double speed;
    private String isLegendary;

    public Pokemon(String name, int attack, int baseEggSteps, double captureRate, int defense, int experienceGrowth,
                   int hp, int spAttack, int spDefense, String type1, String type2, double speed, String isLegendary) {
        this.name = name;
        this.attack = attack;
        this.baseEggSteps = baseEggSteps;
        this.captureRate = captureRate;
        this.defense = defense;
        this.experienceGrowth = experienceGrowth;
        this.hp = hp;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.type1 = type1;
        this.type2 = type2;
        this.speed = speed;
        this.isLegendary = isLegendary;
    }

    /**
     * Récupère la valeur d'un attribut en fonction de son nom.
     * 
     * @param str le nom de l'attribut
     * @return la valeur de l'attribut correspondant, ou 0 si le nom ne correspond pas
     */
    
    public String getValueFromString(String str) {
        switch (str) {
            case "name": return getCategory(name);
            case "attack": return String.valueOf(attack);
            case "base_egg_steps": return String.valueOf(baseEggSteps);
            case "capture_rate": return String.valueOf(captureRate);
            case "defense": return String.valueOf(defense);
            case "experience_growth": return String.valueOf(experienceGrowth);
            case "hp": return String.valueOf(hp);
            case "sp_attack": return String.valueOf(spAttack);
            case "sp_defense": return String.valueOf(spDefense);
            case "type1": return getCategory(type1);
            case "type2": return getCategory(type2);
            case "speed": return String.valueOf(speed);
            case "is_legendary": return getCategory(isLegendary);
            default: return "";
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
            case "name": name = value; break;
            case "attack": attack = Integer.parseInt(value); break;
            case "base_egg_steps": baseEggSteps = Integer.parseInt(value); break;
            case "capture_rate": captureRate = Double.parseDouble(value); break;
            case "defense": defense = Integer.parseInt(value); break;
            case "experience_growth": experienceGrowth = Integer.parseInt(value); break;
            case "hp": hp = Integer.parseInt(value); break;
            case "sp_attack": spAttack = Integer.parseInt(value); break;
            case "sp_defense": spDefense = Integer.parseInt(value); break;
            case "type1": type1 = value; break;
            case "type2": type2 = value; break;
            case "speed": speed = Double.parseDouble(value); break;
            case "is_legendary": isLegendary = value; break;
        }
    }

    public String getCategory(String s){
        if(s == null) return "null";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return attack == pokemon.attack &&
            baseEggSteps == pokemon.baseEggSteps &&
            Double.compare(pokemon.captureRate, captureRate) == 0 &&
            defense == pokemon.defense &&
            experienceGrowth == pokemon.experienceGrowth &&
            hp == pokemon.hp &&
            spAttack == pokemon.spAttack &&
            spDefense == pokemon.spDefense &&
            Double.compare(pokemon.speed, speed) == 0 &&
            isLegendary == pokemon.isLegendary &&
            Objects.equals(name, pokemon.name) &&
            Objects.equals(type1, pokemon.type1) &&
            Objects.equals(type2, pokemon.type2);
    }

    /**
     * Retourne une représentation sous forme de String de l'objet Pokemon.
     *
     * @return une chaîne de caractères représentant l'objet Pokemon
     */
    @Override
    public String toString() {
        return "Pokemon [name=" + name +
            ", attack=" + attack +
            ", baseEggSteps=" + baseEggSteps +
            ", captureRate=" + captureRate +
            ", defense=" + defense +
            ", experienceGrowth=" + experienceGrowth +
            ", hp=" + hp +
            ", spAttack=" + spAttack +
            ", spDefense=" + spDefense +
            ", type1=" + type1 +
            ", type2=" + type2 +
            ", speed=" + speed +
            ", isLegendary=" + isLegendary + "]";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Pokemon that) {
            if (this.getName() == null && that.getName() == null) {
                // pass
            } else if (this.getName() == null) {
                return -1;
            } else if (that.getName() == null) {
                return 1;
            } else {
                int nameComparison = this.getName().compareTo(that.getName());
                if (nameComparison != 0) {
                    return nameComparison;
                }
            }

            if (this.getAttack() != that.getAttack()) {
                return Integer.compare(this.getAttack(), that.getAttack());
            }

            if (this.getDefense() != that.getDefense()) {
                return Integer.compare(this.getDefense(), that.getDefense());
            }

            if (this.getHp() != that.getHp()) {
                return Integer.compare(this.getHp(), that.getHp());
            }

            if (Double.compare(this.getSpeed(), that.getSpeed()) != 0) {
                return Double.compare(this.getSpeed(), that.getSpeed());
            }
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attack, baseEggSteps, captureRate, defense, experienceGrowth, hp, spAttack,
                            spDefense, type1, type2, speed, isLegendary);
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