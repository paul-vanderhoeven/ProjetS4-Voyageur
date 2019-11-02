package projetS3Voyageur.StatsAlgos;

import projetS3Voyageur.Pays;
import projetS3Voyageur.ModesDeRecherches.BrutForceV2;
import projetS3Voyageur.ModesDeRecherches.ModeRecherche;

public class VarianceCurrentTime {

    private final ModeRecherche ALGOREFERENCE = new BrutForceV2();
    private final Pays PAYSREFERENCE = new Pays(9);
    private double esperanceCurrentTime = 0.;
    private double esperanceCarreCurrentTime = 0.;

    private int nombreDeTests;

    /**
     * @param nombreDeTests {@code int} nombre de tests que l'on compte effectuer
     */
    public VarianceCurrentTime(int nombreDeTests) {
        this.nombreDeTests = nombreDeTests * 2;
    }

    /**
     * Calcul pour une itération le temps mit pour exécuter l'algorithme ayant un
     * temps de résolution stable (théoriquement le temps de résolution ne devrait
     * pas varier) donc la seule variance est celle du CurrentTime
     */
    public void calcul() {
        double tempsExecution = TempsExecution.calcule(ALGOREFERENCE, PAYSREFERENCE);
        esperanceCurrentTime += tempsExecution / (nombreDeTests);
        esperanceCarreCurrentTime += (Math.pow(tempsExecution, 2)) / (nombreDeTests);
    }

    /**
     * Renvoie la marge d'erreur en % du CurrentTime
     * 
     * @return {@code double}
     */
    public double getMargeErreur() {
        double variance = esperanceCarreCurrentTime - Math.pow(esperanceCurrentTime, 2);
        return Math.sqrt(variance) / (esperanceCurrentTime / 100);
    }

}