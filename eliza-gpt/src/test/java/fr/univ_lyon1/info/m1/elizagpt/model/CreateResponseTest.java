package fr.univ_lyon1.info.m1.elizagpt.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class CreateResponseTest {

    private CreateResponse createResponse;

    @BeforeEach
    void setUp() {

        createResponse = new CreateResponse();
    }
    /**
     * Teste la méthode setName pour vérifier si elle affecte correctement le nom,
     * et si la méthode response renvoie la réponse attendue pour le pattern 1.
     */
    @Test
    void testSetName() {
        createResponse.setName("Hafid");
        assertEquals("Votre nom est Hafid.", createResponse.response(1, null));
    }

    /**
     * Teste la réponse pour le pattern 0 (salutation). Vérifie si la réponse
     * correspond au format attendu lorsque le pattern est reconnu.
     */
    @Test
    void testCreateHelloResponse() {
        String input = "Hafid";
        Pattern pattern = Pattern.compile(".*(Hafid).*");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String response = createResponse.response(0, matcher);
            assertEquals("Bonjour Hafid.", response);
        }
    }
    /**
     * Teste la réponse pour le pattern 2 (qui est le plus...). Vérifie si la réponse
     * est correcte pour une entrée donnée correspondant à ce pattern.
     */

    @Test
    void testCreateWhoIsResponse() {
        String input = "beau";
        Pattern pattern = Pattern.compile(".*(beau).*");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String response = createResponse.response(2, matcher);
            assertEquals("Le plus beau est bien sûr votre enseignant de MIF01 !", response);

        }

    }
    /**
     * Teste la réponse pour le pattern 3 (réflexion personnelle). Vérifie si la réponse
     * commence par une des phrases attendues et contient le contenu pertinent.
     */
    @Test
    void testCreateQuestion() {
        String input = "je suis content";
        Pattern pattern = Pattern.compile(".*(je suis content).*");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String response = createResponse.response(3, matcher);
            assertTrue(response.startsWith("Pourquoi dites-vous que ")
                    || response.startsWith("Pourquoi pensez-vous que ")
                    ||  response.startsWith("Êtes-vous sûr que "));
            assertTrue(response.contains("vous êtes content"));

        }
    }
    /**
     * Teste la réponse pour le pattern 4 (retour de question). Vérifie si la réponse
     * correspond à une des réponses attendues pour ce pattern.
     */
    @Test
    void testCreateRetorqueResponse() {
        String response = createResponse.response(4, null);
        assertTrue(response.equals("Je vous renvoie la question")
                || response.equals("Ici c'est moi qui pose des questions"), response);
    }
    /**
     * Teste la réponse pour le pattern 5 (adieu). Vérifie si la réponse est appropriée
     * lorsqu'un utilisateur dit au revoir.
     */
    @Test
    void testCreateGoodBye() {
        createResponse.setName("Hafid");
        String response = createResponse.response(5, null);
        assertTrue(response.equals("Au revoir Hafid")
                || response.equals("Oh non, c'est trop triste de se quitter !"), response);
    }

    /**
     * Teste la réponse par défaut (lorsqu'aucun pattern n'est trouvé).
     * Vérifie si une des réponses attendues est donnée dans un ensemble de tentatives.
     */
    @Test
    void testChooseDefaultResponse() {
        List<String> expectedResponses = Arrays.asList(
                "Il fait beau aujourd'hui, vous ne trouvez pas ?",
                "Je ne comprends pas.",
                "Hmmm, hmm ...",
                "Qu'est-ce qui vous fait dire cela, ?",
                "Qu'est ce qui vous fait dire cela ?"
        );

        boolean foundExpectedResponse = false;
        for (int i = 0; i < 100; i++) {
            String response = createResponse.response(6, null);
            if (expectedResponses.contains(response)) {
                foundExpectedResponse = true;
                break;
            }
        }

        assertTrue(foundExpectedResponse);
    }







}
