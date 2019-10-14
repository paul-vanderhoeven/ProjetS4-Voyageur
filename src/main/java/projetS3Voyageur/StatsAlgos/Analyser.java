package projetS3Voyageur.StatsAlgos;

import projetS3Voyageur.Pays;
import projetS3Voyageur.ModesDeRecherches.*;

public class Analyser {

    private static final String BARRE_DE_CHARGEMENT_INIT = "[#...................................................................................................]";

    private byte nbVillesMax = 12;
    private int nbIteration = 100;

    private ModeRecherche algo;

    private double[] tempsMoyenParVilles = new double[nbVillesMax - 3];
    private double[] margeErreurParVilles = new double[nbVillesMax - 3];

    private String etapeChargementAttein = "##";
    private String etapeChargementNonAttein = "#.";
    private String barreDeChargement = BARRE_DE_CHARGEMENT_INIT;

    public Analyser(ModeRecherche algo) {
        this.algo = algo;
    }

    /**
     * @param nbVillesMax
     * @param nbIteration
     * @param algo
     */
    public Analyser(byte nbVillesMax, int nbIteration, ModeRecherche algo) {
        this.nbVillesMax = nbVillesMax;
        this.nbIteration = nbIteration;
        this.algo = algo;
        this.tempsMoyenParVilles = new double[nbVillesMax - 3];
        this.margeErreurParVilles = new double[nbVillesMax - 3];

    }

    // #region setter / Getter

    /**
     * @param nbVillesMax the nbVillesMax to set
     */
    public void setNbVillesMax(byte nbVillesMax) {
        this.nbVillesMax = nbVillesMax;
    }

    /**
     * @param nbIteration the nbIteration to set
     */
    public void setNbIteration(int nbIteration) {
        this.nbIteration =  nbIteration;
    }

    // #endregion setter/ getter

    public void afficher() {
        barreDeChargementInit(nbIteration);
        for (byte nbVille = 3; nbVille < nbVillesMax; nbVille++) {
            barreDeChargement = BARRE_DE_CHARGEMENT_INIT;
            for (byte i = 0; i < nbIteration; i++) {
                double tempsExecution = calculeTempsExecution(nbVille);
                tempsMoyenParVilles[nbVille - 3] += tempsExecution / nbIteration;
                margeErreurParVilles[nbVille - 3] += (Math.pow(tempsExecution, 2)) / nbIteration;
                barreDeChargement(i, nbVille);
            }
        }

        for (int i = 0; i < tempsMoyenParVilles.length; i++) {
            double ecartType = Math.sqrt(margeErreurParVilles[i] - Math.pow(tempsMoyenParVilles[i], 2));
            System.out.println("\n Résultat avec " + (i + 3) + " villes  :\n Temps moyen de recherche : "
                    + tempsMoyenParVilles[i] + "\n Marge d'erreur : " + ecartType);

        }
    }

    // #region Barre de chargement

    private void barreDeChargementInit(int iMax) {
        for (int i = 1; i < (int) ((((double) 1) / ((double) iMax)) * 100); i++) {
            etapeChargementAttein += '#';
            etapeChargementNonAttein += '.';
        }
    }

    private void barreDeChargement(byte i, byte nbVille) {
        int charge = (int) ((((double) i) / ((double) nbIteration)) * 100);
        int chargePrecedant = ((int) ((((double) (i - 1)) / ((double) nbIteration)) * 100));
        if ((charge - chargePrecedant) != 0) {
            barreDeChargement = barreDeChargement.replace(etapeChargementNonAttein, etapeChargementAttein);
        }
        System.out.print("\r" + (nbVille + "/" + (nbVillesMax - 1) + " " + barreDeChargement));
    }

    // #endregion barre de Chargement

    // #region Calcul
    private long calculeTempsExecution(byte nbVilles) {
        long startTime = System.currentTimeMillis();
        algo.recherche(new Pays(nbVilles), 0);
        ;
        long endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }
    // #endregion Calcul

}