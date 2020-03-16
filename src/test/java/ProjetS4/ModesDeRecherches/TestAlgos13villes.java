package ProjetS4.ModesDeRecherches;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import projetS3Voyageur.CompositionPays.Pays;
import projetS3Voyageur.ModesDeRecherches.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestAlgos13villes {

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
    Point p12 = new Point(200, -400);
    Point p13 = new Point(250, -200);
    Pays p = new Pays(13);

    @Before
    public void init() {
        p.setPositionVille(0, p1);
        p.setPositionVille(1, p2);
        p.setPositionVille(2, p3);
        p.setPositionVille(3, p4);
        p.setPositionVille(4, p5);
        p.setPositionVille(5, p6);
        p.setPositionVille(6, p7);
        p.setPositionVille(7, p8);
        p.setPositionVille(8, p9);
        p.setPositionVille(9, p10);
        p.setPositionVille(10, p11);
        p.setPositionVille(11, p12);
        p.setPositionVille(12, p13);
    }

    @Test
    public void test_13villes_backTrack() {
        BackTrackV2 backTrackV2 = new BackTrackV2();
        assertEquals(2260, test_algos_13villes(backTrackV2));
    }
    /*@Test     Trop long
    public void test_13villes_brutforce() {
        BrutForceV2 brutForceV2 = new BrutForceV2();
        assertEquals(2260, test_algos_13villes(brutForceV2));
    }*/
    @Test
    public void test_13villes_PlusProche() {
        PlusProcheV3 plusProcheV3 = new PlusProcheV3();
        assertEquals(2260, test_algos_13villes(plusProcheV3));
    }
    @Test
    public void test_13villes_PPMulti() {
        PPMulti ppmulti = new PPMulti();
        assertEquals(2260, test_algos_13villes(ppmulti));
    }

    @Test
    public void test_13villes_PPMulti_plsrs_calcul() {
        PPMulti ppmulti = new PPMulti();

        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);
        ppmulti.recherche(p, 0);

        assertEquals(2260, (int) ppmulti.getParcours().getDistance());
    }
    @Test
    public void test_13villes_TrackProchesMulti() {
        TrackProchesMulti trackProchesMulti = new TrackProchesMulti();
        assertEquals(2260, test_algos_13villes(trackProchesMulti));
    }
    @Test
    public void test_13villes_TrackProches() {
        TrackProchesV2_1 trackProchesV2_1 = new TrackProchesV2_1();
        assertEquals(2260, test_algos_13villes(trackProchesV2_1));
    }

    private int test_algos_13villes(ModeRecherche algo) {
        algo.recherche(p, 0);
        return (int) algo.getParcours().getDistance();
    }
}
