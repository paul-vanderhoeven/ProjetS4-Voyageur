package ProjetS4.ModesDeRecherches;

import org.junit.Before;
import org.junit.Test;
import projetS3Voyageur.CompositionPays.Pays;
import projetS3Voyageur.ModesDeRecherches.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class TestAlgos5villes {

    Point p1 = new Point(100, 0);
    Point p3 = new Point(-100, 150);
    Point p5 = new Point(-200, -150);
    Point p6 = new Point(-100, -250);
    Point p7 = new Point(50, -250);
    Pays p = new Pays(5);

    @Before
    public void init() {

        ArrayList<Point> list = new ArrayList<>();
        list.add(p1);
        list.add(p3);
        list.add(p5);
        list.add(p6);
        list.add(p7);
        Collections.shuffle(list);

        for (int i = 0; i < p.getNombreDeVilles(); i++) {
            p.setPositionVille(i, list.get(i));
        }
    }


    @Test
    public void test_5villes_backTrack() {
        BackTrackV2 backTrackV2 = new BackTrackV2();
        assertEquals(1112, test_algos_5villes(backTrackV2));
    }
    @Test
    public void test_5villes_brutforce() {
        BrutForceV2 brutForceV2 = new BrutForceV2();
        assertEquals(1112, test_algos_5villes(brutForceV2));
    }
    @Test
    public void test_5villes_PlusProche() {
        PlusProcheV3 plusProcheV3 = new PlusProcheV3();
        assertEquals(1112, test_algos_5villes(plusProcheV3));
    }
    @Test
    public void test_5villes_PPMulti() {
        PPMulti ppmulti = new PPMulti();
        assertEquals(1112, test_algos_5villes(ppmulti));
    }
    @Test
    public void test_5villes_TrackProchesMulti() {
        TrackProchesMulti trackProchesMulti = new TrackProchesMulti();
        assertEquals(1112, test_algos_5villes(trackProchesMulti));
    }
    @Test
    public void test_5villes_TrackProches() {
        TrackProchesV2_1 trackProchesV2_1 = new TrackProchesV2_1();
        assertEquals(1112, test_algos_5villes(trackProchesV2_1));
    }

    private int test_algos_5villes(ModeRecherche algo) {
        algo.recherche(p, 0);
        return (int) algo.getParcours().getDistance();
    }
}
