package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public final class Ia {
	private static Ia m_instance;
	private MessageProcessor m_processor;
	private final Pattern [] m_patterns;
	private Matcher m_matcher;
	private Random m_random;
	private final int m_id;

	public enum RespondType {
		PRESENTATION,
		DEMANDE_DE_NOM,
		LE_PLUS_,
		COMMENCE_PAR_JE,
		POSE_QUESTION
	}

	private Ia() {
		m_patterns = new Pattern[5];
		m_matcher = null;
		m_patterns[0] = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
		m_patterns[1] = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
		m_patterns[2] = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
		m_patterns[3] = Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE);
		m_patterns[4] = Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE);

		m_random = new Random();
		m_processor = new MessageProcessor();

		m_id = 2;
	}

	public static Ia getInstance() {
		if(m_instance == null) {
			m_instance = new Ia();
			return m_instance;
		} else {
			return m_instance;
		}
	}
	
	public String process(final String normalizedText, final String userName) {
		int i = 0;
		while(i < 5) {

			m_matcher = m_patterns[i].matcher(normalizedText);
			if(m_matcher.matches()) {
				break;
			}
		}
		
		return responseChoice(i, userName);
	}

	private String responseChoice(final int indice, final String userName) {
		switch (indice) {
			case 0 -> {
				return "Bonjour " + m_matcher.group(1) + ".";
			}
			case 1 -> {
				if(userName != null) {
					return "Votre nom est " + userName + ".";
				} else {
					return "Je ne connais pas votre nom.";
				}
			}
			case 2 -> {
				return "Le plus " + m_matcher.group(1)
                        + " est bien sûr votre enseignant de MIF01 !";
			}
			case 3 -> {
				final String startQuestion = m_processor.pickRandom(new String[] {
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
            	});

				return startQuestion + m_processor.firstToSecondPerson(m_matcher.group(1)) + " ?";
			}
			case 4 -> {
				if(m_random.nextBoolean()) {
					return "Je vous renvoie la question";
				} else {
					return "Ici c'est moi qui pose des questions";
				}
			}
			default -> {
				if (m_random.nextBoolean()) {
					return "Il faut beau aujourd'hui, vous ne trouvez pas ?";
				} else if(m_random.nextBoolean()) {
					return "Je ne comprends pas.";
				} else if(m_random.nextBoolean()) {
					return "Hmmm, hmm ...";
				} else if(userName != null) {
					return "Qu'est-ce qui vous fait dire cela, " + userName + " ?";
				} else {
					return "Qu'est ce qui vous fait dire cela ?";
				}
			}
		}
	}

	public final int getId() {
		return m_id;
	}
}
