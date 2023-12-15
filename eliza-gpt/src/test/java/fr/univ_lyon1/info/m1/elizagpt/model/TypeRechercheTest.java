package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeRechercheTest {


    /**
     * Teste la méthode toString pour vérifier si elle retourne correctement
     * le nom de la classe TypeRecherche.
     */
    @Test
    void testToString() {
        TypeRecherche typeRecherche = new TypeRecherche();
        String expectedName = "TypeRecherche";
        assertEquals(expectedName, typeRecherche.toString());
    }
}
