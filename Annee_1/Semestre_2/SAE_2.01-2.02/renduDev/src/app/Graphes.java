package app;

import graphes.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;

/**
 * La classe Graphes représente une plateforme de transport intégrant des lieux
 * et des tronçons
 * pour la modélisation d'un réseau de transport. Elle permet de créer et de
 * gérer les lieux et
 * les tronçons, ainsi que de construire des graphes orientés pour représenter
 * la connectivité
 * entre ces lieux selon différents critères de coût (prix, CO2, temps).
 * 
 * Cette classe est utilisée pour faciliter la planification et l'analyse des
 * itinéraires dans
 * le système de transport. Elle offre également des fonctionnalités pour
 * extraire des informations
 * sur les lieux, les tronçons et les graphes associés.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 2.0
 */
public class Graphes {
    private HashMap<Trancon, Double> trancons = new HashMap<>(); // Ensemble des tronçons de la plateforme
    private HashSet<Lieu> lieux = new HashSet<>(); // Ensemble des lieux de la plateforme
    private MultiGrapheOrienteValue graphePrix = new MultiGrapheOrienteValue(); // Graphe orienté représentant la
                                                                                // connectivité entre les lieux basé sur
                                                                                // le coût en prix
    private MultiGrapheOrienteValue grapheCO2 = new MultiGrapheOrienteValue(); // Graphe orienté représentant la
                                                                               // connectivité entre les lieux basé sur
                                                                               // le coût en CO2
    private MultiGrapheOrienteValue grapheTemps = new MultiGrapheOrienteValue(); // Graphe orienté représentant la
                                                                                 // connectivité entre les lieux basé
                                                                                 // sur le coût en temps
    private MultiGrapheOrienteValue grapheCoeff = new MultiGrapheOrienteValue();
    private ModaliteTransport m;
    private TypeCout coutChoisi;
    private Correspondance correspondance;

    /**
     * Constructeur de la classe Graphes prenant en compte un fichier de données et
     * un fichier de correspondances.
     * 
     * @param filename               le nom du fichier contenant les données de
     *                               tronçons
     * @param filenameCorrespondance le nom du fichier contenant les données de
     *                               correspondance
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la
     *                     lecture des fichiers
     */
    public Graphes(String filename, String filenameCorrespondance) throws IOException {
        ArrayList<String[]> dataVentile = ventilation(filename);
        this.correspondance = new Correspondance(filenameCorrespondance);
        this.lieux = extraireLieu(dataVentile);
    }

    /**
     * Constructeur de la classe Graphes prenant en compte un fichier de données.
     * 
     * @param filename le nom du fichier contenant les données de tronçons
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la
     *                     lecture du fichier
     */
    public Graphes(String filename) throws IOException {
        ArrayList<String[]> dataVentile = ventilation(filename);
        this.lieux = extraireLieu(dataVentile);
    }

    /**
     * Constructeur de la classe Graphes prenant en compte un tableau de données.
     * 
     * @param data un tableau de chaînes de caractères représentant les données de
     *             tronçons
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la
     *                     lecture des données
     */
    public Graphes(String[] data) throws IOException {
        ArrayList<String[]> dataVentile = ventilation(data);
        this.lieux = extraireLieu(dataVentile);
    }

    /**
     * Ventile les données brutes en une liste de tableaux de chaînes de caractères.
     * 
     * @param data Les données brutes à ventiler.
     * @return Une liste de tableaux de chaînes de caractères.
     */

