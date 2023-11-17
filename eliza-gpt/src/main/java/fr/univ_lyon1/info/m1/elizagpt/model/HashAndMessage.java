package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Date;


public class HashAndMessage {
    private final String message;
    private final Integer hash;

    public HashAndMessage(String text, Date date) {
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
