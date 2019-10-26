package projetS3Voyageur.ModesDeRecherches;

import projetS3Voyageur.*;

public interface ModeRecherche {

    /**
     * Calcul en interne les chemins le plus court pour visité toutes les villes d'un pays
     * 
     * @param pays        Le pays concerné par la recherche
     * @param villeDepart Le numéro de la ville de départ
     */
    public void recherche(Pays pays, int villeDepart);

    /**
     * Récupére le parcours le plus optimisé après la recherche() de celle-ci
     * 
     * @return {@code Parcours}
     */
    public Parcours getParcour();

    /**
     * Récupère le nom de l'algortihme de recherche
     * 
     * @return {@code String}
     */
    public String getNom();
}