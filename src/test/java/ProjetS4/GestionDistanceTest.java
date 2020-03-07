package ProjetS4;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import projetS3Voyageur.CompositionPays.GestionDistance;
import projetS3Voyageur.CompositionPays.Villes;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GestionDistanceTest {

    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    private GestionDistance g;

    @Before
    public void init() {
        p1 = new Point(0, 0);
        p2 = new Point(1, 0);
        p3 = new Point(0, 1);
        p4 = new Point(1, 1);

        Villes v = new Villes(4);

        v.setPositionVille(0, p1);
        v.setPositionVille(1, p2);
        v.setPositionVille(2, p3);
        v.setPositionVille(3, p4);

        g = new GestionDistance(v);
    }

    @Test
    public void test_distance_4villes() {
        assertEquals(0, g.getDistance(0, 0));
        assertEquals(1, g.getDistance(0, 1));
        assertEquals(1, g.getDistance(0, 2));
        assertEquals(Math.sqrt(2), g.getDistance(0, 3));

        assertEquals(0,g.getDistance(1, 1));
        assertEquals(Math.sqrt(2), g.getDistance(1, 2));
        assertEquals(1, g.getDistance(1, 3));

        assertEquals(0,g.getDistance(2, 2));
        assertEquals(1, g.getDistance(2, 3));

        assertEquals(0, g.getDistance(3, 3));
    }

    @Test
    public void test_actualisation_pt1() {
        p2.setLocation(2, 0);

        assertEquals(1, g.getDistance(1, 0));
        assertEquals(0,g.getDistance(1, 1));
        assertEquals(Math.sqrt(2), g.getDistance(1, 2));
        assertEquals(1, g.getDistance(1, 3));

        g.actualiseNumVille(1);

        assertEquals(0, g.getDistance(1, 1));
        assertEquals(2, g.getDistance(1, 0));
        assertEquals(Math.sqrt(5), g.getDistance(1, 2));
        assertEquals(Math.sqrt(2), g.getDistance(1, 3));
    }

    @Test
    public void test_distance_x_et_y_egale_y_et_x() {
        assertEquals(0, g.getDistance(0, 0));

        assertEquals(1, g.getDistance(0, 1));
        assertEquals(1, g.getDistance(1, 0));

        assertEquals(1, g.getDistance(0, 2));
        assertEquals(1, g.getDistance(2, 0));


        assertEquals(Math.sqrt(2), g.getDistance(0, 3));
        assertEquals(Math.sqrt(2), g.getDistance(3, 0));

        assertEquals(0,g.getDistance(1, 1));

        assertEquals(Math.sqrt(2), g.getDistance(2, 1));
        assertEquals(Math.sqrt(2), g.getDistance(1, 2));

        assertEquals(1, g.getDistance(1, 3));
        assertEquals(1, g.getDistance(3, 1));

        assertEquals(0,g.getDistance(2, 2));

        assertEquals(1, g.getDistance(2, 3));
        assertEquals(1, g.getDistance(3, 2));

        assertEquals(0, g.getDistance(3, 3));
    }

    @Test
    public void test_getDistanceRandom() {
        int rx1 = (int) (Math.random() * 10000);
        int ry1 = (int) (Math.random() * 10000);
        int rx2 = (int) (Math.random() * 10000);
        int ry2 = (int) (Math.random() * 10000);

        Point p5 = new Point(rx1, ry1);
        Point p6 = new Point(rx2, ry2);
        Point p7 = new Point(0, 1);
        Point p8 = new Point(1, 1);

        Villes v = new Villes(4);

        v.setPositionVille(0, p5);
        v.setPositionVille(1, p6);
        v.setPositionVille(2, p7);
        v.setPositionVille(3, p8);

        GestionDistance g2 = new GestionDistance(v);

        assertEquals(hypotegnius(p5, p5), g2.getDistance(0, 0));
        assertEquals(hypotegnius(p5, p6), g2.getDistance(0, 1));
        assertEquals(hypotegnius(p5, p7), g2.getDistance(0, 2));
        assertEquals(hypotegnius(p5, p8), g2.getDistance(0, 3));

        assertEquals(hypotegnius(p6, p6), g2.getDistance(1, 1));
        assertEquals(hypotegnius(p6, p7), g2.getDistance(1, 2));
        assertEquals(hypotegnius(p6, p8), g2.getDistance(1, 3));
    }

    private double hypotegnius(Point ville1, Point ville2) {
        return Math.sqrt(Math.pow(ville1.getX() - ville2.getX(), 2) + Math.pow(ville1.getY() - ville2.getY(), 2));
    }
}
