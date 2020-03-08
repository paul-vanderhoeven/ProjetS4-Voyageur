package ProjetS4;

import static java.lang.Math.hypot;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import projetS3Voyageur.CompositionPays.Pays;
import java.awt.*;



public class PaysTest {
    private Pays p;

    @Before
    public void init() {
        p = new Pays(4);
        p.setPositionVille(0,new Point(0,5));
        p.setPositionVille(1,new Point(3,1));
        p.setPositionVille(2,new Point(0,1));
        p.setPositionVille(3,new Point(2,4));
    }

    @Test
    public void test_positionVille0(){
        assertEquals(p.getPositionVille(0), new Point(0,5));
    }

    @Test
    public void test_positionVille1(){
        assertEquals(p.getPositionVille(1), new Point(3,1));
    }

    @Test
    public void test_positionVille2(){
        assertEquals(p.getPositionVille(2), new Point(0,1));
    }

    @Test
    public void test_positionVille3(){
        assertEquals(p.getPositionVille(3), new Point(2,4));
    }

    @Test
    public void test_distance1(){
        assertEquals(p.getDistanceEntreVilles(0,2), hypot(0, 4));
    }

    @Test
    public void test_distance2(){
        assertEquals(p.getDistanceEntreVilles(1,3), hypot(1,-3));
    }

    @Test
    public void test_distance3(){
        assertEquals(p.getDistanceEntreVilles(0,3), hypot(-2,1));
    }

    @Test
    public void test_distance4(){
        assertEquals(p.getDistanceEntreVilles(1,2), hypot(3,0));
    }

    @Test
    public void test_distance5(){
        assertEquals(p.getDistanceEntreVilles(0,1), hypot(-3,4));
    }

    @Test
    public void test_distance6(){
        assertEquals(p.getDistanceEntreVilles(2,3), hypot(-2,-3));
    }
}
