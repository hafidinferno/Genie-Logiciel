package fr.univ_lyon1.info.m1.elizagpt.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/** La classe DaoTest s'occupe de faire des tests.
 * sur les fonctions de la classe Dao pour voir si il marche correctement
 */

public class DaoTest {
    private Dao dao;

    @BeforeEach
    void setUp() {
        dao = new Dao();
    }

    @Test
    void testAddMessage() {
        dao.addMessage("Ca c'est un test", false);
        List<DataMessage> messages = dao.getAllMessage();
        assertEquals("Ca c'est un test", messages.get(0).getText());
    }
    @Test
    void testGetName() {
        dao.addMessage("Je m'appelle Hafid.", false);
        String name = dao.getName();
        assertEquals("Hafid", name);
    }
    @Test
    void testSearch() {
        dao.addMessage("je travaille sur le porjet génie logiciel depuis longtemps", false);
        dao.addMessage("C'est trop long", false);
        List<DataMessage> found = dao.search("long");
        assertEquals(2, found.size());
    }
    @Test
    void testDeleteMessage() {
        dao.addMessage("supprimer le message", false);
        DataMessage message = dao.getAllMessage().get(0);
        Integer hash = message.getHash();
        dao.deleteMessage(hash);
        assertTrue(dao.getAllMessage().isEmpty());
    }

}
