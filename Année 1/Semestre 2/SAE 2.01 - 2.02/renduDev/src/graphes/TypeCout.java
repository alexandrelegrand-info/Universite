package graphes;

/**
 * L'énumération TypeCout représente les différents types de coûts associés à un
 * tronçon ou à un voyage dans un réseau de transport.
 * Elle est utilisée pour définir les différentes métriques de coût prises en
 * compte dans le calcul d'itinéraires ou d'optimisation de trajets.
 * Les types de coûts possibles sont : CO2, TEMPS et PRIX.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 2.0
 */
public enum TypeCout {
    CO2, // Coût en termes d'émissions de CO2
    TEMPS, // Coût en termes de temps de trajet
    PRIX; // Coût en termes de prix ou de tarif

    public String afficherEquivalent() {
        switch (this) {
            case CO2:
                return "kgCO2";
            case TEMPS:
                return "min";
            case PRIX:
                return "euros";
            default:
                return null;
        }
    }
}
