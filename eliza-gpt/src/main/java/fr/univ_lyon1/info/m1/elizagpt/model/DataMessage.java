package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Date;

/**
 * Classe utilitaire. C'est la structure qui est stockée
 * dans notre classe Dao, c'est à dire notre base de données.
 */
public class DataMessage {

	private final int id;
	private final Date date;

	private final HashAndMessage message;


	/**
	 * Constructeur de la classe.
	 * @param text message écrit par l'expéditeur
	 * @param i Id de l'expéditeur
	 */
	public DataMessage(final String text, final int i) {
		id = i;
		date = new Date();
		message = new HashAndMessage(text, date);
	} 

	/**
	 * 
	 * @return Le message
	 */
	public final HashAndMessage getMessage() {
		return message;
	}

	/**
	 * @return Id de notre message
	 */
	public final int getId() {
		return id;
	}

}
