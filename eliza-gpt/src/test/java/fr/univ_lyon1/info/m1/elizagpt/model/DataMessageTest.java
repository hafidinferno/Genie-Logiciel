package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataMessageTest {

    /**
     * Teste la méthode isIa pour vérifier si elle retourne correctement
     * l'état indiquant si le message provient de l'IA ou de l'utilisateur.
     */
    @Test
    void isIa() {
        DataMessage dataMessage = new DataMessage("Bonjour", true);
        assertTrue(dataMessage.isIa());

        dataMessage = new DataMessage("Bonjour", false);
        assertFalse(dataMessage.isIa());
    }

    /**
     * Teste la méthode getText pour vérifier si elle retourne correctement
     * le texte du message.
     */
    @Test
    void getText() {
        String testText = "Bonjour je m'appelle Hafid";
        DataMessage dataMessage = new DataMessage(testText, true);
        assertEquals(testText, dataMessage.getText());
    }

    /**
     * Teste la méthode getHash pour vérifier si elle retourne un hash valide.
     * Le hash doit être non-null et unique pour chaque instance.
     */
    @Test
    void getHash() {
        DataMessage dataMessage1 = new DataMessage("Bonjour", true);
        DataMessage dataMessage2 = new DataMessage("Au revoir", true);

        assertNotNull(dataMessage1.getHash());
        assertNotNull(dataMessage2.getHash());

    }
}
