package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import exception.CSVFormatException;
import exception.RoadNotFoundException;
import exception.SameCityException;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;
import graphes.*;

/**
 * La classe Main est la classe principale du programme. Elle contient la
 * méthode main à partir de laquelle l'exécution démarre.
 * Cette classe est utilisée pour tester et démontrer le fonctionnement de la
 * classe Plateforme.
 * Elle initialise une plateforme de transport avec des données prédéfinies,
 * extrait les lieux et les tronçons, et affiche ensuite ces informations.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 2.0
 */
public class Main {
    /**
     * La méthode principale à partir de laquelle l'exécution démarre.
     * Elle crée une plateforme de transport avec des données prédéfinies, extrait
     * les lieux et les tronçons, puis affiche ces informations.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés dans cette
     *             application).
     * @throws IOException
     */

    final static String SEPARATOR = System.getProperty("file.separator");
    final static String FILENAME = "res" + SEPARATOR + "data.csv";
    final static String FILENAMEC = "res" + SEPARATOR + "correspondance.csv";

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException, CSVFormatException, RoadNotFoundException, SameCityException {
        // Données de test pour la création de la plateforme

        String[] data = new String[] {
                "villeA;villeB;Train;60;1.7;80",
                "villeB;villeD;Train;22;2.4;40",
                "villeA;villeC;Train;42;1.4;50",
                "villeB;villeC;Train;14;1.4;60",
                "villeC;villeD;Avion;110;150;22",
                "villeC;villeD;Train;65;1.2;90"
        };

        //// VERSION 1 :

        //// Création d'une instance de Plateforme avec les données prédéfinies
        // Graphes graphes = new Graphes(data);
        // Plateforme plateforme = new Plateforme(graphes);

        //// Ventilation des données pour extraire les lieux et les tronçons
        // ArrayList<String[]> ventilatedData = Graphes.ventilation(data);
        // graphes.extraireLieu(ventilatedData);
        // plateforme.getGraphes().setM(plateforme.choisirTransport());
        // graphes.genererGraphe(ventilatedData);

        //// Cout choisi a la valeur de tri
        // plateforme.choisirFiltre();

        // Lieu depart = plateforme.choisirVille("depart", graphes.getLieux());
        // Lieu arrivee = plateforme.choisirVille("arrivee", graphes.getLieux());
        // plateforme.getVoyageur().setArrivee(arrivee);
        // plateforme.getVoyageur().setDepart(depart);
        // System.out.println(toString(plateforme.appliquerCritere(plateforme.critere())));

        // // Affichage des lieux et des tronçons de la plateforme
        // plateforme.getGraphes().afficherLieux(plateforme.getGraphes().getLieux());

        // // Ajout des sommets représentant les lieux au graphe de la plateforme
        // plateforme.afficherTroncons(plateforme.getGraphes().getTroncons());








        // VERSION 2 :

        // Ventilation des données pour extraire les lieux et les tronçons
        //ArrayList<String[]> ventilatedData = Graphes.ventilation(FILENAME);
        //ArrayList<String[]> ventilatedCorrespondance = Graphes.ventilation(FILENAMEC);
        //if (Graphes.areCorrespondancesValid(ventilatedCorrespondance) && Graphes.areTranconsValid(ventilatedData)) {
            // Création d'une instance de Plateforme avec les données prédéfinies
            //Graphes graphes = new Graphes(FILENAME, FILENAMEC);
            //Plateforme plateforme = new Plateforme(graphes);
            // plateforme.getGraphes().setM(plateforme.choisirTransport());
            //graphes.genererGraphe(ventilatedData, ventilatedCorrespondance);
            // Cout choisi a la valeur de tri
            //plateforme.choisirFiltre();

            //Lieu depart = plateforme.choisirVille("depart", graphes.getLieux());
            //Lieu arrivee = plateforme.choisirVille("arrivee", graphes.getLieux());
            //plateforme.getVoyageur().setArrivee(arrivee);
            //plateforme.getVoyageur().setDepart(depart);
            //if(plateforme.getVoyageur().getArrivee() == plateforme.getVoyageur().getDepart()){
            //    throw new SameCityException("La ville de départ et d'arrivée sont les mêmes");
            //}
            //System.out.println(plateforme.getGrapheFinal().aretes().toString());
            //System.out.println(toString(plateforme.appliquerCritere(plateforme.critere()), graphes.getCoutChoisi()));
            // // Affichage des lieux et des tronçons de la plateforme
            // plateforme.getGraphes().afficherLieux(plateforme.getGraphes().getLieux());

            // // Ajout des sommets représentant les lieux au graphe de la plateforme

        //} else {
            //throw new CSVFormatException("Données invalides");
        //}






        // VERSION 3 :

        // Ventilation des données pour extraire les lieux et les tronçons
        ArrayList<String[]> ventilatedData = Graphes.ventilation(FILENAME);
        ArrayList<String[]> ventilatedCorrespondance = Graphes.ventilation(FILENAMEC);
        if (Graphes.areCorrespondancesValid(ventilatedCorrespondance) && Graphes.areTranconsValid(ventilatedData)) {
            ArrayList<Trajet> trajet = new ArrayList<>();
            // Création d'une instance de Plateforme avec les données prédéfinies
            Graphes graphes = new Graphes(FILENAME, FILENAMEC);
            Plateforme plateforme = new Plateforme(graphes);
            // plateforme.getGraphes().setM(plateforme.choisirTransport());
            HashSet<ModaliteTransport> modal = new HashSet<>();
            modal = Plateforme.choixModaliteTransports();
            if(modal.isEmpty()){
                throw new RoadNotFoundException("Aucune modalité de transport choisie");
            }
            graphes.genererGraphe(ventilatedData, ventilatedCorrespondance, modal);
            Lieu depart = plateforme.choisirVille("depart", graphes.getLieux());
            Lieu arrivee = plateforme.choisirVille("arrivee", graphes.getLieux());
            plateforme.getVoyageur().setArrivee(arrivee);
            plateforme.getVoyageur().setDepart(depart);
            if(plateforme.getVoyageur().getArrivee() == plateforme.getVoyageur().getDepart()){
                throw new SameCityException("La ville de départ et d'arrivée sont les mêmes");
            }
            int prix = Plateforme.choixCritere(TypeCout.PRIX);
            int CO2 = Plateforme.choixCritere(TypeCout.CO2);
            int temps = Plateforme.choixCritere(TypeCout.TEMPS);
            if(prix == 0 && CO2 == 0 && temps == 0){
                throw new RoadNotFoundException("Inscrivez au minimum un critère");
            }
            plateforme.getGraphes().createCoeffGraphe( CO2 / 100, temps / 100, prix / 100);
            //System.out.println(plateforme.getGrapheFinal().aretes().toString());
            List<Chemin> c = plateforme.calculKPCC(plateforme.getGraphes().getGrapheCoeff(), plateforme.getVoyageur());
            if (c.size() == 0) {
                throw new RoadNotFoundException("Aucune route existante");
            }
            Trajet t1 = new Trajet(c.get(0), plateforme.getPoidsChemin(c.get(0), TypeCout.CO2), plateforme.getPoidsChemin(c.get(0), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(0), TypeCout.TEMPS));
            Trajet t2 = null;
            Trajet t3 = null;
            if (c.size() > 1) {
                t2 = new Trajet(c.get(1), plateforme.getPoidsChemin(c.get(1), TypeCout.CO2), plateforme.getPoidsChemin(c.get(1), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(1), TypeCout.TEMPS));
                if (c.size() > 2) {
                    t3 = new Trajet(c.get(2), plateforme.getPoidsChemin(c.get(2), TypeCout.CO2), plateforme.getPoidsChemin(c.get(2), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(2), TypeCout.TEMPS));
                }
            }
            trajet.add(t1);
            Trajet.savingTrajet(trajet);
            System.out.println("Trajet 1 : " + t1.toString());
            if(t2 != null){
                System.out.println("Trajet 2 : " +t2.toString());
            } 
            if (t3 != null){
                System.out.println("Trajet 3 : " +t3.toString());
            }
            // // Affichage des lieux et des tronçons de la plateforme
            // plateforme.getGraphes().afficherLieux(plateforme.getGraphes().getLieux());

            // // Ajout des sommets représentant les lieux au graphe de la plateforme

        } else {
            throw new CSVFormatException("Données invalides");
        }






    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères d'une liste de chemins,
     * en prenant en compte le type de coût spécifié.
     * 
     * @param lisChemin La liste de chemins à représenter sous forme de chaîne.
     * @param cout Le type de coût à afficher pour chaque chemin.
     * @return Une chaîne de caractères représentant la liste de chemins avec les détails de coût.
     */
    public static String toString(List<Chemin> lisChemin, TypeCout cout) {
        String s = "";
        int i = 1;
        for (Chemin c : lisChemin) {
            ModaliteTransport m = c.aretes().get(0).getModalite();
            s += i + ") " + ((MonLieu) c.aretes().get(0).getDepart()).cleanName() + "---" + m + "-->";
            for (Trancon t : c.aretes()) {
                if (t.getModalite() != m) {
                    m = t.getModalite();
                    s += ((MonLieu) t.getArrivee()).cleanName() + "---" + m + "-->";
                }
            }
            s += ((MonLieu) c.aretes().get(c.aretes().size() - 1).getArrivee()).cleanName() + " : " + c.poids() + " "
                    + cout.afficherEquivalent() + "\n";
            ++i;
        }
        return s;
    }
}
