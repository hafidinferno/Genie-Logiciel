package fr.univ_lyon1.info.m1.elizagpt.model;

import javafx.scene.control.Label;

public class HandleMessage {
	private final Dao m_dao;
    private final Ia m_ia;
    private final int m_userId;

	public HandleMessage() {
		m_ia = Ia.getInstance();
        m_dao = Dao.getInstance();
        m_userId = 1;
	}

	
	public void reply(final String text, final int id) {
        m_dao.addMessage(text, id);
	// TODO: a click on this hbox should delete the message.
	}

    public void iaRespond(final String text) {
        String iaResponse = m_ia.process(text, m_dao.getName());
        reply(iaResponse, m_ia.getId());
    }

    public void SearchText(final String text, final Label searchTextLabel) {
        m_dao.search(text, searchTextLabel);

    }
}
