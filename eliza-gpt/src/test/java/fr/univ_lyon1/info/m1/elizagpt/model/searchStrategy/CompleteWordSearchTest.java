package fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy;

import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Classe Test pour CompleteWordSearch.
 */

public class CompleteWordSearchTest {
    private CompleteWordSearch completeWordSearch;
    private Dao mockDao;
    private MockedStatic<Dao> mockedDaoStatic;

    /**
     * Prépare l'environnement de test avant chaque test.
     * Initialise les mocks pour la classe Dao et configure l'instance de CompleteWordSearch.
     */
    @BeforeEach
    public void setUp() {
        mockDao = Mockito.mock(Dao.class);
        mockedDaoStatic = Mockito.mockStatic(Dao.class);
        mockedDaoStatic.when(Dao::getInstance).thenReturn(mockDao);

        completeWordSearch = new CompleteWordSearch();


        ArrayList<DataMessage> mockMessages = new ArrayList<>(
                Arrays.asList(
                        new DataMessage("word is big", false),
                        new DataMessage("noword", false)
                )
        );

        when(mockDao.getAllMessage()).thenReturn(mockMessages);
    }

    /**
     * Teste la méthode getTextForStrategy de CompleteWordSearch.
     * Vérifie qu'elle retourne le bon motif regex pour l'entrée donnée.
     */
    @Test
    public void testGetTextForStrategy() {
        String input = "word";
        String expectedOutput = "\\bword\\b";
        assertEquals(expectedOutput, completeWordSearch.getTextForStrategy(input));
    }

    /**
     * Teste la méthode search de CompleteWordSearch.
     * Vérifie qu'elle identifie correctement les messages contenant le mot complet "word".
     */
    @Test
    public void testSearch() {
        String searchText = "word";
        ArrayList<DataMessage> results = completeWordSearch.search(searchText);


        assertEquals(1, results.size());
        for (DataMessage message : results) {
            assertTrue(message.getText().matches(".*\\bword\\b.*"));
        }
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
