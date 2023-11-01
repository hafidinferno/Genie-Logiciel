package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;


public final class Dao {
	private static Dao m_instance;
	private ArrayList<MessageId> m_messages;

	public Dao() {
		m_messages = new ArrayList<MessageId>();
	}

	public static Dao getInstance() {
		if(m_instance == null) {
			m_instance = new Dao();
		} 
		return m_instance;
	}

	public void addMessage(final String message, final int id) {
		m_messages.add(new MessageId(message, id));
	}

	public final String getName() {
        for (MessageId message : m_messages) {
			if (message.getId() == 1) {
				String text = message.getMessage();
				Pattern pattern = Pattern.compile("Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(text);
				if (matcher.matches()) {
					return matcher.group(1);
				}
            }
        }
        return null;
    }

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
        for (MessageId tuple : m_messages) {
			String message = tuple.getMessage();
			matcher = pattern.matcher(message);
			if (!matcher.find()) {
				// Can delete it right now, we're iterating over the list.
				toDelete.add(tuple);
			}
        }
        m_messages.removeAll(toDelete);
        //text.setText("");
    }
}
