package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * @param isIa permet de savoir si le message est envoyé par l'IA ou l'utilisateur
	 */
	public void addMessage(final String message, final boolean isIa) {
		messages.add(new DataMessage(message, isIa));
	}

	/**
	 * Fonction permettant de chercher le nom de l'utilisateur.
	 * @return La fonction retourne une le nom de l'utilisateur s'il existe
	 * sinon null.
	 */
	public String getName() {
        for (DataMessage message : messages) {
			if (!message.isIa()) {
				String text = message.getText();
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
	 * @return Une liste de messages qui match avec notre recherche.
	 */
	public ArrayList<DataMessage> search(final String text) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(".*" + text + ".*", Pattern.CASE_INSENSITIVE);
        ArrayList<DataMessage> found = new ArrayList<>();
        for (DataMessage tuple : messages) {
			String message = tuple.getText();
			matcher = pattern.matcher(message);
			if (matcher.find()) {
				// Can delete it right now, we're iterating over the list.
				found.add(tuple);
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
	public ArrayList<DataMessage> getAllMessage() {
	return messages;
}

	/**
	 * La fonction permet de supprimer dans notre
	 * base de données, les données relatives au
	 * hash passé en paramètre.
	 * @param hash Hash de la donnée que l'on souhaite
	 *             supprimer.
	 */
	public void deleteMessage(final Integer hash) {
		for (DataMessage message : messages) {
			if (message.getHash().equals(hash)) {
				messages.remove(message);
				return;
			}
		}
	}

}
