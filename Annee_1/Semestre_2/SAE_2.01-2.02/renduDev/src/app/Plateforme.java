package app;

import graphes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import exception.RoadNotFoundException;
import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Plateforme représente une plateforme de transport intégrant des
 * lieux et des tronçons pour la modélisation d'un réseau de transport.
 * Elle permet de créer et de gérer les lieux et les tronçons, ainsi que de
 * construire un graphe orienté pour représenter la connectivité entre ces
 * lieux.
 * Cette classe est utilisée pour faciliter la planification et l'analyse des
 * itinéraires dans le système de transport.
 * Elle offre également des fonctionnalités pour extraire des informations sur
 * les lieux, les tronçons et le graphe associé.
 * 
 * @author Benjamin Sere
 * @author Alexandre Legrand
 * @author Rémi Poupard-Ramaut
 * @version 2.0
 */
public class Plateforme {
    private Voyageur voyageur = new Voyageur(); // Utilisateur de la Plateforme
    private MultiGrapheOrienteValue grapheFinal;
    private Graphes graphes;

    public Plateforme(Graphes g) {
        this.graphes = g;
    }

    /**
     * Affiche le type de coût que l'utilisateur à choisi
     */
    public void choisirFiltre() {
        graphes.setCoutChoisi(filtre());
        System.out.println("Votre choix " + graphes.getCoutChoisi().toString() + "\n");
    }

    /**
     * 
     * Récupere l'input de l'utilisateur grâce à la classe Scanner
     * 
     * @return l'input de l'utilisateur
     */
    @SuppressWarnings("resource")
    public static String getUserInuput() {
        Scanner scanner = new Scanner(System.in);
        String r = scanner.next();
        return r;
    }

    /**
     * 
     * Retourne vrai ou faux selon que le parametre pet oui ou non être transformé
     * en Double
     * 
     * @param s Chaine de caractère
     * @return true si s a pu être transformé en Double et false sinon
     */
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 
     * recupere le second critere de l'utilisateur si il souhaite en rajouter un
     * ainsi que la valeur a ne pas
     * exéder pour ce critère
     * 
     * @return un tableau de String avec le critere et la valeur max
     */
    public String[] critere() {
        String[] res = new String[2];
        TypeCout cout = graphes.getCoutChoisi();
        boolean bool = true;
        String userInput = "";
        while (bool) {
            System.out.print(
                    "Veuillez choisir un critère parmi les valeurs ci-dessus (tapez 0 pour ne pas ajouter de critère): \n");
            afficherTypeCout();
            userInput = getUserInuput();
            if (userInput.equals("0")) {
                return res;
            }
            if (estDansTypeCout(userInput)) {
                cout = TypeCout.valueOf(userInput.toUpperCase());
                res[0] = cout.toString();
                afficherCritere(cout);
                userInput = getUserInuput();
                if (isDouble(userInput)) {
                    bool = false; // Sortir de la boucle si le choix est valide
                    res[1] = userInput;
                } else {
                    System.out.println("Entrez une valeur correcte (nombre), veuillez rechoisir votre critère");
                }
            } else {
                System.out.println("Valeur incorrecte. Veuillez choisir parmi les valeurs ci-dessus.");
            }
        }
        return res;
    }

    /**
     * 
     * renvoie vrai ou faux selon le fait que a1 soit egals à a2 ou non
     * 
     * @param a1 chemin 1
     * @param a2 chemin 2
     * @return true si a1 et a2 sont egaux false sinon
     */
    public static boolean comparerArete(Chemin a1, Chemin a2) {
        return a1.aretes().toString().equals(a2.aretes().toString());
    }

    /**
     * 
     * renvoie vrai ou faux selon le fait que a1 soit egals à a2 ou non
     * 
     * @param a1 List 1
     * @param a2 List 2
     * @return true si a1 et a2 sont egaux false sinon
     */
    public static boolean comparerArete(List<Trancon> a1, List<Trancon> a2) {
        return a1.toString().equals(a2.toString());
    }

