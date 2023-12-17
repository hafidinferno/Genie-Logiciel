package fr.univ_lyon1.info.m1.elizagpt.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class CreateResponseTest {

    private CreateResponse createResponse;
    private Random mockRandom;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        createResponse = new CreateResponse();
        createResponse = new CreateResponse();
        mockRandom = mock(Random.class);

        // Utiliser la réflexion pour accéder au champ `random` de `CreateResponse`
        Field randomField = CreateResponse.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(createResponse, mockRandom);
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
     * Teste la réponse pour le pattern 5 . Vérifie si la réponse est appropriée
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
     * Teste la réponse par défaut(Plusieurs cas).
     * Vérifie si une des réponses attendues est donnée dans un ensemble de tentatives.
     */
    @Test
    void testChooseDefaultResponse() {

        when(mockRandom.nextBoolean()).thenReturn(true);
        createResponse.response(7, null);
        assertEquals("Il fait beau aujourd'hui, vous ne trouvez pas ?",
                createResponse.response(7, null));


        // Test de la branche avec le nom d'utilisateur
        createResponse.setName("Hafid");
        when(mockRandom.nextBoolean()).thenReturn(false, false, false);
        createResponse.response(7, null);
        assertEquals("Qu'est-ce qui vous fait dire cela, Hafid ?",
                createResponse.response(7, null));


    }

    /**
     * Pour les tests de la méthodes response.
     * je vais faire 2 cas avec les deux premières patterns.
     * et la réponse par défault
     */
    @Test
    void testResponseForHello() {
        Matcher mockMatcher = mock(Matcher.class);
        when(mockMatcher.group(1)).thenReturn("Hafid");

        String result = createResponse.response(0, mockMatcher);
        assertEquals("Bonjour Hafid.", result);
    }


    @Test
    void testResponseForWho() {
        Matcher mockMatcher = mock(Matcher.class);
        when(mockMatcher.group(1)).thenReturn("beau");

        String result = createResponse.response(2, mockMatcher);
        assertEquals("Le plus beau est bien sûr votre enseignant de MIF01 !", result);
    }


    @Test
    void testResponseForDefault() {
        when(mockRandom.nextBoolean()).thenReturn(true);
        String result = createResponse.response(7, null);
        assertEquals("Il fait beau aujourd'hui, vous ne trouvez pas ?", result);
    }






}
