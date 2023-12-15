package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;

/**
 * Interface permettant de créer différentes stratégies
 * de recherche.
 */
public interface SearchFunction {

    /**
     * Fonction de recherche à définir dans les classes
     * qui implémenteront cette dernière.
     * @param text Chaîne de caractères utilisée lors de la recherche.
     * @return Ensembles des messages qui satisfont la recherche.
     */
    ArrayList<DataMessage> search(String text);

}