    /**
     * 
     * applique le critere qui a été choisi au graphe
     * 
     * @param critere Tableau du/des critere.s choisi.s
     * @return une list des chemin correspondant au critere.s choisi.s
     * @throws RoadNotFoundException
     * @throws IOException
     */
    public List<Chemin> appliquerCritere(String[] critere) throws RoadNotFoundException, IOException {
        if (critere[0] == null || critere[1] == null) {
            List<Chemin> c = calculKPCC(grapheFinal, voyageur);
            if (c.size() == 0) {
                throw new RoadNotFoundException("Aucune route existante");
            }
            System.out.println("\n\n\nLes " + c.size() + " plus courts chemins sont : \n");
            return c;
        }
        MultiGrapheOrienteValue grapheCritèreTrie = graphes.associerGraphe(TypeCout.valueOf(critere[0]));
        MultiGrapheOrienteValue grapheCritère = grapheFinal;

        List<Chemin> resGrapheCritèreTrie = calculKPCC(grapheCritèreTrie, voyageur);
        List<Chemin> resGrapheCritère = calculKPCC(grapheCritère, voyageur);

        List<Chemin> trie = new ArrayList<>();
        for (Chemin c : resGrapheCritère) {
            for (Chemin c2 : resGrapheCritèreTrie) {
                if (comparerArete(c, c2)) {
                    // System.out.println(c2.toString() + " = " + c.toString());
                    trie.add(c2);
                }
            }
        }
        // System.out.println("TRI : " + toString(trie) + "\n\n\n");
        // System.out.println("GRAPH : " + toString(resGrapheCritère) + "\n\n\n");
        List<Chemin> trash = new ArrayList<>();

        for (int i = 0; i < trie.size(); ++i) {
            if ((trie.get(i).poids()) > Double.parseDouble(critere[1])) {
                trash.add(resGrapheCritère.get(i));
            }
        }
        // System.out.println("TRASH : " + trash);
        resGrapheCritère.removeAll(trash);
        if (resGrapheCritère.size() == 0) {
            throw new RoadNotFoundException("Aucune route existante");
        }
        List<Chemin> l = calculKPCC(grapheFinal, voyageur);
        System.out.println("\n\n\nLes " + l.size() + " plus courts chemins sont : \n");
        return l;
    }

    /**
     * Calcule une liste de chemins optimaux pour un voyageur donné en utilisant
     * l'algorithme KPCC.
     * Cette méthode prend en compte les correspondances entre les villes et filtre
     * les chemins
     * en fonction des critères de la même ville.
     * 
     * @param graph Le graphe orienté représentant le réseau de transport.
     * @param v     Le voyageur pour lequel les chemins sont calculés.
     * @return Une liste de chemins optimaux pour le voyageur.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
     *                     lecture des correspondances.
     */
    public List<Chemin> calculKPCC(MultiGrapheOrienteValue graph, Voyageur v) throws IOException {
        List<Chemin> l = AlgorithmeKPCC.kpcc(graph, v.getDepart(), v.getArrivee(), 5000);
        List<Chemin> res = new ArrayList<>();
        for (String[] s : Graphes.ventilation("res" + System.getProperty("file.separator") + "correspondance.csv")) {
            if (s[0].equals(v.getDepart().toString())) {
                for (Chemin c : l) {
                    if (((MonLieu) c.aretes().get(0).getArrivee()).cleanName().equals(s[0])) {
                        List<Chemin> tmp = AlgorithmeKPCC.kpcc(graph, c.aretes().get(0).getArrivee(), v.getArrivee(),
                                5000);
                        for (Chemin ch : tmp) {
                            if (!((Troncon) ch.aretes().get(0)).isSameCity()) {
                                res.add(ch);
                            }
                        }
                    } else {
                        res.add(c);
                    }
                }
            } else if (s[0].equals(v.getArrivee().toString())) {
                for (Chemin c : l) {
                    int lastTroncon = c.aretes().size() - 1;
                    if (((MonLieu) c.aretes().get(lastTroncon).getDepart()).cleanName().equals(s[0])) {
                        List<Chemin> tmp = AlgorithmeKPCC.kpcc(graph, v.getDepart(),
                                c.aretes().get(lastTroncon).getDepart(), 5000);
                        for (Chemin ch : tmp) {
                            lastTroncon = ch.aretes().size() - 1;
                            if (!((Troncon) ch.aretes().get(lastTroncon)).isSameCity()) {
                                res.add(ch);
                            }
                        }
                    } else {
                        res.add(c);
                    }
                }
            } else {
                return l;
            }
        }
        // Collections.sort(res);
        return res;
    }

