package ProjetS4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import projetS3Voyageur.CompositionPays.Villes;

import java.awt.*;
import java.util.ArrayList;

public class VillesTest {

    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;

    @Before
    public void b4() {
        p1 = new Point(35, 54);
        p2 = new Point(64, 33);
        p3 = new Point(32, 76);
        p4 = new Point(11, 22);
    }

    @Test
    public void test_villes_inferieur3() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Villes(2));
        assertThrows(IndexOutOfBoundsException.class, () -> new Villes(1));
        assertThrows(IndexOutOfBoundsException.class, () -> new Villes(0));
    }
    @Test
    public void test_villes_entre3_15() {
        for (int i = 3; i < 16; i++) {
            Villes v = new Villes(i);
            assertNotNull(v);
            assertEquals(i, v.getNombreDeVilles());
        }
    }
    @Test
    public void test_villes_supérieur15() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Villes(16));
        assertThrows(IndexOutOfBoundsException.class, () -> new Villes(17));
        assertThrows(IndexOutOfBoundsException.class, () -> new Villes(22));
    }

    @Test
    public void test_villes_points() {
        ArrayList<Point> l = new ArrayList<Point>();
        l.add(p1);
        l.add(p2);
        l.add(p3);
        l.add(p4);
        Villes v = new Villes(l);
        assertNotNull(v);

        assertEquals(4, v.getNombreDeVilles());
        assertEquals(l.get(0), v.getPositionVille(0));
        assertEquals(l.get(1), v.getPositionVille(1));
        assertEquals(l.get(2), v.getPositionVille(2));
        assertEquals(l.get(3), v.getPositionVille(3));

        assertThrows(IndexOutOfBoundsException.class, () -> v.getPositionVille(4));

    }

    @Test public void test_setPositionVilles() {
        Villes v = new Villes(4);

        v.setPositionVille(0, p1);
        v.setPositionVille(1, p2);
        v.setPositionVille(2, p3);
        v.setPositionVille(3, p4);

        assertThrows(IndexOutOfBoundsException.class, () -> v.setPositionVille(4, new Point(43, 54)));

    }
}
