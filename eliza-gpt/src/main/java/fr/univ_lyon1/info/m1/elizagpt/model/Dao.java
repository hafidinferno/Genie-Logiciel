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
	private final ArrayList<DataMessage> messages;

	/**
	 * Constructeur de la classe.
	 */
	public Dao() {
		messages = new ArrayList<>();
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
	 * @param message texte de notre message
	 * @param id Id de l'entité ayant écrit le message
	 */
	public void addMessage(final String message, final int id) {
		messages.add(new DataMessage(message, id));
	}

	/**
	 * Fonction permettant de chercher le nom de l'utilisateur.
	 * @return La fonction retourne une le nom de l'utilisateur s'il existe
	 * sinon null.
	 */
	public String getName() {
        for (DataMessage message : messages) {
			if (message.getId() == 1) {
				String text = message.getMessage().getText();
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
	 * @return Une liste de messages qui match avec notre recherche.
	 */
	public ArrayList<HashAndMessage> search(final String text, final Label searchTextLabel) {
        Pattern pattern;
        Matcher matcher;
        if (text == null || text.isEmpty()) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + text);
        }
        pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
        ArrayList<HashAndMessage> found = new ArrayList<>();
        for (DataMessage tuple : messages) {
			HashAndMessage message = tuple.getMessage();
			matcher = pattern.matcher(message.getText());
			if (!matcher.find()) {
				// Can delete it right now, we're iterating over the list.
				found.add(tuple.getMessage());
			}
        }
        return found;
        //text.setText("");
    }

	/**
	 * Fonction permettant d'obtenir une liste de tuples
	 * [message, hash].
	 * @return une Arrayliste de couple [message,hash]
	 */
	public ArrayList<HashAndMessage> getAllMessage() {
	ArrayList<HashAndMessage> res = new ArrayList<>();
	for (DataMessage message : messages) {
		res.add(message.getMessage());
	}
	return res;
}

	/**
	 * La fonction permet de supprimer dans notre
	 * base de données, les données relatives au
	 * hash passé en paramètre.
	 * @param hash Hash de la donnée que l'on souhaite
	 *             supprimer.
	 */
	public void deleteMessage(final Integer hash) {
		DataMessage deletedMessage = null;
		for (DataMessage message : messages) {
			if (message.getMessage().getHash().equals(hash)) {
				messages.remove(message);
				return;
			}
		}
	}

}