    /**
     * 
     * Affiche la question correspondant au critère
     * 
     * @param c critère choisi
     */
    public void afficherCritere(TypeCout c) {
        if (c.equals(TypeCout.CO2)) {
            System.out.println("Quel est le maximum de taux de CO2 que vous demandez ?");
        }
        if (c.equals(TypeCout.PRIX)) {
            System.out.println("Quel prix ne voulez vous pas excéder ?");
        }
        if (c.equals(TypeCout.TEMPS)) {
            System.out.println("Quel est le temps que vous ne voulez pas excéder ?");
        }
    }

    /**
     * 
     * Fait choisir une ville de départ et arrivée à l'utilisateur
     * 
     * @param type        départ ou arrivée
     * @param disponibles HashSet des villes restantes
     * @return le lieu choisi par l'utilisateur
     */
    public Lieu choisirVille(String type, HashSet<Lieu> disponibles) {
        Lieu l = null;
        boolean bool = true;
        while (bool) {
            System.out.print("Veuillez choisir la ville de " + type + " parmi les suivantes : \n");
            graphes.afficherLieux(disponibles);
            String nomVille = getUserInuput();
            for (Lieu lieu : disponibles) {
                if (lieu.toString().equalsIgnoreCase(nomVille)) {
                    l = lieu;
                    bool = false;
                }
            }
            if (bool) {
                System.out.println("Ville invalide. Veuillez choisir une ville valide.");
            }
        }
        return l;
    }

    /**
     * Permet à l'utilisateur de choisir un mode de transport parmi les options
     * disponibles.
     * Affiche les choix possibles et demande à l'utilisateur de faire un choix
     * valide.
     * 
     * @return Le mode de transport choisi par l'utilisateur.
     */
    public ModaliteTransport choisirTransport() {
        ModaliteTransport t = ModaliteTransport.TRAIN;
        boolean bool = true;
        while (bool) {
            System.out.print("Veuillez choisir un transport parmi les choix ci-dessous : \n");
            afficherModaliteTransport();
            String userInput = getUserInuput();
            if (estDansModaliteTransport(userInput)) {
                t = ModaliteTransport.valueOf(userInput.toUpperCase());
                bool = false; // Sortir de la boucle si le choix est valide
            } else {
                System.out.println("Choix incorrecte. Veuillez choisir parmi les transports ci-dessus.");
            }
        }
        return t;
    }

    /**
     * Vérifie si la chaîne donnée correspond à l'un des types de coût disponibles.
     * 
     * @param s La chaîne à vérifier.
     * @return true si la chaîne correspond à un type de coût, false sinon.
     */
    public static boolean estDansTypeCout(String s) {
        TypeCout[] a = TypeCout.values();
        boolean find = false;
        int i = 0;
        while (i < a.length && !find) {
            if (a[i].toString().equalsIgnoreCase(s)) {
                find = true;
            }
            ++i;
        }
        return find;
    }

    /**
     * Vérifie si la chaîne donnée correspond à l'une des modalités de transport
     * disponibles.
     * 
     * @param s La chaîne à vérifier.
     * @return true si la chaîne correspond à une modalité de transport, false
     *         sinon.
     */
    public static boolean estDansModaliteTransport(String s) {
        ModaliteTransport[] a = ModaliteTransport.values();
        boolean find = false;
        int i = 0;
        while (i < a.length && !find) {
            if (a[i].toString().equalsIgnoreCase(s)) {
                find = true;
            }
            ++i;
        }
        return find;
    }

    /**
     * Affiche les différents types de coûts disponibles.
     */
    public void afficherTypeCout() {
        for (TypeCout t : TypeCout.values()) {
            System.out.println(t);
        }
    }

