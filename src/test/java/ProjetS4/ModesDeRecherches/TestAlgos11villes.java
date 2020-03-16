package ProjetS4.ModesDeRecherches;

import org.junit.Before;
import org.junit.Test;
import projetS3Voyageur.CompositionPays.Pays;
import projetS3Voyageur.ModesDeRecherches.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class TestAlgos11villes {

    Point p1 = new Point(200, 0);
    Point p2 = new Point(150, 150);
    Point p3 = new Point(50, 250);
    Point p4 = new Point(-100, 250);
    Point p5 = new Point(-250, 150);
    Point p6 = new Point(-350, 0);
    Point p7 = new Point(-350, -200);
    Point p8 = new Point(-300, -450);
    Point p9 = new Point(-150, -550);
    Point p10 = new Point(0, -550);
    Point p11 = new Point(100, -500);
    Pays p = new Pays(11);

    @Before
    public void init() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        list.add(p7);
        list.add(p8);
        list.add(p9);
        list.add(p10);
        list.add(p11);
        Collections.shuffle(list);

        for (int i = 0; i < p.getNombreDeVilles(); i++) {
            p.setPositionVille(i, list.get(i));
        }
    }

    @Test
    public void test_11villes_backTrack() {
        BackTrackV2 backTrackV2 = new BackTrackV2();
        assertEquals(2217, test_algos_11villes(backTrackV2));
    }
    @Test
    public void test_11villes_brutforce() {
        BrutForceV2 brutForceV2 = new BrutForceV2();
        assertEquals(2217, test_algos_11villes(brutForceV2));
    }
    @Test
    public void test_11villes_PlusProche() {
        PlusProcheV3 plusProcheV3 = new PlusProcheV3();
        assertEquals(2217, test_algos_11villes(plusProcheV3));
    }
    @Test
    public void test_11villes_PPMulti() {
        PPMulti ppmulti = new PPMulti();
        assertEquals(2217, test_algos_11villes(ppmulti));
    }

    @Test
    public void test_11villes_PPMulti_plsrs_calcul() {
        PPMulti ppmulti = new PPMulti();

        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);

        assertEquals(2217, (int) ppmulti.getParcours().getDistance());
    }
    @Test
    public void test_11villes_TrackProchesMulti() {
        TrackProchesMulti trackProchesMulti = new TrackProchesMulti();
        assertEquals(2217, test_algos_11villes(trackProchesMulti));
    }
    @Test
    public void test_11villes_TrackProches() {
        TrackProchesV2_1 trackProchesV2_1 = new TrackProchesV2_1();
        assertEquals(2217, test_algos_11villes(trackProchesV2_1));
    }

    private int test_algos_11villes(ModeRecherche algo) {
        algo.recherche(p, 0);
        return (int) algo.getParcours().getDistance();
    }
}
