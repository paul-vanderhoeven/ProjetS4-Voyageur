package ProjetS4.ModesDeRecherches;

import org.junit.Before;
import org.junit.Test;
import projetS3Voyageur.CompositionPays.Pays;
import projetS3Voyageur.ModesDeRecherches.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestAlgos6villes {

    Point p1 = new Point(100, 0);
    Point p3 = new Point(-100, 150);
    Point p4 = new Point(-200, 0);
    Point p5 = new Point(-200, -150);
    Point p6 = new Point(-100, -250);
    Point p7 = new Point(50, -250);
    Pays p = new Pays(6);

    @Before
    public void init() {
        p.setPositionVille(0, p1);
        p.setPositionVille(1, p3);
        p.setPositionVille(2, p4);
        p.setPositionVille(3, p5);
        p.setPositionVille(4, p6);
        p.setPositionVille(5, p7);
    }


    @Test
    public void test_6villes_backTrack() {
        BackTrackV2 backTrackV2 = new BackTrackV2();
        assertEquals(1126, test_algos_6villes(backTrackV2));
    }
    @Test
    public void test_6villes_brutforce() {
        BrutForceV2 brutForceV2 = new BrutForceV2();
        assertEquals(1126, test_algos_6villes(brutForceV2));
    }
    @Test
    public void test_6villes_PlusProche() {
        PlusProcheV3 plusProcheV3 = new PlusProcheV3();
        assertEquals(1126, test_algos_6villes(plusProcheV3));
    }
    @Test
    public void test_6villes_PPMulti() {
        PPMulti ppmulti = new PPMulti();
        assertEquals(1126, test_algos_6villes(ppmulti));
    }
    @Test
    public void test_6villes_TrackProchesMulti() {
        TrackProchesMulti trackProchesMulti = new TrackProchesMulti();
        assertEquals(1126, test_algos_6villes(trackProchesMulti));
    }
    @Test
    public void test_6villes_TrackProches() {
        TrackProchesV2_1 trackProchesV2_1 = new TrackProchesV2_1();
        assertEquals(1126, test_algos_6villes(trackProchesV2_1));
    }

    private int test_algos_6villes(ModeRecherche algo) {
        algo.recherche(p, 0);
        return (int) algo.getParcours().getDistance();
    }
}
