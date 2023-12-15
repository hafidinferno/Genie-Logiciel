package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;

/**
 * Classe utilitaire dérivée de la classe TypeRecherche qui permet
 * d'obtenir le nom de cette dernière dans la vue.
 */
public class SubString extends TypeRecherche {

    /**
     * Constructeur de la classe.
     */
    public SubString() {
        super();
    }

    @Override
    public String getTextForStrategy(final String text) {
        return text;
    }

    @Override
    public ArrayList<DataMessage> search(final String text) {
        ArrayList<DataMessage> found = new ArrayList<>();
        Dao dao = getDao();
        ArrayList<DataMessage> messages = dao.getAllMessage();
        for (DataMessage tuple : messages) {
            String message = tuple.getText();
            if (message.contains(text)) {
                found.add(tuple);
            }
        }
        return found;
    }
}
