package app;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fr.ulille.but.sae_s2_2024.Lieu;
import graphes.MonLieu;

/**
 * La classe Correspondance représente les données de correspondance pour une application de transport.
 * Elle stocke les correspondances en minutes, les émissions de CO2 et les prix entre les lieux.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 1.0
 */
public class Correspondance {    

    /**
     * HashMap pour stocker les correspondances en minutes entre paires de lieux.
     */
    private HashMap<String[], Integer> correspondanceMinute = new HashMap<>();

    /**
     * HashMap pour stocker les correspondances d'émissions de CO2 entre paires de lieux.
     */
    private HashMap<String[], Double> correspondanceCO2 = new HashMap<>();

    /**
     * HashMap pour stocker les correspondances de prix entre paires de lieux.
     */
    private HashMap<String[], Integer> correspondancePrix = new HashMap<>();

    /**
     * Construit un objet Correspondance en lisant les données à partir d'un fichier spécifié.
     * @param fileVilleCorresp Le fichier contenant les données de correspondance pour la ville.
     * @throws IOException En cas d'erreur lors de la lecture du fichier.
     */
    public Correspondance(String fileVilleCorresp) throws IOException {
        // Lit les données à partir du fichier et initialise les hashmaps de correspondance
        for (String[] s : Graphes.ventilation(fileVilleCorresp)) {
            String[] s2 = {s[0], s[1], s[2]};
            correspondanceMinute.put(s2, Integer.parseInt(s[3]));
            correspondanceCO2.put(s2, Double.parseDouble(s[4]));
            correspondancePrix.put(s2, Integer.parseInt(s[5]));
        }
    }

    /**
     * Extrait les lieux de correspondance à partir d'un ensemble de Lieu et les ajoute à cet ensemble.
     * @param lieux L'ensemble de Lieu à enrichir avec les lieux de correspondance.
     */
    public void extraireLieuCorrespondance(Set<Lieu> lieux) {
        lieux.addAll(getCorrespondance());
    }

    /**
     * Récupère les lieux de correspondance sous forme de HashSet de Lieu.
     * @return HashSet de Lieu représentant les lieux de correspondance.
     */
    public HashSet<Lieu> getCorrespondance() {
        HashSet<String> l = new HashSet<>();
        for (String[] s : correspondanceCO2.keySet()) {
            l.add(s[0] + ", " + s[2].toUpperCase());
        }
        HashSet<Lieu> res = new HashSet<>();
        for (String s : l) {
            res.add(new MonLieu(s));
        }
        return res;
    }
}
