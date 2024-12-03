package app;

import graphes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.RoadNotFoundException;
import exception.SameCityException;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlateforme {

    private Plateforme plateforme;
    private String[] data;

    final String SEPARATOR = System.getProperty("file.separator");
    final String FILENAME = "res" + SEPARATOR + "data.csv";
    final String FILENAMEC = "res" + SEPARATOR + "correspondance.csv";

    @BeforeEach
    public void setUp() throws IOException {
        data = new String[]{
            "villeA;villeB;Train;60;1.7;80",
            "villeB;villeD;Train;22;2.4;40",
            "villeA;villeC;Train;42;1.4;50",
            "villeB;villeC;Train;14;1.4;60",
            "villeC;villeD;Avion;110;150;22",
            "villeC;villeD;Train;65;1.2;90"
        };
        Graphes g = new Graphes(FILENAME, FILENAMEC);
        plateforme = new Plateforme(g);
        plateforme.getGraphes().setCoutChoisi(TypeCout.TEMPS);
    }

    @Test
    public void testVentilation() throws IOException {
        ArrayList<String[]> ventilatedData = plateforme.getGraphes().ventilation(data);
        assertEquals(6, ventilatedData.size());
        assertArrayEquals(new String[]{"villeA", "villeB", "Train", "60", "1.7", "80"}, ventilatedData.get(0));
        assertArrayEquals(new String[]{"villeB", "villeD", "Train", "22", "2.4", "40"}, ventilatedData.get(1));
        assertArrayEquals(new String[]{"villeC", "villeD", "Train", "65", "1.2", "90"}, ventilatedData.get(5));
        ventilatedData = Graphes.ventilation(FILENAME);
        assertEquals(6, ventilatedData.size());
        assertArrayEquals(new String[]{"villeA", "villeB", "Train", "60", "1.7", "80"}, ventilatedData.get(0));
        assertArrayEquals(new String[]{"villeB", "villeD", "Train", "22", "2.4", "40"}, ventilatedData.get(1));
        assertArrayEquals(new String[]{"villeC", "villeD", "Train", "65", "1.2", "90"}, ventilatedData.get(5));
    }

    @Test
    public void testExtraireLieu() throws IOException {
        ArrayList<String[]> ventilatedData = plateforme.getGraphes().ventilation(data);
        HashSet<Lieu> lieux = plateforme.getGraphes().extraireLieu(ventilatedData);
        assertEquals(5, lieux.size());
        ventilatedData = Graphes.ventilation(FILENAME);
        lieux = plateforme.getGraphes().extraireLieu(ventilatedData);
        assertEquals(5, lieux.size());
    }

    @Test
    public void testExtraireTroncon() throws IOException {
        ArrayList<String[]> ventilatedData = plateforme.getGraphes().ventilation(data);
        plateforme.getGraphes().extraireSommet(plateforme.getGraphes().getLieux(), plateforme.getGraphes().getGraphePrix());
        HashMap<Trancon, Double> troncons = plateforme.getGraphes().extraireTroncon(ventilatedData, TypeCout.PRIX);
        assertEquals(12, troncons.size()); //Tous les tronçons en train en aller-retour
    }

    @Test
    public void testGenererGraphe() throws IOException {
        ArrayList<String[]> ventilatedData = Graphes.ventilation(FILENAME);
        plateforme.getGraphes().genererGraphe(ventilatedData);

        assertEquals(5, plateforme.getGraphes().getGrapheCO2().sommets().size());
        assertEquals(5, plateforme.getGraphes().getGraphePrix().sommets().size());
        assertEquals(5, plateforme.getGraphes().getGrapheTemps().sommets().size());

        assertEquals(12, plateforme.getGraphes().getGrapheCO2().aretes().size());
        assertEquals(12, plateforme.getGraphes().getGraphePrix().aretes().size());
        assertEquals(12, plateforme.getGraphes().getGrapheTemps().aretes().size());
    }

    @Test
    public void testComparerArrete() {
        // Create lieux
        Lieu lieuA = new MonLieu("villeA");
        Lieu lieuB = new MonLieu("villeB");
        Lieu lieuC = new MonLieu("villeC");

        // Create troncons
        Troncon tronconABTrain = new Troncon(lieuA, lieuB, ModaliteTransport.TRAIN);
        Troncon tronconBATrain = new Troncon(lieuB, lieuA, ModaliteTransport.TRAIN);
        Troncon tronconBCTrain = new Troncon(lieuB, lieuC, ModaliteTransport.TRAIN);
        Troncon tronconCBTrain = new Troncon(lieuC, lieuB, ModaliteTransport.TRAIN);

        // Create chemins
        List<Trancon> aretes1 = Arrays.asList(tronconABTrain, tronconBCTrain);
        List<Trancon> aretes2 = Arrays.asList(tronconABTrain, tronconBCTrain);
        List<Trancon> aretes3 = Arrays.asList(tronconBATrain, tronconCBTrain);

        // Test comparerArrete
        assertTrue(Plateforme.comparerArete(aretes1, aretes2));
        assertFalse(Plateforme.comparerArete(aretes1, aretes3));
    }

    @Test
    public void testAssocierGraphe() {
        MultiGrapheOrienteValue grapheCO2 = plateforme.getGraphes().getGrapheCO2();
        MultiGrapheOrienteValue graphePrix = plateforme.getGraphes().getGraphePrix();
        MultiGrapheOrienteValue grapheTemps = plateforme.getGraphes().getGrapheTemps();

        assertSame(grapheCO2, plateforme.getGraphes().associerGraphe(TypeCout.CO2));
        assertSame(graphePrix, plateforme.getGraphes().associerGraphe(TypeCout.PRIX));
        assertSame(grapheTemps, plateforme.getGraphes().associerGraphe(TypeCout.TEMPS));
    }

    @Test
    public void testEstDansTypeCout() {
        assertTrue(Plateforme.estDansTypeCout("CO2"));
        assertTrue(Plateforme.estDansTypeCout("prix"));
        assertTrue(Plateforme.estDansTypeCout("TeMpS"));
        
        assertFalse(Plateforme.estDansTypeCout(""));
        assertFalse(Plateforme.estDansTypeCout(null));
    }

    @Test
    public void testEstDansModaliteTransport() {
        assertTrue(Plateforme.estDansModaliteTransport("Train"));
        assertTrue(Plateforme.estDansModaliteTransport("AvIoN"));
        assertTrue(Plateforme.estDansModaliteTransport("buS"));
        
        assertFalse(Plateforme.estDansModaliteTransport(""));
        assertFalse(Plateforme.estDansModaliteTransport(null));
    }

    @Test
    public void isDouble(){
        assertTrue(Plateforme.isDouble("2.5"));
        assertTrue(Plateforme.isDouble("4"));
        assertTrue(Plateforme.isDouble("8.4"));

        assertFalse(Plateforme.isDouble(""));
        assertFalse(Plateforme.isDouble(null));
        assertFalse(Plateforme.isDouble("marge"));
    }

    @Test
    public void testClearMap() throws IOException{
        ArrayList<String[]> ventilatedData = plateforme.getGraphes().ventilation(data);
        plateforme.getGraphes().extraireSommet(plateforme.getGraphes().getLieux(), plateforme.getGraphes().getGraphePrix());
        plateforme.getGraphes().extraireTroncon(ventilatedData, TypeCout.PRIX);
        assertEquals(12, plateforme.getGraphes().getTroncons().size());
        plateforme.getGraphes().clearMap();
        assertEquals(0, plateforme.getGraphes().getTroncons().size());

        ventilatedData.clear();

        ventilatedData = Graphes.ventilation(FILENAME);
        plateforme.getGraphes().extraireSommet(plateforme.getGraphes().getLieux(), plateforme.getGraphes().getGraphePrix());
        plateforme.getGraphes().extraireTroncon(ventilatedData, TypeCout.PRIX);
        assertEquals(12, plateforme.getGraphes().getTroncons().size());
        plateforme.getGraphes().clearMap();
        assertEquals(0, plateforme.getGraphes().getTroncons().size());
    }

    @Test
    public void getTypeCout() throws IOException{
        ArrayList<String[]> ventilatedData = plateforme.getGraphes().ventilation(data);
        assertEquals(60, plateforme.getGraphes().getTypeCout(3, ventilatedData));
        assertEquals(90, plateforme.getGraphes().getTypeCout(5, ventilatedData));
        assertEquals(80, plateforme.getGraphes().getTypeCout(0, ventilatedData));

        ventilatedData = Graphes.ventilation(FILENAME);
        assertEquals(60, plateforme.getGraphes().getTypeCout(3, ventilatedData));
        assertEquals(90, plateforme.getGraphes().getTypeCout(5, ventilatedData));
        assertEquals(80, plateforme.getGraphes().getTypeCout(0, ventilatedData));
    }

    @Test
    public void testV2() throws IOException, SameCityException, RoadNotFoundException{
        ArrayList<String[]> ventilatedData = Graphes.ventilation(FILENAME);
        ArrayList<String[]> ventilatedCorrespondance = Graphes.ventilation(FILENAMEC);
        Graphes graphes = new Graphes(FILENAME, FILENAMEC);
        Plateforme plateforme = new Plateforme(graphes);
        // plateforme.getGraphes().setM(plateforme.choisirTransport());
        graphes.genererGraphe(ventilatedData, ventilatedCorrespondance);
        // Cout choisi a la valeur de tri
        graphes.setCoutChoisi(TypeCout.TEMPS);
        TypeCout cout = graphes.getCoutChoisi();
        plateforme.setGraphe(graphes.associerGraphe(cout));
        for(Lieu l : graphes.getLieux()){
            if(l.toString().equals("villeA")){
                plateforme.getVoyageur().setDepart(l);
            } 
            if(l.toString().equals("villeD")){
                plateforme.getVoyageur().setArrivee(l);
            }
        }
        List<Chemin> c = plateforme.calculKPCC(plateforme.getGrapheFinal(), plateforme.getVoyageur());
        String s = Main.toString(c, cout);
        String l1 = s.split("\n")[0];
        assertEquals(l1, "1) villeA---TRAIN-->villeC---AVION-->villeD : 92.0 min");
    }

    @Test
    public void testV3() throws IOException{
        ArrayList<String[]> ventilatedData = Graphes.ventilation(FILENAME);
        ArrayList<String[]> ventilatedCorrespondance = Graphes.ventilation(FILENAMEC);
        Graphes graphes = new Graphes(FILENAME, FILENAMEC);
        Plateforme plateforme = new Plateforme(graphes);
        HashSet<ModaliteTransport> modal = new HashSet<>();
        modal.add(ModaliteTransport.TRAIN);
        modal.add(ModaliteTransport.BUS);
        graphes.genererGraphe(ventilatedData, ventilatedCorrespondance, modal);
        for(Lieu l : graphes.getLieux()){
            if(l.toString().equals("villeA")){
                plateforme.getVoyageur().setDepart(l);
            } 
            if(l.toString().equals("villeD")){
                plateforme.getVoyageur().setArrivee(l);
            }
        }
        plateforme.getGraphes().createCoeffGraphe(100 / 100, 50 / 100, 0 / 100);
        List<Chemin> c = plateforme.calculKPCC(plateforme.getGraphes().getGrapheCoeff(), plateforme.getVoyageur());
        Trajet t1 = new Trajet(c.get(0), plateforme.getPoidsChemin(c.get(0), TypeCout.CO2), plateforme.getPoidsChemin(c.get(0), TypeCout.PRIX), plateforme.getPoidsChemin(c.get(0), TypeCout.TEMPS));
        assertEquals(t1.toString(), "villeA---TRAIN-->villeD\n2,60kgCO2 / 140,00min / 107,00euros");
    }
}