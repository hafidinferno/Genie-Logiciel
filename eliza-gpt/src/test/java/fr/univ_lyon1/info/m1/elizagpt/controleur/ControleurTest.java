package fr.univ_lyon1.info.m1.elizagpt.controleur;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;


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
    @Mock
    private HandleMessage handleMessageMock;

    private Controleur controleur;

    /**
     * La fonction setUp s'occupe de initialiser les mock.
     * qu'on va utiliser des les fonctions test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Controleur.setDao(daoMock);
        controleur = Controleur.getInstance(viewMock);
        controleur.setHandleMessage(handleMessageMock);
    }


    /**
     * la fonction deleteMessageTest s'occupe de voir.
     * si on appelle les fonctions refreshView et deleteMessages de la vue et du controleur.
     * et coir si le message était supprimer(On a deja testé ca dans DaoTest).
     */
    @Test
    void deleteMessageTest() {
        Dao mockDao = mock(Dao.class);
        Integer hash = 1;
        ArrayList<DataMessage> mockMessages = new ArrayList<>();
        controleur.deleteMessage(hash);

        when(mockDao.getAllMessage()).thenReturn(mockMessages);


        verify(daoMock).deleteMessage(hash);
        verify(viewMock, times(2)).refreshView(mockMessages);

    }

    /**
     * la fonction testHandleUserReplyWithValidText.
     */
    @Test
    public void testHandleUserReplyWithValidText() {
        String validText = "My name is hafid";
        ArrayList<DataMessage> mockMessages = new ArrayList<>();

        when(daoMock.getAllMessage()).thenReturn(mockMessages);

        controleur.handleUserReply(validText);

        verify(handleMessageMock, times(1)).reply(validText, false);
        verify(handleMessageMock, times(1)).iaResponse(validText);


    }


    /**
     * la fonction testSearchTextWithEmptyText.
     */
    @Test
    public void testSearchTextWithEmptyText() {
        String searchText = "";
        ArrayList<DataMessage> mockMessages = new ArrayList<>();

        when(handleMessageMock.searchText(searchText)).thenReturn(mockMessages);

        controleur.searchText(searchText);

        verify(viewMock, times(1)).changeSearchLabel("No active search.");
        verify(viewMock, times(1)).refreshView(mockMessages);
    }

    /**
     * la fonction testSearchTextWithNullText.
     */
    @Test
    public void testSearchTextWithNullText() {
        String searchText = null;
        ArrayList<DataMessage> mockMessages = new ArrayList<>();

        when(handleMessageMock.searchText(searchText)).thenReturn(mockMessages);

        controleur.searchText(searchText);

        verify(viewMock, times(1)).changeSearchLabel("No active search.");
        verify(viewMock, times(2)).refreshView(mockMessages);
    }





}
