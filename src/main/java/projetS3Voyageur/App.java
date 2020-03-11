package projetS3Voyageur;

import projetS3Voyageur.ModesDeRecherches.*;
import projetS3Voyageur.StatsAlgos.Analyser;
import projetS3Voyageur.StatsAlgos.CSV;
import projetS3Voyageur.StatsAlgos.Comparer;
import projetS3Voyageur.StatsAlgos.GenererCSV;

import javax.sound.midi.Track;
import java.util.ArrayList;

class App {

    public static void main(String[] args) throws Exception {

        ModeRecherche[] algos = new ModeRecherche[6];
        algos[0] = new BackTrackV2();
        algos[1] = new BrutForceV4();
        algos[2] = new PlusProcheV3();
        algos[3] = new PPMulti();
        algos[4] = new TrackProchesMulti();
        algos[5] = new TrackProchesV2_1();

        Analyser a = new Analyser(algos, 4, 1, 10);

        a.analyse();
        a.afficher();
    }
}
