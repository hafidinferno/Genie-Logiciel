package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;

/**
 * Classe utilitaire dérivée de la classe TypeRecherche qui permet
 * d'obtenir le nom de cette dernière dans la vue.
 */
public class RegexSearch extends TypeRecherche {

    /**
     * Constructeur de la classe.
     */
    public RegexSearch() {
        super();
    }

    @Override
    public String getTextForStrategy(final String text) {
        return ".*" + text + ".*";
    }

    @Override
    public ArrayList<DataMessage> search(final String text) {
        String newText = getTextForStrategy(text);
        return super.search(newText);
    }
}
