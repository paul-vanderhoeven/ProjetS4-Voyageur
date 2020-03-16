package projetS3Voyageur;

import projetS3Voyageur.CompositionPays.Pays;

import java.awt.*;

class App {

    public static void main(String[] args) throws Exception {

        /*Point p1 = new Point(200, 0);
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
        Point p14 = new Point(200, -150);
        Point p15 = new Point(250, -50);

        double distance = p1.distance(p2) + p2.distance(p3) + p3.distance(p4) + p4.distance(p5) + p5.distance(p6) + p6.distance(p7) + p7.distance(p8) + p8.distance(p9) + p9.distance(p10) + p10.distance(p11) + p11.distance(p12) + p12.distance(p13) + p13.distance(p14) + p14.distance(p15) + p15.distance(p1);
        System.out.println(distance);*/

        Point p1 = new Point(100, 0);
        Point p3 = new Point(-100, 150);
        Point p5 = new Point(-200, -150);
        Point p6 = new Point(-100, -250);
        Point p7 = new Point(50, -250);
        Pays p = new Pays(5);

        double distance = p1.distance(p3) + p3.distance(p5) + p5.distance(p6) + p6.distance(p7) + p7.distance(p1);
        System.out.println(distance);
    }
}
