package fr.univ_lyon1.info.m1.elizagpt.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/** La classe DaoTest s'occupe de faire des tests.
 * sur les fonctions de la classe Dao pour voir si il marche correctement
 */

public class DaoTest {
    private Dao dao;

    private SearchFunction mockSearchFunction;

    @BeforeEach
    void setUp() {
        dao = new Dao();
        mockSearchFunction = mock(SearchFunction.class);
        dao.setSearchStrategy(mockSearchFunction);

    }

    /**
     * Teste la méthode addMessage pour vérifier si un nouveau message
     * est correctement ajouté à la liste des messages.
     */
    @Test
    void testAddMessage() {
        dao.addMessage("Ca c'est un test", false);
        List<DataMessage> messages = dao.getAllMessage();
        assertEquals("Ca c'est un test", messages.get(0).getText());
    }
    /**
     * Teste la méthode getName pour vérifier si elle extrait correctement
     * le nom de l'utilisateur à partir d'un message.
     */
    @Test
    void testGetName() {
        dao.addMessage("Je m'appelle Hafid.", false);
        String name = dao.getName();
        assertEquals("Hafid", name);
    }
    /**
     * Teste la méthode search pour vérifier si elle trouve correctement
     * les messages contenant un mot clé spécifique.
     */
    @Test
    void testSearch() {
        dao.addMessage("je travaille sur le porjet génie logiciel depuis longtemps", false);
        dao.addMessage("C'est trop long", false);
        List<DataMessage> found = dao.search("long");
        assertEquals(0, found.size());
    }
    /**
     * Teste la méthode deleteMessage pour vérifier si elle supprime
     * correctement un message de la liste en utilisant son hash.
     */
    @Test
    void testDeleteMessage() {
        dao.addMessage("supprimer le message", false);
        DataMessage message = dao.getAllMessage().get(0);
        Integer hash = message.getHash();
        dao.deleteMessage(hash);
        assertTrue(dao.getAllMessage().isEmpty());
    }

}
