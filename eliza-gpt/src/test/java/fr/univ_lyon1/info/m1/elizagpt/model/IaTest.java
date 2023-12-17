package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


/**
 * Cette classe contient des cas de test pour la classe IA.
 */
class IaTest {

    private Ia iaInstance;

    /**
     * Configure l'environnement de test en initialisant l'instance de IA
     * avant chaque test.
     */
    @BeforeEach
    void setUp() {
        iaInstance = Ia.getInstance();
    }

    /**
     * Teste le fonctionnement du modèle singleton
     * en vérifiant que deux instances de IA qui sont identiques.
     */
    @Test
    void testSingletonInstance() {
        Ia anotherInstance = Ia.getInstance();
        assertSame(iaInstance, anotherInstance);
    }

    /**
     * Teste le traitement du pattern  "Qui est le plus beau ?"
     * pour la methode process de la classe IA.
     */
    @Test
    void testProcessForWhoIsPattern() {
        String response = iaInstance.process("Qui est le plus beau ?", "Hafid");
        assertEquals(iaInstance.process("Qui est le plus beau ?", "Hafid"), response);
    }
}

