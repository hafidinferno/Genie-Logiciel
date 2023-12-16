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
 * Classe Test pour RegexSearch.
 */

public class RegexSearchTest {
    private RegexSearch regexSearch;
    private Dao mockDao;
    private MockedStatic<Dao> mockedDaoStatic;

    /**
     * Configure l'environnement de test avant chaque test.
     * Initialise les mocks pour la classe Dao et prépare l'instance de RegexSearch.
     */
    @BeforeEach
    public void setUp() {
        mockDao = Mockito.mock(Dao.class);
        mockedDaoStatic = Mockito.mockStatic(Dao.class);
        mockedDaoStatic.when(Dao::getInstance).thenReturn(mockDao);

        regexSearch = new RegexSearch();


        ArrayList<DataMessage> mockMessages = new ArrayList<>(
                Arrays.asList(
                        new DataMessage("trouve le ballon", false),
                        new DataMessage("il trouve le trésor", false),
                        new DataMessage("rien", false)
                )
        );

        when(mockDao.getAllMessage()).thenReturn(mockMessages);
    }

    /**
     * Teste la méthode getTextForStrategy de RegexSearch.
     * Vérifie qu'elle retourne la bonne expression régulière pour une entrée donnée.
     */
    @Test
    public void testGetTextForStrategy() {
        String input = "trouve";
        String expectedOutput = ".*trouve.*";
        assertEquals(expectedOutput, regexSearch.getTextForStrategy(input));
    }

    /**
     * Teste la méthode search de RegexSearch.
     * Vérifie qu'elle identifie correctement les messages contenant le terme recherché.
     */
    @Test
    public void testSearch() {
        String searchText = "trouve";
        ArrayList<DataMessage> results = regexSearch.search(searchText);


        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(message -> message.getText().contains("trouve")));
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