    public static ArrayList<String[]> ventilation(String filename) throws IOException {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] tokenizedLine;
        ArrayList<String[]> arr = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            tokenizedLine = parse(line);
            arr.add(tokenizedLine);
        }
        bufferedReader.close();
        return arr;
    }

    /**
     * Ventile les données brutes en une liste de tableaux de chaînes de caractères.
     * 
     * @param data Les données brutes à ventiler.
     * @return Une liste de tableaux de chaînes de caractères.
     */

    public ArrayList<String[]> ventilation(String[] data) {
        ArrayList<String[]> retour = new ArrayList<String[]>();
        for (int i = 0; i < data.length; ++i) {
            retour.add(data[i].split(";"));
        }
        return retour;
    }

    private static String[] parse(String line) {
        return line.split(";");
    }

    /**
     * Vérifie la validité des données ventilées.
     * 
     * @param data Les données ventilées à vérifier.
     * @return true si les données sont valides, false sinon.
     */
    public static boolean areTranconsValid(List<String[]> data) {
        for (String[] ligne : data) {
            if (ligne.length != 6) {
                return false;
            }

            for (String s : ligne) {
                if (s.equals(null) || s.equals("")) {
                    return false;
                }
            }

            try {
                Integer.parseInt(ligne[3]);
                Double.parseDouble(ligne[4]);
                Integer.parseInt(ligne[5]);
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                ModaliteTransport.valueOf(ligne[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie la validité des données ventilées.
     * 
     * @param data Les données ventilées à vérifier.
     * @return true si les données sont valides, false sinon.
     */
    public static boolean areCorrespondancesValid(List<String[]> data) {
        for (String[] ligne : data) {
            if (ligne.length != 6) {
                return false;
            }

            for (String s : ligne) {
                if (s.equals(null) || s.equals("")) {
                    return false;
                }
            }

            try {
                Integer.parseInt(ligne[3]);
                Double.parseDouble(ligne[4]);
                Integer.parseInt(ligne[5]);
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                ModaliteTransport.valueOf(ligne[1].toUpperCase());
                ModaliteTransport.valueOf(ligne[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Extrait les lieux à partir des données ventilées et les ajoute à la liste des
     * lieux de la plateforme.
     * 
     * @param data Les données ventilées.
     * @return Un ensemble de lieux représentant les points d'arrêt dans le réseau
     *         de transport.
     */
    public HashSet<Lieu> extraireLieu(ArrayList<String[]> data) {
        HashSet<String> s = new HashSet<>();
        for (String[] ligne : data) {
            String nomDepart = ligne[0];
            String nomArrivee = ligne[1];
            s.add(nomArrivee);
            s.add(nomDepart);
        }
        HashSet<Lieu> l = new HashSet<>();
        for (String s2 : s) {
            Lieu m = new MonLieu(s2);
            l.add(m);
        }
        correspondance.extraireLieuCorrespondance(l);
        return l;
    }

    /**
     * Récupere les sommets du HashSet avec les lieux afin de les ajouter au graphe
     * 
     * @param lieu   HashSet avec toutles lieux
     * @param graphe Graphe orienté représentant la connectivité entre les lieux
     */
    public void extraireSommet(HashSet<Lieu> lieu, MultiGrapheOrienteValue graphe) {
        for (Lieu l : lieu) {
            graphe.ajouterSommet(l);
        }
    }

    /**
     * Extrait les tronçons à partir des données ventilées et les ajoute à la liste
     * des tronçons de la plateforme.
     * 
     * @param data Les données ventilées.
     * @param cout critere choisi par l'utilisateur
     * @return Un ensemble de tronçons représentant les segments de liaison entre
     *         les lieux.
     */
    public HashMap<Trancon, Double> extraireTroncon(ArrayList<String[]> data, TypeCout cout) {
        if (m != null)
            triData(data);
        for (int i = 0; i < data.size(); ++i) {
            // Ce qui constitue un tronçon
            String l1 = data.get(i)[0];
            Lieu lieu1 = null;
            String l2 = data.get(i)[1];
            Lieu lieu2 = null;
            ModaliteTransport m = ModaliteTransport.valueOf(data.get(i)[2].toUpperCase());

            for (Lieu l : getGraphePrix().sommets()) {
                if (l.toString().equals(l1)) {
                    lieu1 = l;
                }
                if (l.toString().equals(l2)) {
                    lieu2 = l;
                }
            }

            // Ce qui constitue une HashMap
            Trancon t = new Troncon(lieu1, lieu2, m);
            Trancon tbis = new Troncon(lieu2, lieu1, m);
            coutChoisi = cout;
            double d = getTypeCout(i, data);

            trancons.put(t, d);
            trancons.put(tbis, d);
        }
        return trancons;
    }

    /**
     * Extrait les tronçons à partir des données ventilées et les ajoute à la liste
     * des tronçons de la plateforme.
     * 
     * @param data                Les données ventilées.
     * @param dataCorrespondances Les données de correspondaces ventilées.
     * @param cout                critere choisi par l'utilisateur
     * @return Un ensemble de tronçons représentant les segments de liaison entre
     *         les lieux.
     */
    public HashMap<Trancon, Double> extraireTroncon(ArrayList<String[]> data, ArrayList<String[]> dataCorrespondance,
            TypeCout cout) {
        if (m != null)
            triData(data);

        for (int i = 0; i < dataCorrespondance.size(); ++i) {
            String s1 = dataCorrespondance.get(i)[0];
            Lieu l1 = null;
            Lieu l2 = null;
            ModaliteTransport m = ModaliteTransport.valueOf(dataCorrespondance.get(i)[2].toUpperCase());

            for (Lieu lieu : getGraphePrix().sommets()) {
                if (lieu.toString().equals(s1)) {
                    l1 = lieu;
                }
                if (lieu.toString().equals(s1 + ", " + m.toString().toUpperCase())) {
                    l2 = lieu;
                }
            }

            Trancon t = new Troncon(l1, l2, m);
            Trancon tbis = new Troncon(l2, l1, m);
            coutChoisi = cout;
            double d = getTypeCout(i, dataCorrespondance);

            trancons.put(t, d);
            trancons.put(tbis, d);
        }

        for (int i = 0; i < data.size(); ++i) {
            // Ce qui constitue un tronçon
            String l1 = data.get(i)[0];
            Lieu lieu1 = null;
            String l2 = data.get(i)[1];
            Lieu lieu2 = null;
            ModaliteTransport m = ModaliteTransport.valueOf(data.get(i)[2].toUpperCase());

            for (Lieu l : getGraphePrix().sommets()) {
                if (l.toString().equals(l1)) {
                    for (Trancon t : trancons.keySet()) {
                        if (t.getDepart().equals(l) && t.getModalite().equals(m)) {
                            for (Lieu lieu : getGraphePrix().sommets()) {
                                if (lieu.toString().equals(l1 + ", " + m)) {
                                    lieu1 = lieu;
                                }
                            }
                        }
                    }
                    if (lieu1 == null) {
                        lieu1 = l;
                    }
                }
                if (l.toString().equals(l2)) {
                    lieu2 = l;
                }
            }

            Trancon t = new Troncon(lieu1, lieu2, m);
            Trancon tbis = new Troncon(lieu2, lieu1, m);
            double d = getTypeCout(i, data);

            trancons.put(t, d);
            trancons.put(tbis, d);
        }
        return trancons;
    }

    /**
     * Extrait les tronçons à partir des données ventilées et les ajoute à la liste
     * des tronçons de la plateforme.
     * 
     * @param data                Les données ventilées.
     * @param dataCorrespondances Les données de correspondaces ventilées.
     * @param cout                critere choisi par l'utilisateur
     * @param listeTransports     liste des transports demandés par l'utilisateur
     * @return Un ensemble de tronçons représentant les segments de liaison entre
     *         les lieux.
     */
    public HashMap<Trancon, Double> extraireTroncon(ArrayList<String[]> data, ArrayList<String[]> dataCorrespondance, TypeCout cout, HashSet<ModaliteTransport> listeTransports) {
        for (int i = 0; i < dataCorrespondance.size(); ++i) {
            String s1 = dataCorrespondance.get(i)[0];
            Lieu l1 = null;
            Lieu l2 = null;
            ModaliteTransport m = ModaliteTransport.valueOf(dataCorrespondance.get(i)[2].toUpperCase());
            if(listeTransports.contains(m)){
                for (Lieu lieu : getGraphePrix().sommets()) {
                    if (lieu.toString().equals(s1)) {
                        l1 = lieu;
                    }
                    if (lieu.toString().equals(s1 + ", " + m.toString().toUpperCase())) {
                        l2 = lieu;
                    }
                }

                Trancon t = new Troncon(l1, l2, m);
                Trancon tbis = new Troncon(l2, l1, m);
                coutChoisi = cout;
                double d = getTypeCout(i, dataCorrespondance);

                trancons.put(t, d);
                trancons.put(tbis, d);
            }
        }

        for (int i = 0; i < data.size(); ++i) {
            // Ce qui constitue un tronçon
            String l1 = data.get(i)[0];
            Lieu lieu1 = null;
            String l2 = data.get(i)[1];
            Lieu lieu2 = null;
            ModaliteTransport m = ModaliteTransport.valueOf(data.get(i)[2].toUpperCase());

            if(listeTransports.contains(m)){
                for (Lieu l : getGraphePrix().sommets()) {
                    if (l.toString().equals(l1)) {
                        for (Trancon t : trancons.keySet()) {
                            if (t.getDepart().equals(l) && t.getModalite().equals(m)) {
                                for (Lieu lieu : getGraphePrix().sommets()) {
                                    if (lieu.toString().equals(l1 + ", " + m)) {
                                        lieu1 = lieu;
                                    }
                                }
                            }
                        }
                        if (lieu1 == null) {
                            lieu1 = l;
                        }
                    }
                    if (l.toString().equals(l2)) {
                        lieu2 = l;
                    }
                }
    
                Trancon t = new Troncon(lieu1, lieu2, m);
                Trancon tbis = new Troncon(lieu2, lieu1, m);
                coutChoisi = cout;
                double d = getTypeCout(i, data);
    
                trancons.put(t, d);
                trancons.put(tbis, d);
            }
        }
        return trancons;
    }

    /**
     * 
     * Crée le graphe coefficenté selon les valeurs présentes dans 3 doubles en paramètres
     * 
     * @param coeffCO2
     * @param coeffTemps
     * @param coeffPrix
     */
    public void createCoeffGraphe(double coeffCO2, double coeffTemps, double coeffPrix){
        for(Trancon t_co2 : getGrapheCO2().aretes()){
            for(Trancon t_temps : getGrapheTemps().aretes()){
                for(Trancon t_prix : getGraphePrix().aretes()){
                    if(t_co2.toString().equals(t_temps.toString()) && t_co2.toString().equals(t_prix.toString())){
                        double d = getGrapheCO2().getPoidsArete(t_co2) * coeffCO2 + getGrapheTemps().getPoidsArete(t_temps) * coeffTemps + getGraphePrix().getPoidsArete(t_prix) * coeffPrix;
                        trancons.put(t_co2, d);
                    }
                }
            }
        }
        extraireArete(trancons, grapheCoeff);
        clearMap();
    }

    /**
     * 
     * Conserve uniquement les données qui utilises le mode de transport choisi
     * auparavant (attribut m)
     * 
     * @param data Les données ventilées.
     */
    public void triData(ArrayList<String[]> data) {
        ArrayList<String[]> trash = new ArrayList<>();
        for (int i = 0; i < data.size(); ++i) {
            if (!ModaliteTransport.valueOf(data.get(i)[2].toUpperCase()).toString().equalsIgnoreCase(m.toString())) {
                trash.add(data.get(i));
            }
        }
        data.removeAll(trash);
    }

    /**
     * 
     * Récupère la valeur dans les datas ventilées qui correspondent à l'attribut
     * coutChoisi qui est un type de
     * cout présent dans l'énumération TypeCout
     * 
     * @param i    index du tableau a traier dans l'arraylist data
     * @param data Les données ventilées.
     * @return la valeur du coup
     */
    public Double getTypeCout(int i, ArrayList<String[]> data) {
        if (coutChoisi.equals(TypeCout.CO2)) {
            return Double.parseDouble(data.get(i)[4]);
        } else if (coutChoisi.equals(TypeCout.TEMPS)) {
            return Double.parseDouble(data.get(i)[5]);
        } else if (coutChoisi.equals(TypeCout.PRIX)) {
            return Double.parseDouble(data.get(i)[3]);
        }
        return null;
    }

    /**
     * 
     * Récupere les valeurs (l'arrivée et le départ qui forment une arete et le
     * poids de cette arete)
     * des tronçons de l'HashMap pour creer le graphe
     * 
     * @param hashMapTrancon HashMap avec les aretes et le poids de ces aretes
     * @param graphe         Graphe orienté représentant la connectivité entre les
     *                       lieux
     */
    public void extraireArete(HashMap<Trancon, Double> hashMapTrancon, MultiGrapheOrienteValue graphe) {
        for (Map.Entry<Trancon, Double> entry : hashMapTrancon.entrySet()) {
            Trancon trancon = entry.getKey();
            double poids = (double) entry.getValue();
            graphe.ajouterArete(trancon, poids);
        }
    }

    /**
     * 
     * Creé le graphe a l'aide des fonctions permetant d'extraire les données
     * nécessaire a la création du
     * graphe
     * 
     * @param data Les données ventilées.
     */
    public void genererGraphe(ArrayList<String[]> data) {
        extraireSommet(lieux, grapheCO2);
        extraireSommet(lieux, graphePrix);
        extraireSommet(lieux, grapheTemps);
        extraireTroncon(data, TypeCout.CO2);
        extraireArete(trancons, grapheCO2);
        clearMap();
        extraireTroncon(data, TypeCout.PRIX);
        extraireArete(trancons, graphePrix);
        clearMap();
        extraireTroncon(data, TypeCout.TEMPS);
        extraireArete(trancons, grapheTemps);
        clearMap();
    }

    /**
     * 
     * Creé le graphe a l'aide des fonctions permetant d'extraire les données
     * nécessaire a la création du
     * graphe
     * 
     * @param data               Les données ventilées.
     * @param dataCorrespondance Les données de correspondances ventilées.
     */
    public void genererGraphe(ArrayList<String[]> data, ArrayList<String[]> dataCorrespondance) {
        extraireSommet(lieux, grapheCO2);
        extraireSommet(lieux, graphePrix);
        extraireSommet(lieux, grapheTemps);
        extraireTroncon(data, dataCorrespondance, TypeCout.CO2);
        extraireArete(trancons, grapheCO2);
        clearMap();
        extraireTroncon(data, dataCorrespondance, TypeCout.PRIX);
        extraireArete(trancons, graphePrix);
        clearMap();
        extraireTroncon(data, dataCorrespondance, TypeCout.TEMPS);
        extraireArete(trancons, grapheTemps);
        clearMap();
    }

    /**
     * 
     * Creé le graphe a l'aide des fonctions permetant d'extraire les données
     * nécessaire a la création du
     * graphe
     * 
     * @param data               Les données ventilées.
     * @param dataCorrespondance Les données de correspondances ventilées.
     * @param modal              Les modalités de transport demandées
     */
    public void genererGraphe(ArrayList<String[]> data, ArrayList<String[]> dataCorrespondance, HashSet<ModaliteTransport> modal) {
        extraireSommet(lieux, grapheCO2);
        extraireSommet(lieux, graphePrix);
        extraireSommet(lieux, grapheTemps);
        extraireSommet(lieux, grapheCoeff);
        extraireTroncon(data, dataCorrespondance, TypeCout.CO2, modal);
        extraireArete(trancons, grapheCO2);
        clearMap();
        extraireTroncon(data, dataCorrespondance, TypeCout.PRIX, modal);
        extraireArete(trancons, graphePrix);
        clearMap();
        extraireTroncon(data, dataCorrespondance, TypeCout.TEMPS, modal);
        extraireArete(trancons, grapheTemps);
        clearMap();
    }

    /**
     * vide l'attribut trancons
     */
    public void clearMap() {
        trancons.clear();
    }

    /**
     * Remet à 0 tous les graphes
     */
    public void resetGraphe(){
        graphePrix = new MultiGrapheOrienteValue(); 
        grapheCO2 = new MultiGrapheOrienteValue(); 
        grapheTemps = new MultiGrapheOrienteValue(); 
        grapheCoeff = new MultiGrapheOrienteValue();
    }

    /**
     * 
     * Renvoie le graphe correspondant au critere choisis
     * 
     * @param cout critere
     * @return un graphe ou null si le critere n'est pas valide
     */
    public MultiGrapheOrienteValue associerGraphe(TypeCout cout) {
        if (cout.equals(TypeCout.CO2)) {
            return grapheCO2;
        }
        if (cout.equals(TypeCout.TEMPS)) {
            return grapheTemps;
        }
        if (cout.equals(TypeCout.PRIX)) {
            return graphePrix;
        }
        return null;
    }

    /**
     * Affiche les lieux de la plateforme.
     * 
     * @param lieux L'ensemble des lieux à afficher.
     */
    public void afficherLieux(HashSet<Lieu> lieux) {
        System.out.println("Liste des lieux :");
        if (lieux.size() == 0) {
            System.out.println("La liste des lieux est vide");
        }
        for (Lieu lieu : lieux) {
            if (!lieu.toString().contains(",")) {
                System.out.println(lieu.toString());
            }
        }
    }

    /**
     * Renvoie un HashSet des lieux en enlevant les correspondances
     * 
     * @return Les lieux sans les correspondances
     */
    public HashSet<Lieu> getCleanLieux(){
        HashSet<Lieu> l = new HashSet<>();
        for (Lieu lieu : this.lieux) {
            if (!lieu.toString().contains(",")) {
                l.add(lieu);
            }
        }
        return l;
    }

    /**
     * Retourne l'ensemble des lieux de la plateforme.
     * 
     * @return L'ensemble des lieux représentant les points d'arrêt dans le réseau
     *         de transport.
     */
    public HashSet<Lieu> getLieux() {
        return lieux;
    }

    /**
     * Retourne l'ensemble des tronçons de la plateforme.
     * 
     * @return L'ensemble des tronçons représentant les segments de liaison entre
     *         les lieux.
     */
    public HashMap<Trancon, Double> getTroncons() {
        return trancons;
    }

    /**
     * Retourne le graphe orienté pondéré par les émissions de CO2.
     * 
     * @return Le graphe orienté pondéré par les émissions de CO2.
     */
    public MultiGrapheOrienteValue getGrapheCO2() {
        return grapheCO2;
    }

    /**
     * Retourne le graphe orienté pondéré par les prix.
     * 
     * @return Le graphe orienté pondéré par les prix.
     */
    public MultiGrapheOrienteValue getGraphePrix() {
        return graphePrix;
    }

    /**
     * Retourne le graphe orienté pondéré par le temps.
     * 
     * @return Le graphe orienté pondéré par le temps.
     */
    public MultiGrapheOrienteValue getGrapheTemps() {
        return grapheTemps;
    }

    /**
     * Retourne le type de coût choisi pour la planification des itinéraires.
     * 
     * @return Le type de coût choisi.
     */
    public TypeCout getCoutChoisi() {
        return coutChoisi;
    }

    /**
     * Définit la modalité de transport.
     * 
     * @param m La modalité de transport à définir.
     */
    public void setM(ModaliteTransport m) {
        this.m = m;
    }

    /**
     * Définit le type de coût choisi pour la planification des itinéraires.
     * 
     * @param coutChoisi Le type de coût à définir.
     */
    public void setCoutChoisi(TypeCout coutChoisi) {
        this.coutChoisi = coutChoisi;
    }

    /**
     * Retourne l'objet de correspondance pour les lieux.
     * 
     * @return L'objet de correspondance.
     */
    public Correspondance getCorrespondance() {
        return correspondance;
    }

    /**
     * Retourne l'ensemble des tronçons de la plateforme.
     * 
     * @return L'ensemble des tronçons.
     */
    public HashMap<Trancon, Double> getTrancons() {
        return trancons;
    }

    /**
     * Retourne la modalité de transport.
     * 
     * @return La modalité de transport.
     */
    public ModaliteTransport getM() {
        return m;
    }

    /**
     * Retourne le graphe coefficienté
     * 
     * @return le graphe coefficienté
     */
    public MultiGrapheOrienteValue getGrapheCoeff() {
        return grapheCoeff;
    }

    /**
     * Définit le graphe coefficienté
     * 
     * @param graphCoeff Le graphe coefficienté
     */
    public void setGrapheCoeff(MultiGrapheOrienteValue grapheCoeff) {
        this.grapheCoeff = grapheCoeff;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères d'une liste de chemins.
     * 
     * @param l La liste de chemins à représenter sous forme de chaîne.
     * @return Une chaîne de caractères représentant la liste de chemins.
     */
    public static String toString(List<Chemin> lisChemin) {
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
            s += ((MonLieu) c.aretes().get(c.aretes().size() - 1).getArrivee()).cleanName() + " : " + c.poids() + "\n";
            ++i;
        }
        return s;
    }
}