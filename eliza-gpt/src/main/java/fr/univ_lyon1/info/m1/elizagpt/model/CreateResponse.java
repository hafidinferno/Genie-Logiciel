package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * La fonction permet fabriquer et récuperer la réponse
 * de l'IA qu'obtiendra l'utilisateur.
 */
public class CreateResponse {
    private String groupeNominal;
    private String username;
    private ArrayList<String> responseList;
    private MessageProcessor processor;
    private Random random;

    /**
     * Constructeur de la classe.
     */
    public CreateResponse() {
        username = null;
        groupeNominal = null;
        processor = new MessageProcessor();
        random = new Random();

        responseList = new ArrayList<>();

        responseList.add("Bonjour .");
        responseList.add("Je ne connais pas votre nom.");
        responseList.add("Le plus " + groupeNominal
                + " est bien sûr votre enseignant de MIF01 !");
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
     * La fonction permet de changer le groupe nominal
     * actuel. Cependant, si l'indice entré en paramètre est supérieur
     * ou égal au nombre de choix de réponses possible alors
     * une chaine de charactères vide est affectée au groupe nominal.
     * @param indice indice de la réponse choisie dans le tableau
     *               de réponses posssibles.
     * @param groupNom groupe nominal.
     */
    public void setGroupeNominal(final int indice, final String groupNom) {
        if (indice + 1 < responseList.size()) {
            groupeNominal = groupNom;
        } else {
            groupeNominal = " ";
        }
    }

    /**
     * Fonction permettant de créer la réponse de salutation.
     */
    private void createHelloResponse() {
        responseList.set(0, "Bonjour " + groupeNominal + ".");
    }

    /**
     * fonction permettant de créer la réponse correspondant
     * au phrase de l'utilisateur commençant par la première personne du singulier.
     * Cette fontion ne modifie pas notre liste de réponses
     * si le groupe nominal actuel est n'est pas différent de "null".
     */
    private void createQuestion() {
        String startQuestion = processor.pickRandom(new String[]{
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
        });

        if (groupeNominal != null) {
            responseList.set(3, startQuestion
                    + processor.firstToSecondPerson(groupeNominal)
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
            responseList.set(6, "Il faut beau aujourd'hui, vous ne trouvez pas ?");
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
     * @return une réponse de l'IA parmis sa liste
     */
    public String response(final int indice) {
        createHelloResponse();
        createQuestion();
        createRetorqueResponse();
        createGoodBye();
        chooseDefaultResponse();
        return responseList.get(indice);
    }
    
}
