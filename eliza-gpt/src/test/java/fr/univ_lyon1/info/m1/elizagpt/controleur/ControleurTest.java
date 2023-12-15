package fr.univ_lyon1.info.m1.elizagpt.controleur;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.atMost;



/**
 * La classe ControleurTest s'occupe de tester
 * les fonctions de la classe controleur
 * et voir si elles appellent les fonctions du modele et de la vue.
 */
public class ControleurTest {

    @Mock
    private JfxView viewMock;
    @Mock
    private Dao daoMock;


    @InjectMocks
    private static Controleur controleur;


    /**
     * La méthode setUp s'occupe d'initialiser les mocks et l'instance du controleur.
     * Cette configuration est utilisée dans toutes les méthodes de test.
     */
    @BeforeEach
    public void setUp() {
        daoMock = mock(Dao.class);
        viewMock = mock(JfxView.class);
        controleur = Controleur.getInstance(viewMock);
        Controleur.setDao(daoMock);


    }

    private ArrayList<DataMessage> prepareMockMessages() {
        return new ArrayList<>();
    }

    /**
     * Teste la méthode handleUserReply avec un texte valide.
     * Vérifie que les méthodes getAllMessage et refreshView sont appelées correctement.
     */
    @Test
    public void testHandleUserReplyWithValidText() {
        String validText = "Some valid text";
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(daoMock.getAllMessage()).thenReturn(mockMessages);

        controleur.handleUserReply(validText);

        verify(daoMock, times(1)).getAllMessage();
        verify(viewMock, times(2)).refreshView(mockMessages);
    }



    /**
     * Teste la méthode handleUserReply avec une valeur vide.
     * Vérifie que les méthodes getAllMessage est appelée correctement.
     */

    @Test
    public void testHandleUserReplyWithEmptyText() {
        controleur.handleUserReply("");
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(daoMock.getAllMessage()).thenReturn(mockMessages);


        verify(daoMock, never()).getAllMessage();


    }

    /**
     * Teste la méthode handleUserReply avec une valeur null.
     * Vérifie que les méthodes getAllMessage est appelé correctement.
     */
    @Test
    public void testHandleUserReplyWithNullText() {
        controleur.handleUserReply(null);
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(daoMock.getAllMessage()).thenReturn(mockMessages);


        verify(daoMock, never()).getAllMessage();

    }




    /**
     * la fonction deleteMessageTest s'occupe de voir.
     * si on appelle la fonction  deleteMessages de la vue et du controleur.
     * et voir si le message était supprimer(On a deja testé ca dans DaoTest).
     */
    @Test
    public void testDeleteMessage() {
        Integer messageHash = 12345;
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(daoMock.getAllMessage()).thenReturn(mockMessages);

        controleur.deleteMessage(messageHash);

        verify(daoMock, times(1)).deleteMessage(messageHash);
    }

    /**
     * Teste la méthode searchText avec un texte de recherche spécifique.
     * Vérifie que la vue est mise à jour avec le label de recherche et les résultats.
     */
    @Test
    public void testSearchTextWithValidText() {
        String searchText = "Hafid";
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(daoMock.getAllMessage()).thenReturn(mockMessages);

        controleur.searchText(searchText);

        verify(viewMock, times(1)).changeSearchLabel("Searching for: " + searchText);
        verify(viewMock, atMost(2)).refreshView(mockMessages);
    }

    /**
     * Teste la méthode searchText avec un texte vide.
     */
    @Test
    public void testSearchTextWithEmptyText() {
        controleur.searchText("");

        verify(viewMock, atMost(2)).changeSearchLabel("No active search.");
    }

    /**
     * Teste la méthode searchText avec un texte null.
     */
    @Test
    public void testSearchTextWithNullText() {
        controleur.searchText(null);

        verify(viewMock, atMost(2)).changeSearchLabel("No active search.");
    }



    /**
     * Teste la méthode undoSearch pour vérifier si la vue est réinitialisée
     * avec tous les messages après une recherche.
     */
    @Test
    public void testUndoSearch() {
        ArrayList<DataMessage> mockMessages = prepareMockMessages();
        when(daoMock.getAllMessage()).thenReturn(mockMessages);

        controleur.undoSearch();

        verify(viewMock, atMost(2)).refreshView(mockMessages);
    }









}