    /**
     * Affiche les différentes modalités de transport disponibles.
     */
    public void afficherModaliteTransport() {
        for (ModaliteTransport m : ModaliteTransport.values()) {
            System.out.println(m);
        }
    }

    /**
     * 
     * Fait choisir le premier critere a l'utilisateur
     * 
     * @return un TypeCout qui sera le premier critere
     */
    public TypeCout filtre() {
        TypeCout cout = graphes.getCoutChoisi();
        boolean bool = true;
        while (bool) {
            System.out.print("Veuillez choisir un filtre parmi les valeurs ci-dessus : \n");
            afficherTypeCout();
            String userInput = getUserInuput();
            if (estDansTypeCout(userInput)) {
                cout = TypeCout.valueOf(userInput.toUpperCase());
                setGraphe(graphes.associerGraphe(cout));
                bool = false; // Sortir de la boucle si le choix est valide
            } else {
                System.out.println("Valeur incorrecte. Veuillez choisir parmi les valeurs ci-dessus.");
            }
        }
        return cout;
    }

    /**
     * Affiche les tronçons de la plateforme.
     * 
     * @param trancons L'ensemble des tronçons à afficher.
     */
    public void afficherTroncons(HashMap<Trancon, Double> trancons) {
        System.out.println("Liste des tronçons :");
        if (trancons.size() == 0) {
            System.out.println("La liste des trancons est vide");
        }
        for (Trancon trancon : trancons.keySet()) {
            System.out.println(trancon.toString());
        }
        System.out.println();
    }

    /**
     * Retourne une représentation textuelle d'un chemin, incluant les modalités de
     * transport et les poids associés.
     *
     * @param c Le chemin à représenter.
     * @return Une chaîne de caractères décrivant le chemin.
     * @throws IOException En cas d'erreur lors de la récupération des poids du
     *                     chemin.
     */
    public String toString(Chemin c) throws IOException {
        String s = "";
        ModaliteTransport m = c.aretes().get(0).getModalite();
        s += ((MonLieu) c.aretes().get(0).getDepart()).cleanName() + "---" + m + "-->";
        for (Trancon t : c.aretes()) {
            if (t.getModalite() != m) {
                m = t.getModalite();
                s += ((MonLieu) t.getArrivee()).cleanName() + "---" + m + "-->";
            }
        }
        s += ((MonLieu) c.aretes().get(c.aretes().size() - 1).getArrivee()).cleanName();
        s += "\n" + getPoidsChemin(c, TypeCout.CO2) + " / " + getPoidsChemin(c, TypeCout.PRIX) + " / "
                + getPoidsChemin(c, TypeCout.TEMPS);
        return s;
    }

    /**
     * Retourne une représentation textuelle d'un chemin, incluant uniquement les
     * modalités de transport.
     *
     * @param c Le chemin à représenter.
     * @return Une chaîne de caractères décrivant le chemin.
     * @throws IOException En cas d'erreur lors de la récupération des modalités de
     *                     transport.
     */
    public static String OtherToString(Chemin c) throws IOException {
        String s = "";
        ModaliteTransport m = c.aretes().get(0).getModalite();
        s += ((MonLieu) c.aretes().get(0).getDepart()).cleanName() + "---" + m + "-->";
        for (Trancon t : c.aretes()) {
            if (t.getModalite() != m) {
                m = t.getModalite();
                s += ((MonLieu) t.getArrivee()).cleanName() + "---" + m + "-->";
            }
        }
        s += ((MonLieu) c.aretes().get(c.aretes().size() - 1).getArrivee()).cleanName();
        return s;
    }

    /**
     * Retourne le poids du chemin pour un type de coût donné.
     *
     * @param ch Le chemin dont le poids doit être calculé.
     * @param t  Le type de coût pour lequel le poids doit être calculé.
     * @return Le poids du chemin sous forme de chaîne de caractères.
     * @throws IOException En cas d'erreur lors de la récupération du poids.
     */
    public String getPoidsChemin(Chemin ch, TypeCout t) throws IOException {
        MultiGrapheOrienteValue g = graphes.associerGraphe(t);
        List<Chemin> l = calculKPCC(g, voyageur);
        String s = "";

        for (Chemin c : l) {
            if (comparerArete(c, ch)) {
                s = String.format("%.2f", c.poids());
                s += t.afficherEquivalent();
            }
        }
        return s;
    }

