package fr.univ_lyon1.info.m1.elizagpt.controleur;

import fr.univ_lyon1.info.m1.elizagpt.model.HashAndMessage;
import javafx.scene.control.Label;
import fr.univ_lyon1.info.m1.elizagpt.model.Ia;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import java.util.ArrayList;

/**
 * Controlleur des messages.
 */
public class HandleMessage {
	private final Dao dao;
    private final Ia ia;
    private final int userId;

    /**
     * Constructeur de la classe.
     */
	public HandleMessage() {
		ia = Ia.getInstance();
        dao = Dao.getInstance();
        userId = 1;
	}

	/**
     * Fonction de réponse qui sera utilisée par l'utilissateur
     * et l'IA.
     * @param text message.
     * @param id L'ID est celui de l'expéditeur.
     */
	public void reply(final String text, final int id) {

        dao.addMessage(text, id);
	// TODO: a click on this hbox should delete the message.

	}

    /**
     * Fonction de réponse de l'IA.
     * @param text message envoyé par l'utilisateur.
     */
    public void iaRespond(final String text) {
        String iaResponse = ia.process(text, dao.getName());
        reply(iaResponse, ia.getId());
    }

    /**
     * Fonction de recherche via regex dans la database.
	 * @param text chaine de caractère que l'on cherche
	 * @param searchTextLabel label de notre barre de recherche.
	 * il nous permet de notifier l'utilisateur si la recherche est en
	 * cours ou non et l'execution.
     * @return Une liste de messages qui match avec notre recherche.
     */
    public ArrayList<HashAndMessage> searchText(final String text, final Label searchTextLabel) {
        return dao.search(text, searchTextLabel);
    }
}
