package projetS3Voyageur.ModesDeRecherches;

import java.util.HashMap;

import projetS3Voyageur.*;

public class BrutForceV3 implements ModeRecherche {

    private int overFlow;

    private Pays pays;

    private int villeInitial;
    private int nombreDeVilles;

    private double distanceOptimum;
    private int[] villesEmprunteesOptimum;

    private static HashMap<Integer, Integer> ConversionFormatBinaireEnNumVille = new HashMap<>();

    /**
     * Recherche depuis une ville de départ le parcours le plus optimisé pour
     * visiter toutes les villes d'un pays.
     */
    @Override
    public void recherche(Pays pays, int villeInitial) {
        this.pays = pays;
        this.villeInitial = villeInitial;
        nombreDeVilles = pays.getNombreDeVilles();
        overFlow = 1 << nombreDeVilles;
        distanceOptimum = Double.MAX_VALUE;

        for (Integer i = 0; i != nombreDeVilles; i++) {
            ConversionFormatBinaireEnNumVille.put(1 << i, i);
        }

        rechercheAux(1 << villeInitial, villeInitial, 0.0, emprunteVille(new int[nombreDeVilles + 1], 0, villeInitial));
    }

    /**
     * Recherche récursivement le parcours le plus court possible.
     * 
     * @param villesAVisiter    villes qui reste à visité
     * @param villeActuelle     ville dans la quelle se situe l'algo
     * @param distanceParcourue distance parcourue depuis la première itération
     * @param nbVillesAVisiter  Variable de fin de la récursiv
     * @param villesEmprunté    Stock par ordre chronologique les numéros des villes
     *                          emprunté
     */
    private void rechercheAux(int villesVisitees, int villeActuelle, double distanceParcourue, int[] villesEmprunté) {

        // Je prend en compte que la VilleActuell est déjà une ville visité

        if ((villesVisitees + 1) == overFlow) {
            double distanceParcourueFinal = distanceParcourue
                    + pays.getDistanceEntreVilles(villeActuelle, villeInitial);

            if (distanceParcourueFinal < distanceOptimum) {
                distanceOptimum = distanceParcourueFinal;
                villesEmprunteesOptimum = emprunteVille(villesEmprunté, nombreDeVilles, villeInitial);
            }
        } else {

            for (int villeFomatBinaire = villeNonVisitee(1,
                    villesVisitees); villeFomatBinaire < overFlow; villeFomatBinaire = villeNonVisitee(
                            villeFomatBinaire << 1, villesVisitees)) {

                int villeChoisie = (ConversionFormatBinaireEnNumVille.get(villeFomatBinaire));

                rechercheAux(villesVisitees + villeFomatBinaire, (villeChoisie),
                        distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeChoisie),
                        emprunteVille(villesEmprunté, Integer.bitCount(villesVisitees), villeChoisie));

            }

        }

    }

    // #region Outils

    /**
     * Renvois un type int où chaque bit représente une ville, si un bit 0 elle
     * n'est pas visitée, si un bit vaut 1 elle a été visitée. La méthode récupère
     * les villes visitées et la ville actuelle si la ville actuelle fait partie des
     * villes déjà visitée elle fait passer la ville actuelle à une ville non
     * visitée.
     * 
     * @param villeActuelle  Chaque bit du int représente une ville seul l'un des
     *                       bits est à 1, elle représente la ville actuelle
     * 
     * @param villesVisitees Chaque bit à 1 du int représente les villes visitées.
     * 
     * @return {@code int} Renvois un int avec un seul bit à 1, son emplacement
     *         (dans la séquence de bits du int) représente une ville non visitée
     *         qui est la nouvelle ville actuelle.
     */
    private int villeNonVisitee(int villeActuelle, int villesVisitees) {
        villeActuelle += villesVisitees;
        return villeActuelle - (villeActuelle & villesVisitees);
    }

    /**
     * Stock par ordre chronologique les villes visitées.
     * 
     * @param villesEmpruntees Tableau de int où chaque case représente le numéro
     *                         d'une ville visité
     * @param index            index à la quelle le numéro de la ville visité doit
     *                         être ajouté
     * @param villeVisitee     Numéro de la ville visitée
     * @return
     */
    private int[] emprunteVille(int[] villesEmpruntees, int index, int villeSuivante) {
        villesEmpruntees[index] = villeSuivante;
        return villesEmpruntees.clone();
    }
    // #endregion Outils

    // #region Getters

    /**
     * Renvois le nom de l'algorithme de recherche
     * 
     * @return {@code String}
     */
    @Override
    public String getNom() {
        return "BrutForce v3";
    }

    /**
     * Dois être exécuté après la recherche() Retourne le parcours le plus optimisé
     * 
     * @return {@code Parcours}
     */
    public Parcours getParcours() {
        // TODO: Ajouter l'exception avec un getParcours sans avoir fait de recherche
        String villesEmpruntees = String.valueOf(villesEmprunteesOptimum[0]);
        for (int i = 1; i < villesEmprunteesOptimum.length; i++) {
            villesEmpruntees += '>' + String.valueOf(villesEmprunteesOptimum[i]);
        }

        return new Parcours(distanceOptimum, villesEmpruntees);
    }

    // #endregion Getters

}