    /**
     * Retourne le poids du chemin pour un type de coût donné sous forme de double.
     *
     * @param ch Le chemin dont le poids doit être calculé.
     * @param t  Le type de coût pour lequel le poids doit être calculé.
     * @return Le poids du chemin sous forme de double.
     * @throws IOException En cas d'erreur lors de la récupération du poids.
     */
    public double getPoidsDoubleChemin(Chemin ch, TypeCout t) throws IOException {
        MultiGrapheOrienteValue g = graphes.associerGraphe(t);
        List<Chemin> l = calculKPCC(g, voyageur);
        String s = "";

        for (Chemin c : l) {
            if (comparerArete(c, ch)) {
                s = String.format("%.2f", c.poids());
            }
        }
        return Double.parseDouble(s);
    }

    /**
     * Permet de choisir les modalités de transport préférées par l'utilisateur.
     *
     * @return Un ensemble de modalités de transport choisies par l'utilisateur.
     */
    public static HashSet<ModaliteTransport> choixModaliteTransports() {
        HashSet<ModaliteTransport> modal = new HashSet<>();
        System.out.println("Voulez-vous voyager en train ? (oui/non)");
        while (true) {
            String userInput = getUserInuput();
            if (userInput.equalsIgnoreCase("oui")) {
                modal.add(ModaliteTransport.TRAIN);
                break;
            } else if (userInput.equalsIgnoreCase("non")) {
                break;
            } else {
                System.out.println("Choix incorrecte.");
            }
        }
        System.out.println("Voulez-vous voyager en bus ? (oui/non)");
        while (true) {
            String userInput = getUserInuput();
            if (userInput.equalsIgnoreCase("oui")) {
                modal.add(ModaliteTransport.BUS);
                break;
            } else if (userInput.equalsIgnoreCase("non")) {
                break;
            } else {
                System.out.println("Choix incorrecte.");
            }
        }
        System.out.println("Voulez-vous voyager en avion ? (oui/non)");
        while (true) {
            String userInput = getUserInuput();
            if (userInput.equalsIgnoreCase("oui")) {
                modal.add(ModaliteTransport.AVION);
                break;
            } else if (userInput.equalsIgnoreCase("non")) {
                break;
            } else {
                System.out.println("Choix incorrecte.");
            }
        }
        return modal;
    }

    /**
     * Permet de choisir une préférence pour un critère donné.
     *
     * @param c Le critère pour lequel la préférence doit être choisie.
     * @return La préférence choisie sous forme de nombre entre 0 et 100.
     */
    public static int choixCritere(TypeCout c) {
        System.out.println("Votre préférence pour le " + c.toString() + ". Entrez un nombre entre 0 et 100 :");
        int res = 0;
        while (true) {
            String userInput = getUserInuput();
            try {
                res = Integer.parseInt(userInput);
                if (res >= 0 || res <= 100) {
                    return res;
                }
            } catch (Exception e) {
                System.out.println("Erreur");
            }
            System.out.println("Entrez un nombre valide");
        }
    }

    /**
     * Retourne le graphe final représentant le réseau de transport.
     * 
     * @return Le graphe final de type MultiGrapheOrienteValue.
     */
    public MultiGrapheOrienteValue getGrapheFinal() {
        return grapheFinal;
    }

    /**
     * Retourne l'objet Voyageur associé à la plateforme.
     * 
     * @return L'objet Voyageur.
     */
    public Voyageur getVoyageur() {
        return voyageur;
    }

    /**
     * Définit le graphe final représentant le réseau de transport.
     * 
     * @param grapheFinal Le graphe final de type MultiGrapheOrienteValue à définir.
     */
    public void setGraphe(MultiGrapheOrienteValue grapheFinal) {
        this.grapheFinal = grapheFinal;
    }

    /**
     * Retourne l'objet Graphes associé à la plateforme.
     * 
     * @return L'objet Graphes.
     */
    public Graphes getGraphes() {
        return graphes;
    }

    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }
}
