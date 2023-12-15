package fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy;

import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;

import java.util.ArrayList;

/**
 * Classe permettant de chercher des
 * mots exacts dans nos messages.
 */
public class CompleteWordSearch extends TypeRecherche {
    /**
     * Constructeur de la classe.
     */
    public CompleteWordSearch() {
        super();
    }

    @Override
    public String getTextForStrategy(final String text) {
        return "\\b" + text + "\\b";
    }

    @Override
    public ArrayList<DataMessage> search(final String text) {
        String newText = getTextForStrategy(text);
        return super.search(newText);
    }
}
