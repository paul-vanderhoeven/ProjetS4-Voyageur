package projetS3Voyageur.ModesDeRecherches;

import projetS3Voyageur.CompositionPays.Pays;

public class TrackProchesV2 implements ModeRecherche {

    private int toutesVillesVisitees;

    private Pays pays;
    private double[] distancesPlusCourt;

    private byte villeInitiale;
    private byte nombreDeVilles;

    private double distanceOptimum;
    private byte[] villesEmprunteesOptimum;

    /**
     * Recherche depuis une ville de départ le parcours pour visiter toutes les
     * villes d'un pays, en allant à la ville la plus proche non visitée.
     * 
     * @param pays          Le pays concerné par la recherche
     * @param villeInitiale Le numéro de la ville de départ
     */
    @Override
    public void recherche(final Pays pays, final int villeInitiale) {
        this.pays = pays;
        this.villeInitiale = (byte) villeInitiale;
        nombreDeVilles = (byte) pays.getNombreDeVilles();
        toutesVillesVisitees = (1 << nombreDeVilles) - 1;
        distanceOptimum = Double.MAX_VALUE;

        distancesPlusCourt = distancesPlusCourts();

        rechercheAuxDistanceProche(1 << this.villeInitiale, this.villeInitiale, 0.0);
        rechercheAuxDistance(1 << this.villeInitiale, this.villeInitiale, 0.0);
        rechercheAuxVillesEmpruntees(1 << this.villeInitiale, this.villeInitiale, 0.0,
                emprunteVille(new byte[nombreDeVilles + 1], 0, this.villeInitiale));
    }

    // #region Badtrack v2 + Optimisation

