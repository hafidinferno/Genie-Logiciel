package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * Classe utilitaire. C'est la structure qui est stockée
 * dans notre classe Dao, c'est à dire notre base de données.
 */
public class MessageId {
	private final String message;
	private final int id;

	/**
	 * Constructeur de la classe.
	 * @param text message écrit par l'expéditeur
	 * @param i Id de l'expéditeur
	 */
	public MessageId(final String text, final int i) {
		message = text;
		id = i;
	} 

	/**
	 * 
	 * @return Le message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @return Id de notre message
	 */
	public final int getId() {
		return id;
	}
}
