package ProjetS4.ModesDeRecherches;

import projetS3Voyageur.ModesDeRecherches.*;
import projetS3Voyageur.StatsAlgos.Analyser;

import java.util.Arrays;

public class TestAlgosPerformances {

    public static void main(String[] args) {
        ModeRecherche[] algos = new ModeRecherche[3];
        algos[0] = new BackTrackV2();
        algos[1] = new BrutForceV2();
        algos[2] = new PlusProcheV3();
        //algos[3] = new PPMulti();
        //algos[4] = new TrackProchesMulti();
        //algos[5] = new TrackProchesV2_1();

        Analyser a = new Analyser(algos, 3, 1, 10);

        for (int i = 3; i < 16; i++) {
            a.setNombreDeVilles(i);
            a.analyse();

            double[] min = a.getListTempsMoyenAlgo();
            Arrays.sort(min);
            System.out.println("Nombres de villes: " + i + " - Temps: " + Arrays.toString(min) + " - Marge d'erreurs: " + a.getMargeErreurCurrentTime() + "%");
        }

    }
}