    /**
     * Recherche récursivement le parcours le plus court possible. En vérifiant que
     * la distance parcourue ne soit pas plus longue que la distance la plus courte
     * enregistré.
     * 
     * @param villesVisitees    Villes qui reste à visitée.
     * @param villeActuelle     Ville où se situe l'algorithme
     * @param distanceParcourue Distance parcourure depuis la première itération
     */
    private void rechercheAuxDistance(final int villesVisitees, final byte villeActuelle,
            final double distanceParcourue) {

        // Je prend en compte que VilleActuelle est déjà une ville visitée

        if (distanceParcourue < distanceOptimum
                && ((distanceParcourue + parcoursIdeal(villesVisitees)) <= distanceOptimum)) {
            if (Integer.bitCount(villesVisitees) > (nombreDeVilles / 2))
                rechercheAuxDistanceProche(villesVisitees, villeActuelle, distanceParcourue);
            if (villesVisitees == toutesVillesVisitees) {
                final double distanceParcourueFinal = distanceParcourue
                        + pays.getDistanceEntreVilles(villeActuelle, villeInitiale);

                if (distanceParcourueFinal < distanceOptimum) {
                    distanceOptimum = distanceParcourueFinal;
                }
            } else {

                byte villeChoisie;

                for (int villeFormatBinaire = villeNonVisitee(1,
                        villesVisitees); villeFormatBinaire < toutesVillesVisitees; villeFormatBinaire = villeNonVisitee(
                                villeFormatBinaire << 1, villesVisitees)) {

                    villeChoisie = (byte) (Math.getExponent(villeFormatBinaire));

                    rechercheAuxDistance(villesVisitees + villeFormatBinaire, (villeChoisie),
                            distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeChoisie));

                }

            }
        }

    }
    // #endregion BadTrack v2 + Optimisation

    // #region PlusProche v2

    /**
     * Recherche la ville la plus proche parmis les villes non visitées pour y
     * aller.
     * 
     * @param villesVisitees    Villes qui ont été visitées jusqu'à présent.
     * @param villeActuelle     Ville où se situe l'algorithme.
     * @param distanceParcourue Distance parcourue depuis la première itération
     */
    private void rechercheAuxDistanceProche(final int villesVisitees, final byte villeActuelle,
            final double distanceParcourue) {

        // Je prend en compte que la VilleActuell est déjà une ville visité
        // TODO : Mettre un backTrack ?
        if (villesVisitees == toutesVillesVisitees) {
            distanceOptimum = Double.min(distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeInitiale),
                    distanceOptimum);
        } else {

            double distanceMin = Long.MAX_VALUE;
            byte villePlusProche = 0;
            byte villeChoisie;
            double distanceVilleChoisie;

            for (int villeFormatBinaire = villeNonVisitee(1,
                    villesVisitees); villeFormatBinaire < toutesVillesVisitees; villeFormatBinaire = villeNonVisitee(
                            villeFormatBinaire << 1, villesVisitees)) {

                villeChoisie = (byte) (Math.getExponent(villeFormatBinaire));
                distanceVilleChoisie = pays.getDistanceEntreVilles(villeActuelle, villeChoisie);

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
     * Recherche récursivement le parcours le plus court possible. En vérifiant que
     * la distance parcourue n'est pas plus longue que la distance la distance
     * optimum enregistrée via rechercheDistanceAux().
     * 
     * @param villesVisitees    Villes qui ont été visitées depuis la première
     *                          itération
     * 
     * @param villeActuelle     Ville dans laquelle se situe l'algo
     * 
     * @param distanceParcourue Distance parcourue depuis la première itération
     * 
     * @param villesEmpruntees  Stock par ordre chronologique les numéros des villes
     *                          empruntées
     */
    private void rechercheAuxVillesEmpruntees(final int villesVisitees, final byte villeActuelle,
            final double distanceParcourue, final byte[] villesEmpruntees) {

        // Je prend en compte que la VilleActuell est déjà une ville visité
        if (distanceParcourue < distanceOptimum
                && ((distanceParcourue + parcoursIdeal(villesVisitees)) <= distanceOptimum)) {

            if (villesVisitees == toutesVillesVisitees) {

                final double distanceParcourueFinal = distanceParcourue
                        + pays.getDistanceEntreVilles(villeActuelle, villeInitiale);

                if (distanceParcourueFinal == distanceOptimum) {
                    distanceOptimum = distanceParcourueFinal;
                    villesEmprunteesOptimum = emprunteVille(villesEmpruntees, nombreDeVilles, villeInitiale);
                }
            } else {

                byte villeChoisie;

                for (int villeFormatBinaire = villeNonVisitee(1,
                        villesVisitees); villeFormatBinaire < toutesVillesVisitees; villeFormatBinaire = villeNonVisitee(
                                villeFormatBinaire << 1, villesVisitees)) {

                    villeChoisie = (byte) Math.getExponent(villeFormatBinaire);

                    rechercheAuxVillesEmpruntees(villesVisitees + villeFormatBinaire, (villeChoisie),
                            distanceParcourue + pays.getDistanceEntreVilles(villeActuelle, villeChoisie),
                            emprunteVille(villesEmpruntees, Integer.bitCount(villesVisitees), villeChoisie));

                }

            }
        }

    }

    // #endregion Récupére parcours

    // #region Outils utile

    /**
     * /** Renvois un type int où chaque bit représente une ville, si un bit 0 elle
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
    private int villeNonVisitee(int villeActuelle, final int villesVisitees) {
        villeActuelle += villesVisitees;
        return villeActuelle ^ (villeActuelle & villesVisitees);
    }

    /**
     * Stock par ordre chronologique la nouvelle ville visitée à la liste des villes
     * visitées.
     * 
     * @param villesEmpruntees  Tableau de int où chaque case représente le numéro
     *                          d'une ville visité
     * 
     * @param indexVilleVisitee index auquel le numéro de la ville visité doit être
     *                          ajouté
     * 
     * @param villeVisitee      Numéro de la ville visitée
     * 
     * @return {@code int[]} Une liste de int contenant par ordre chronologique les
     *         villes empruntées
     */
    private byte[] emprunteVille(byte[] villesEmpruntees, final int indexVilleVisitee, final byte villeSuivante) {
        villesEmpruntees[indexVilleVisitee] = villeSuivante;
        return villesEmpruntees.clone();
    }

    // #region parcours idéal

    /**
     * Renvois le parcours le plus optimiste possible (en empruntant les chemins les
     * plus courts). Exemple : il reste 3 villes à visiter alors il prend les trois
     * meilleurs chemins sans même voir où est-ce qu'ils mènent
     * 
     * @param villesVisitees type int qui contient sous forme de bit les villes qui
     *                       ont été visitées
     * 
     * @return {@code double} retourne la distance parcourue en empruntant les
     *         chemins les plus courts sans voir où ils mènent
     */
    private double parcoursIdeal(final int villesVisitees) {
        return distancesPlusCourt[(nombreDeVilles - Integer.bitCount(villesVisitees))];
    }

    /**
     * Retourne une distance minimum de toutes les distances possibles au sein d'un
     * pays, mais supérieur au nombre rentré en paramètre (représente la distance
     * minimum précédente).
     * 
     * @param distanceMinPrecedente Représante le nombre au quelle la distance
     *                              minimum doit être supérieur
     * 
     * @return {@code double} distance minimum de toutes les distances séparant les
     *         villes d'un pays, mais supérieur à la distanceMinPrecedente
     */
    private double distancePlusCourtEntre2Villes(final double minimumSuperieur, final int index) {
        // TODO: clarifier les nom de variable
        int villesVisitees = 0;
        double resultat = Double.MAX_VALUE;
        double valeurMinimal;

        for (int villeFormatBinaire = villeNonVisitee(1,
                villesVisitees); villeFormatBinaire < toutesVillesVisitees; villeFormatBinaire = villeNonVisitee(
                        villeFormatBinaire << 1, villesVisitees)) {

            for (int villeFormatBinaire2 = villeNonVisitee(1,
                    villeFormatBinaire); villeFormatBinaire2 < toutesVillesVisitees; villeFormatBinaire2 = villeNonVisitee(
                            villeFormatBinaire2 << 1, villeFormatBinaire)) {

                valeurMinimal = pays.getDistanceEntreVilles(Math.getExponent(villeFormatBinaire),
                        Math.getExponent(villeFormatBinaire2));

                if ((valeurMinimal < resultat) && (minimumSuperieur <= valeurMinimal)
                        && (pairDeVillesNonVisite(villeFormatBinaire | villeFormatBinaire2))) {
                    resultat = valeurMinimal;
                    pairDeVilleVisitee[index] = villeFormatBinaire + villeFormatBinaire2;

                }
            }

        }

        return resultat;
    }

    private int[] pairDeVilleVisitee;

    // TODO: à voir s'il est possible de faire un meilleur commentaire explicatif

    /**
     * Retourne une distance minimum de toutes les distances possibles au sein d'un
     * pays, mais supérieur au nombre rentré en paramètre (représente la distance
     * minimum précédente).
     * 
     * @param distanceMinPrecedente Représante le nombre au quelle la distance
     *                              minimum doit être supérieur
     * 
     * @return {@code double} distance minimum de toutes les distances séparant les
     *         villes d'un pays, mais supérieur à la distanceMinPrecedente
     */
    private double[] distancesPlusCourts() {
        pairDeVilleVisitee = new int[nombreDeVilles + 1];
        int i = 0;
        double[] resultat = new double[nombreDeVilles];
        for (double minValue = distancePlusCourtEntre2Villes(0,
                0); i < resultat.length; minValue = distancePlusCourtEntre2Villes(minValue, i)) {
            resultat[i++] = minValue + resultat[(i == 1) ? 0 : i - 2];
        }

        return resultat;
    }

    // TODO: trouvé une nouvelle version moins encombrante

    /**
     * Vérifie si la paire de ville (entré sous forme d'un int possédant deux bits à
     * 1 les bits à un représente les villes visitées) n'est pas déjà comprise dans
     * le tableau de pairDeVilleVisitee
     * 
     * @param pairDeVilleActuelle Type int dans le quel deux bits sont à 1 ils
     *                            représantent les villes visitées
     * 
     * @return {@code boolean} vrai si la pairDeVilleActuelle rentré en paramètre
     *         n'est pas visité sinon faux
     */
    private boolean pairDeVillesNonVisite(final int pairDeVilleActuelle) {
        boolean resultat = true;
        for (final int pairVille : pairDeVilleVisitee) {
            resultat = ((pairDeVilleActuelle ^ pairVille) != 0) && resultat;
        }
        return resultat;
    }

    // #endregion parcours idéal

    // #endregion Outils Utile

    // #region Getters

    /**
     * @près-requis : Cette méthode doit être exécuté après la méthode recherche().
     * 
     * @return {@code Parcours} parcours le plus optimisé qu'il ai trouvé
     */
    @Override
    public Parcours getParcours() {
        // TODO: Ajouter l'exception avec un getParcours sans avoir fait de recherche
        String villesEmpruntees = String.valueOf(villesEmprunteesOptimum[0]);
        for (int i = 1; i < villesEmprunteesOptimum.length; i++) {
            villesEmpruntees += '>' + String.valueOf(villesEmprunteesOptimum[i]);
        }

        return new Parcours(distanceOptimum, villesEmpruntees);
    }

    /**
     * Renvois le nom de l'algorithme de recherche
     * 
     * @return {@code String} Nom de l'algorithme
     */
    @Override
    public String getNom() {
        return "TrackProches v2";
    }

    // #endregion Getters

}