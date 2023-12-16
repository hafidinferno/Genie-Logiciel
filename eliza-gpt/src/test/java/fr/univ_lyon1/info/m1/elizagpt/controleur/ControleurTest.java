package fr.univ_lyon1.info.m1.elizagpt.controleur;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.anyString;
import java.util.ArrayList;

/**
 * Classe Test pour le controleur.
 */
public class ControleurTest {
    private Controleur controleur;
    private JfxView mockView;
    private Dao mockDao;
    private MockedStatic<Dao> mockedDaoStatic;

    /**
     * Configure l'environnement de test avant chaque test.
     * Initialise les mocks pour la classe Dao et la vue JfxView.
     */
    @BeforeEach
    public void setUp() {
        mockDao = Mockito.mock(Dao.class);
        mockedDaoStatic = Mockito.mockStatic(Dao.class);
        mockedDaoStatic.when(Dao::getInstance).thenReturn(mockDao);

        mockView = Mockito.mock(JfxView.class);
        controleur = Controleur.getInstance(mockView);
    }

    private ArrayList<DataMessage> prepareMockMessages() {
        return new ArrayList<>();
    }

    /**
     * Teste le comportement de la méthode handleUserReply avec un texte valide.
     * Vérifie si la vue est rafraîchie correctement avec les messages.
     */
    @Test
    public void testHandleUserReplyWithValidText() {
        String validText = "Some valid text";
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(mockDao.getAllMessage()).thenReturn(mockMessages);

        controleur.handleUserReply(validText);

        verify(mockView, times(1)).refreshView(mockMessages);
    }

    /**
     * Teste la fonctionnalité de suppression d'un message.
     * Vérifie si la vue est rafraîchie après la suppression du message.
     */
    @Test
    public void testDeleteMessage() {
        Integer hash = 1;
        ArrayList<DataMessage> mockMessages = new ArrayList<>();
        controleur.deleteMessage(hash);

        when(mockDao.getAllMessage()).thenReturn(mockMessages);
        verify(mockView, times(1)).refreshView(mockMessages);
    }

    /**
     * Teste la recherche de texte avec une requête valide.
     * Vérifie si le label de recherche dans la vue est mis à jour correctement.
     */
    @Test
    public void testSearchTextWithValidText() {
        String searchText = "specific query";
        ArrayList<DataMessage> expectedMessages = new ArrayList<>();
        expectedMessages.add(new DataMessage("Message containing specific query", false));
        when(mockDao.search(anyString())).thenReturn(expectedMessages);

        controleur.searchText(searchText);

        verify(mockView, atLeastOnce()).changeSearchLabel("Searching for: " + searchText);
    }

    /**
     * Teste la recherche de texte avec une chaîne vide.
     * Vérifie si le label de recherche indique qu'aucune recherche n'est active.
     */
    @Test
    public void testSearchTextWithEmptyText() {
        controleur.searchText("");
        verify(mockView, atLeastOnce()).changeSearchLabel("No active search.");
    }

    /**
     * Teste la recherche de texte avec une chaîne nulle.
     * Vérifie si le label de recherche indique qu'aucune recherche n'est active.
     */
    @Test
    public void testSearchTextWithNullText() {
        controleur.searchText(null);
        verify(mockView, atLeastOnce()).changeSearchLabel("No active search.");
    }

    /**
     * Teste l'annulation de la recherche.
     * Vérifie si le label de recherche est réinitialisé et si la vue est rafraîchie.
     */
    @Test
    public void testUndoSearch() {
        ArrayList<DataMessage> allMessages = new ArrayList<>();
        when(mockDao.getAllMessage()).thenReturn(allMessages);

        controleur.undoSearch();

        verify(mockView, atLeastOnce()).changeSearchLabel("");
        verify(mockView, atLeastOnce()).refreshView(allMessages);
    }

    /**
     * Teste la synchronisation de la vue.
     * Vérifie si la vue est rafraîchie avec les messages actuels.
     */
    @Test
    public void testSyncVue() {
        ArrayList<DataMessage> messages = new ArrayList<>();
        when(mockDao.getAllMessage()).thenReturn(messages);
        controleur.syncVue();
        verify(mockView, atLeastOnce()).refreshView(messages);
    }

    /**
     * Ferme le mock static après chaque test.
     */
    @AfterEach
    public void fermeMock() {
        mockedDaoStatic.close();
    }
}
