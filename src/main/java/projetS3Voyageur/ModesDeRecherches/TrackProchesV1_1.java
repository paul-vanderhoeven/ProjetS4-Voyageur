package projetS3Voyageur.ModesDeRecherches;

import projetS3Voyageur.*;

public class TrackProchesV1_1 implements ModeRecherche {

    private int overFlow;

    private Pays pays;
    private double[] distancesPlusCourt;

    private byte villeInitial;
    private byte nombreDeVilles;

    private double distanceOptimum;
    private byte[] villesEmprunteesOptimum;

    /**
     * Recherche depuis une ville de départ le parcours pour visiter toutes les
     * villes d'un pays, en allant à la ville la plus proche non visitée.
     */
    @Override
    public void recherche(Pays pays, int villeInitialP) {
        this.pays = pays;
        this.villeInitial = (byte) villeInitialP;
        nombreDeVilles = (byte) pays.getNombreDeVilles();
        overFlow = (1 << nombreDeVilles) - 1;
        distanceOptimum = Double.MAX_VALUE;

        distancesPlusCourt = distancesPlusCourts();

        rechercheAuxDistanceProche(1 << villeInitial, villeInitial, 0.0);
        rechercheAuxDistance(1 << villeInitial, villeInitial, 0.0);
        rechercheAuxVillesEmpruntees(1 << villeInitial, villeInitial, 0.0,
                emprunteVille(new byte[nombreDeVilles + 1], 0, villeInitial));
    }

    // #region Badtrack v2

