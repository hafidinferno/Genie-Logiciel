package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * La classe Ia nous permet de gérer les différentes fonctionnalités
 * qui composeront notre IA. Il est bon de savoir qu'une seule instance
 * de cette classe doit être présente dans le programme.
 */
public final class Ia {
	private static Ia instance;
	private final ArrayList<Pattern> patterns;
	private Matcher matcher;
	private final CreateResponse responseList;

	private final int nbPatterns;

	private Ia() {

		patterns = new ArrayList<>();
		matcher = null;
		patterns.add(Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("Quel est mon nom\\?", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("Qui est le plus (.*)\\?", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("Au revoir(.*)\\.", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("(.*)!", Pattern.CASE_INSENSITIVE));
		nbPatterns = patterns.size();

		responseList = new CreateResponse();

	}

	/**
	 * Fonction permettant de récupérer l'instance d'IA s'il en existe une
	 * ou d'en créer une et la récupérer.
	 *
	 * @return Ia instance
	 */
	public static Ia getInstance() {
		if (instance == null) {
			instance = new Ia();
		}
		return instance;
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
		while (i < nbPatterns) {

			matcher = patterns.get(i).matcher(normalizedText);
			if (matcher.matches()) {
				break;
			}
			i++;
		}

		return response(i, userName);
	}

	private String response(final int indice, final String userName) {
		responseList.setName(userName);
		return responseList.response(indice, matcher);
	}


}
