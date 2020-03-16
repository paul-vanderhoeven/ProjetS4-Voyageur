package ProjetS4.ModesDeRecherches;

import org.junit.Before;
import org.junit.Test;
import projetS3Voyageur.CompositionPays.Pays;
import projetS3Voyageur.ModesDeRecherches.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestAlgos8villes {

    Point p1 = new Point(100, 0);
    Point p2 = new Point(50, 100);
    Point p3 = new Point(-100, 150);
    Point p4 = new Point(-200, 0);
    Point p5 = new Point(-200, -150);
    Point p6 = new Point(-100, -250);
    Point p7 = new Point(50, -250);
    Point p8 = new Point(150, -100);
    Pays p = new Pays(8);

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
        Collections.shuffle(list);

        for (int i = 0; i < p.getNombreDeVilles(); i++) {
            p.setPositionVille(i, list.get(i));
        }
    }


    @Test
    public void test_8villes_backTrack() {
        BackTrackV2 backTrackV2 = new BackTrackV2();
        assertEquals(1183, test_algos_8villes(backTrackV2));
    }
    @Test
    public void test_8villes_brutforce() {
        BrutForceV2 brutForceV2 = new BrutForceV2();
        assertEquals(1183, test_algos_8villes(brutForceV2));
    }
    @Test
    public void test_8villes_PlusProche() {
        PlusProcheV3 plusProcheV3 = new PlusProcheV3();
        assertEquals(1183, test_algos_8villes(plusProcheV3));
    }
    @Test
    public void test_8villes_PPMulti() {
        PPMulti ppmulti = new PPMulti();
        assertEquals(1183, test_algos_8villes(ppmulti));
    }
    @Test
    public void test_8villes_TrackProchesMulti() {
        TrackProchesMulti trackProchesMulti = new TrackProchesMulti();
        assertEquals(1183, test_algos_8villes(trackProchesMulti));
    }
    @Test
    public void test_8villes_TrackProches() {
        TrackProchesV2_1 trackProchesV2_1 = new TrackProchesV2_1();
        assertEquals(1183, test_algos_8villes(trackProchesV2_1));
    }

    private int test_algos_8villes(ModeRecherche algo) {
        algo.recherche(p, 0);
        return (int) algo.getParcours().getDistance();
    }
}

