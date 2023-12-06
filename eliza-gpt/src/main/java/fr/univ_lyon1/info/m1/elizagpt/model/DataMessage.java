package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Date;

/**
 * Classe utilitaire. C'est la structure qui est stockée
 * dans notre classe Dao, c'est à dire notre base de données.
 */
public class DataMessage {

	private final boolean ia;
	private final String texte;
	private final Integer hash;



	/**
	 * Constructeur de la classe.
	 * @param text message écrit par l'expéditeur
	 * @param isIa permet de savoir si le message est écrit par l'utilisateur ou l'IA.
	 */
	public DataMessage(final String text, final boolean isIa) {
		ia = isIa;
		Date date = new Date();
		hash = date.hashCode();
		texte = text;

	} 


	/**
	 * @return Id de notre message
	 */
	public final boolean isIa() {
		return ia;
	}

	public final String getText() {
		return texte;
	}

	public final Integer getHash() {
		return hash;
	}

}
