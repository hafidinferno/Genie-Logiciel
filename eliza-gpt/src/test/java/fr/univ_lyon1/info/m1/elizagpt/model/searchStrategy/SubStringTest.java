package fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy;

import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Classe Test pour SubStringTest.
 */

public class SubStringTest {
    private SubString subStringSearch;
    private MockedStatic<Dao> mockedDaoStatic;

    /**
     * Prépare l'environnement de test avant chaque test.
     * Initialise les mocks pour la classe Dao et configure l'instance de SubString.
     */
    @BeforeEach
    public void setUp() {
        Dao mockDao = Mockito.mock(Dao.class);
        mockedDaoStatic = Mockito.mockStatic(Dao.class);
        mockedDaoStatic.when(Dao::getInstance).thenReturn(mockDao);
        subStringSearch = new SubString();


        ArrayList<DataMessage> mockMessages = new ArrayList<>(
                Arrays.asList(
                        new DataMessage("On a un example ", false),
                        new DataMessage("On a un Exemple ", false)
                )
        );

        when(mockDao.getAllMessage()).thenReturn(mockMessages);
    }

    /**
     * Teste la méthode search de SubString.
     * Vérifie qu'elle identifie correctement les messages contenant la sous-chaîne recherchée.
     */
    @Test
    public void testSearch() {
        String searchText = "example";
        ArrayList<DataMessage> results = subStringSearch.search(searchText);

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(message -> message.getText().contains("example")));
    }

    /**
     * Nettoie l'environnement de test après chaque test.
     * Ferme le mock static de la classe Dao.
     */
    @AfterEach
    public void fermeMock() {
        mockedDaoStatic.close();
    }
}