    /**
     * Recherche récursivement le parcours le plus court possible. En vérifiant que
     * la distance parcourue ne soit pas plus longue que la distance la plus courte
     * enregistré.
     * 
     * @param villesVisitees      Villes qui reste à visitée.
     * @param villeActuelle       Ville où se situe l'algorithme
     * @param distanceParcourue Distance parcourure depuis la première itération
     */
    private void rechercheAuxDistance(int villesVisitees, byte villeActuelle, double distanceParcourue) {

        // Je prend en compte que la VilleActuell est déjà une ville visité

        if (distanceParcourue < distanceOptimum && ((distanceParcourue
                + distancesPlusCourt[(nombreDeVilles - Integer.bitCount(villesVisitees))]) < distanceOptimum)) {
            if (Integer.bitCount(villesVisitees) > (nombreDeVilles / 2))
                rechercheAuxDistanceProche(villesVisitees, villeActuelle, distanceParcourue);
            if (villesVisitees == overFlow) {
                double distanceParcourueFinal = distanceParcourue
                        + pays.getDistanceEntreVilles(villeActuelle, villeInitial);

                if (distanceParcourueFinal < distanceOptimum) {
                    distanceOptimum = distanceParcourueFinal;
                }
            } else {

                for (int villeFomatBinaire = villeNonVisitee(1,
                        villesVisitees); villeFomatBinaire < overFlow; villeFomatBinaire = villeNonVisitee(
                                villeFomatBinaire << 1, villesVisitees)) {

                    byte villeChoisie = (byte) (Math.getExponent(villeFomatBinaire));

                    rechercheAuxDistance(villesVisitees + villeFomatBinaire, (villeChoisie),
                            distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeChoisie));

                }

            }
        }

    }
    // #endregion BadTrack v2

    // #region PlusProche v2

    /**
     * Recherche la ville la plus proche parmis les villes non visitées pour y
     * aller.
     * 
     * @param villesVisitees      Villes qui ont été visitées jusqu'à présent.
     * @param villeActuelle       Ville où se situe l'algorithme.
     * @param distanceParcourue Distance parcourue depuis la première itération
     */
    private void rechercheAuxDistanceProche(int villesVisitees, byte villeActuelle, double distanceParcourue) {

        // Je prend en compte que la VilleActuell est déjà une ville visité

        if (villesVisitees == overFlow) {
            if (distanceOptimum > distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeInitial))
                distanceOptimum = distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeInitial);
        } else {
            double distanceMin = Long.MAX_VALUE;
            byte villePlusProche = 0;
            for (int villeFomatBinaire = villeNonVisitee(1,
                    villesVisitees); villeFomatBinaire < overFlow; villeFomatBinaire = villeNonVisitee(
                            villeFomatBinaire << 1, villesVisitees)) {

                byte villeChoisie = (byte) (Math.getExponent(villeFomatBinaire));

                double distanceVilleChoisie = pays.getDistanceEntreVilles(villeActuelle, villeChoisie);
                if (distanceVilleChoisie < distanceMin) {
                    distanceMin = distanceVilleChoisie;
                    villePlusProche = villeChoisie;
                }

            }
            rechercheAuxDistanceProche((villesVisitees + (1 << villePlusProche)), (villePlusProche),
                    distanceParcourue + distanceMin);

        }

    }

    // #endregion PlusProche v2

    // #region Récupére parcours effectué

    /**
     * Recherche récusivement le parcours le plus court possible. En vérifiant que
     * la distance parcourue ne soit pas plus longue que la distance la distance
     * optimum enregistré avec rchercheDistanceAux().
     * 
     * @param villesVisitees      Villes qui ont étais visité jusqu'à présent
     * @param villeActuelle       Ville dans laquelle se situe l'algo
     * @param distanceParcourue Distance parcourue depuis la première itération
     * @param villesEmprunté    Stock par ordre chronologique les numéros des villes
     *                          emprunté
     */
    private void rechercheAuxVillesEmpruntees(int villesVisitees, byte villeActuelle, double distanceParcourue,
            byte[] villesEmprunté) {

        // Je prend en compte que la VilleActuell est déjà une ville visité
        if (distanceParcourue < distanceOptimum) {

            if (villesVisitees == overFlow) {
                double distanceParcourueFinal = distanceParcourue
                        + pays.getDistanceEntreVilles(villeActuelle, villeInitial);

                if (distanceParcourueFinal == distanceOptimum) {
                    distanceOptimum = distanceParcourueFinal;
                    villesEmprunteesOptimum = emprunteVille(villesEmprunté, nombreDeVilles, villeInitial);
                }
            } else {

                for (int villeFomatBinaire = villeNonVisitee(1,
                        villesVisitees); villeFomatBinaire < overFlow; villeFomatBinaire = villeNonVisitee(
                                villeFomatBinaire << 1, villesVisitees)) {

                    byte villeChoisie = (byte) Math.getExponent(villeFomatBinaire);

                    rechercheAuxVillesEmpruntees(villesVisitees + villeFomatBinaire, (villeChoisie),
                            distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeChoisie),
                            emprunteVille(villesEmprunté, Integer.bitCount(villesVisitees), villeChoisie));

                }

            }
        }

    }

    // #endregion Récupére parcours

    // #region Outils utile

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
        return villeActuelle ^ (villeActuelle & villesVisitees);
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
    private byte[] emprunteVille(byte[] villesEmpruntees, int index, byte villeSuivante) {
        villesEmpruntees[index] = villeSuivante;
        return villesEmpruntees.clone();
    }

    private double distancePlusCourtEntre2Villes(double minimumSuperieur) {
        int villesVisitees = 0;
        double resultat = Double.MAX_VALUE;
        for (int villeFormatBinaire = villeNonVisitee(1,
                villesVisitees); villeFormatBinaire < overFlow; villeFormatBinaire = villeNonVisitee(
                        villeFormatBinaire << 1, villesVisitees)) {
            for (int villeFormatBinaire2 = villeNonVisitee(1,
                    villeFormatBinaire); villeFormatBinaire2 < overFlow; villeFormatBinaire2 = villeNonVisitee(
                            villeFormatBinaire2 << 1, villeFormatBinaire)) {
                resultat = (minimumSuperieur < Double.min(resultat,
                        pays.getDistanceEntreVilles(Math.getExponent(villeFormatBinaire),
                                Math.getExponent(villeFormatBinaire2))))
                                        ? Double.min(resultat,
                                                pays.getDistanceEntreVilles(Math.getExponent(villeFormatBinaire),
                                                        Math.getExponent(villeFormatBinaire2)))
                                        : resultat;
            }

        }
        return resultat;
    }

    private double[] distancesPlusCourts() {
        int i = 0;
        double[] resultat = new double[nombreDeVilles];
        for (double minValue = distancePlusCourtEntre2Villes(0); resultat[nombreDeVilles
                - 1] == 0.; minValue = distancePlusCourtEntre2Villes(minValue)) {
            resultat[i++] = minValue + resultat[(i == 1) ? 0 : i - 2];
        }

        return resultat;
    }

    // #endregion Outils Utile

    // #region Getters

    /**
     * Dois être éxecuté après la recherche() Retourne le parcous le plus optimisé
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

    /**
     * Renvoi le nom de l'algorithme de recherche
     * 
     * @return {@code String}
     */
    @Override
    public String getNom() {
        return "TrackProches v1.1";
    }

    // #endregion Getters

}