package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;

/**
 * Database de notre programme. Elle regroupe toutes les
 * informations relatives à nos message.
 */
public final class Dao {
	private static Dao instance;
	private ArrayList<MessageId> messages;

	/**
	 * Constructeur de la classe.
	 */
	public Dao() {
		messages = new ArrayList<MessageId>();
	}

	/**
	 * Fonction permettant de récupérer l'instance de la classe si
	 * elle existe. Si elle n'existe pas, elle sera dans un premier
	 * temps créée puis renvoyée.
	 * @return instance de la classe.
	 */
	public static Dao getInstance() {
		if (instance == null) {
			instance = new Dao();
		} 
		return instance;
	}

	/**
	 * Ajoute à notre database un message ainsi que l'ID de
	 * l'expéditeur.
	 * @param message
	 * @param id
	 */
	public void addMessage(final String message, final int id) {
		messages.add(new MessageId(message, id));
	}

	/**
	 * Fonction permettant de chercher le nom de l'utilisateur.
	 * @return La fonction retourne une le nom de l'utilisateur s'il existe
	 * sinon null.
	 */
	public String getName() {
        for (MessageId message : messages) {
			if (message.getId() == 1) {
				String text = message.getMessage();
				Pattern pattern = Pattern.compile("Je m'appelle (.*)\\.",
									Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(text);
				if (matcher.matches()) {
					return matcher.group(1);
				}
            }
        }
        return null;
    }

	/**
	 * Recherche dans la database les messages correspondant au text qui lui
	 * a été donné. cette recherche fonctionne à l'aide de regex.
	 * @param text chaine de caractère que l'on cherche
	 * @param searchTextLabel label de notre barre de recherche.
	 * il nous permet de notifier l'utilisateur si la recherche est en
	 * cours ou non.
	 */
	public void search(final String text, final Label searchTextLabel) {
        Pattern pattern;
        Matcher matcher;
        if (text == null || text.isEmpty()) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + text);
        }
        pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
        ArrayList<MessageId> toDelete = new ArrayList<MessageId>();
        for (MessageId tuple : messages) {
			String message = tuple.getMessage();
			matcher = pattern.matcher(message);
			if (!matcher.find()) {
				// Can delete it right now, we're iterating over the list.
				toDelete.add(tuple);
			}
        }
        messages.removeAll(toDelete);
        //text.setText("");
    }
}
