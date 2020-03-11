package projetS3Voyageur;

import projetS3Voyageur.ModesDeRecherches.*;
import projetS3Voyageur.StatsAlgos.Analyser;
import projetS3Voyageur.StatsAlgos.CSV;
import projetS3Voyageur.StatsAlgos.Comparer;
import projetS3Voyageur.StatsAlgos.GenererCSV;

import javax.sound.midi.Track;
import java.awt.*;
import java.util.ArrayList;

class App {

    public static void main(String[] args) throws Exception {

        /*ModeRecherche[] algos = new ModeRecherche[6];
        algos[0] = new BackTrackV2();
        algos[1] = new BrutForceV4();
        algos[2] = new PlusProcheV3();
        algos[3] = new PPMulti();
        algos[4] = new TrackProchesMulti();
        algos[5] = new TrackProchesV2_1();

        Analyser a = new Analyser(algos, 4, 1, 10);

        a.analyse();

        System.out.println(algos[0].getParcours().getVillesEmprunté());
        System.out.println(algos[0].getParcours().toString());*/

        Point p1 = new Point(100, 0);
        Point p2 = new Point(50, 100);
        Point p3 = new Point(-100, 150);
        Point p4 = new Point(-200, 0);
        Point p5 = new Point(-200, -150);
        Point p6 = new Point(-100, -250);
        Point p7 = new Point(50, -250);
        Point p8 = new Point(150, -100);

        double distance = p1.distance(p3) + p3.distance(p4) + p4.distance(p5) + p5.distance(p7) + p7.distance(p1);
        System.out.println(distance);
    }
}
