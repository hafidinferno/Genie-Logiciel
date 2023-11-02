package fr.univ_lyon1.info.m1.elizagpt.model;

public class MessageId {
	private final String m_message;
	private final int m_id;

	public MessageId(final String message, final int id) {
		m_message = message;
		m_id = id;
	} 

	public final String getMessage() {
		return m_message;
	}
	public final int getId() {
		return m_id;
	}
}
