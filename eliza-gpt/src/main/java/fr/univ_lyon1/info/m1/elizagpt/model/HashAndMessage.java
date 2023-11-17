package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Date;

/**
 * Classe de qui permet de fabriquer la donnée
 * [message, hash].
 */
public class HashAndMessage {
    private final String message;
    private final Integer hash;

    /**
     * constructeur de notre classe. Un hash de la donnée
     * est calculé lors de l'initialisation.
     * @param text texte de notre message
     * @param date date de création du message
     */
    public HashAndMessage(final String text, final Date date) {
        message = text;
        hash = date.hashCode();
    }

    public final String getText() {
        return message;
    }

    public final Integer getHash() {
        return hash;
    }
}
