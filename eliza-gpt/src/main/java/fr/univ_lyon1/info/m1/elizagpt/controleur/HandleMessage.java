package fr.univ_lyon1.info.m1.elizagpt.controleur;

import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.Ia;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import java.util.ArrayList;

/**
 * Controleur des messages.
 */
public class HandleMessage {
	private final Dao dao;
    private final Ia ia;

    /**
     * Constructeur de la classe.
     */
	public HandleMessage() {
		ia = Ia.getInstance();
        dao = Dao.getInstance();
        dao.addMessage("Bonjour.", true);
	}

	/**
     * Fonction de réponse qui sera utilisée par l'utilisateur
     * et l'IA.
     * @param text message.
     * @param isIa permet de savoir si le message a été envoyé par l'utilisateur ou l'IA.
     */
	public void reply(final String text, final boolean isIa) {
        dao.addMessage(text, isIa);
	}

    /**
     * Fonction de réponse de l'IA.
     * @param text message envoyé par l'utilisateur.
     */
    public void iaResponse(final String text) {
        String iaResponse = ia.process(text, dao.getName());
        reply(iaResponse, true);
    }

    /**
     * Fonction de recherche via regex dans la database.
     * @param text chaine de caractère que l'on cherche
     * @return Une liste de messages qui match avec notre recherche.
     */
    public ArrayList<DataMessage> search(final String text) {
        return dao.search(text);
    }
}
