package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * La classe Ia nous perme de gérer les différentes fonctionnalités
 * qui composeront notre IA. Il est bon de savoir qu'une seule instance
 * de cette classe doit être présente dans le programme.
 */
public final class Ia {
	private static Ia instance;
	private final MessageProcessor processor;
	private final Pattern[] patterns;
	private Matcher matcher;
	private final Random random;

	// Idée à discuter: cette enum devait permettre d'identifier les
	// réponses possibles de notre IA.
	// public enum RespondType {
	// 	PRESENTATION,
	// 	DEMANDE_DE_NOM,
	// 	LE_PLUS_,
	// 	COMMENCE_PAR_JE,
	// 	POSE_QUESTION
	// }

	private Ia() {
		patterns = new Pattern[5];
		matcher = null;
		patterns[0] = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
		patterns[1] = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
		patterns[2] = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
		patterns[3] = Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE);
		patterns[4] = Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE);

		random = new Random();
		processor = new MessageProcessor();

	}

	/**
	 * Fonction permettant de récupérer l'instance d'IA s'il en existe une
	 * ou d'en créer une puis la récupérer.
	 *
	 * @return Ia instance
	 */
	public static Ia getInstance() {
		if (instance == null) {
			instance = new Ia();
			return instance;
		} else {
			return instance;
		}
	}

	/**
	 * Fonction permettant de connaitre quel pattern est présent
	 * dans le message de l'utilisateur afin de lui répondre.
	 *
	 * @param normalizedText message de l'utilisateur.
	 * @param userName       Nom de l'utilisateur si le programme le connait
	 * @return une réponse de type String.
	 */
	public String process(final String normalizedText, final String userName) {
		int i = 0;
		while (i < 5) {

			matcher = patterns[i].matcher(normalizedText);
			if (matcher.matches()) {
				break;
			}
			i++;
		}

		return responseChoice(i, userName);
	}

	private String responseChoice(final int indice, final String userName) {
		switch (indice) {
			case 0:
				return "Bonjour " + matcher.group(1) + ".";
			case 1:
				if (userName != null) {
					return "Votre nom est " + userName + ".";
				} else {
					return "Je ne connais pas votre nom.";
				}
			case 2:
				return "Le plus " + matcher.group(1)
						+ " est bien sûr votre enseignant de MIF01 !";
			case 3:
				final String startQuestion = processor.pickRandom(new String[]{
						"Pourquoi dites-vous que ",
						"Pourquoi pensez-vous que ",
						"Êtes-vous sûr que ",
				});

				return startQuestion
						+ processor.firstToSecondPerson(matcher.group(1))
						+ " ?";
			case 4:
				if (random.nextBoolean()) {
					return "Je vous renvoie la question";
				} else {
					return "Ici c'est moi qui pose des questions";
				}
			default:
				if (random.nextBoolean()) {
					return "Il faut beau aujourd'hui, vous ne trouvez pas ?";
				} else if (random.nextBoolean()) {
					return "Je ne comprends pas.";
				} else if (random.nextBoolean()) {
					return "Hmmm, hmm ...";
				} else if (userName != null) {
					return "Qu'est-ce qui vous fait dire cela, "
							+ userName
							+ " ?";
				} else {
					return "Qu'est ce qui vous fait dire cela ?";
				}
		}
	}
}
