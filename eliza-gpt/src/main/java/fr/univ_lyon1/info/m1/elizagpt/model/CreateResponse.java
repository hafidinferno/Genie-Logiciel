package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

/**
 * La fonction permet fabriquer et récuperer la réponse
 * de l'IA qu'obtiendra l'utilisateur.
 */
public class CreateResponse {
    private String username;
    private final ArrayList<String> responseList;
    private final MessageProcessor processor;
    private final Random random;

    /**
     * Constructeur de la classe.
     */
    public CreateResponse() {
        username = null;
        processor = new MessageProcessor();
        random = new Random();

        responseList = new ArrayList<>();

        responseList.add("Bonjour .");
        responseList.add("Je ne connais pas votre nom.");
        responseList.add(" ");
        responseList.add("verbe question");
        responseList.add("rétorque");
        responseList.add("au revoir");
        responseList.add("defaut");
    }

    /**
     * La fonction permet de modifier le nom de l'utilisateur.
     * Toutefois, si un nom est déjà connu, alors la fonction
     * ne modifiera pas le nom déjà présent.
     * @param name Nom de l'utilisateur.
     */
    public void setName(final String name) {
        if (name != null) {
            username = name;
            responseList.set(1, "Votre nom est " + username + ".");
        }
    }

    /**
     * Fonction permettant de créer la réponse de salutation.
     * si l'indice ne corresspond pas à l'élément du tableau
     * correspondant à cette réponse alors rien est fait.
     * @param indice indice correspondant à la réponse choisie.
     * @param matcher matcher contenant la phrase de l'utilisateur.
     */
    private void createHelloResponse(final int indice, final Matcher matcher) {
        if (indice == 0) {
            responseList.set(0, "Bonjour " + matcher.group(1) + ".");
        }
    }

    /**
     * Fonction permettant de créer la réponse indiquant
     * qui est "le plus..." en fonction du groupe nominal.
     * @param indice indice de la réponse choisie.
     * @param matcher matcher contenant la phrase de l'utilisateur.
     */
    private void createWhoIsResponse(final int indice, final Matcher matcher) {
        if (indice == 2) {
            responseList.set(2, "Le plus " + matcher.group(1)
                    + " est bien sûr votre enseignant de MIF01 !");
        }
    }

    /**
     * fonction permettant de créer la réponse correspondant
     * au phrase de l'utilisateur commençant par la première personne du singulier.
     * Cette fontion ne modifie pas notre liste de réponses
     * si le groupe nominal actuel est n'est pas différent de "null".
     */
    private void createQuestion(final int indice, final Matcher matcher) {
        String startQuestion = processor.pickRandom(new String[]{
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
        });

        if (indice == 3) {
            responseList.set(3, startQuestion
                    + processor.firstToSecondPerson(matcher.group(1))
                    + "?");
        }
    }

    /**
     * Fonction qui construit la réponse qui correspond
     * aux messages intérrogateurs de l'utilisateur.
     */
    private void createRetorqueResponse() {
        if (random.nextBoolean()) {
            responseList.set(4, "Je vous renvoie la question");
        } else {
            responseList.set(4, "Ici c'est moi qui pose des questions");
        }
    }

    /**
     * La fonction construit un message congediant.
     */
    private void createGoodBye() {
        if (username != null && random.nextBoolean()) {
            responseList.set(5, "Au revoir " + username);
        } else {
            responseList.set(5, "Oh non, c'est trop triste de se quitter !");
        }
    }

    /**
     * Cette fonction est appellée afin de construire le message
     * par défaut de notre IA.
     */
    private void chooseDefaultResponse() {
        if (random.nextBoolean()) {
            responseList.set(6, "Il fait beau aujourd'hui, vous ne trouvez pas ?");
        } else if (random.nextBoolean()) {
            responseList.set(6, "Je ne comprends pas.");
        } else if (random.nextBoolean()) {
            responseList.set(6, "Hmmm, hmm ...");
        } else if (username != null) {
            responseList.set(6, "Qu'est-ce qui vous fait dire cela, "
                    + username
                    + " ?");
        } else {
            responseList.set(6, "Qu'est ce qui vous fait dire cela ?");
        }
    }

    /**
     * La fonction va construire une liste de réponse
     * puis à l'aide de l'indice passé en paramêtre, une
     * réponse sera séléctionnée.
     * @param indice indice de la réponse correspondant
     *               au message envoyé par l'utilisateur.
     * @param matcher matcher contenant la phrase de l'utilisateur.
     * @return une réponse de l'IA parmis sa liste
     */
    public String response(final int indice, final Matcher matcher) {
        createHelloResponse(indice, matcher);
        createWhoIsResponse(indice, matcher);
        createQuestion(indice, matcher);
        createRetorqueResponse();
        createGoodBye();
        chooseDefaultResponse();
        return responseList.get(indice);
    }
    
}
