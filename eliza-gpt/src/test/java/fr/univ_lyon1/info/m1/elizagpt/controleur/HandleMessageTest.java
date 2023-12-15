package fr.univ_lyon1.info.m1.elizagpt.controleur;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.Ia;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import java.lang.reflect.Field;



class HandleMessageTest {

    @Mock
    private Dao mockDao;

    @Mock
    private Ia mockIa;


    @InjectMocks
    private HandleMessage handleMessage;

    @BeforeEach
    public void setUp() throws Exception {
        // Création des mocks
        mockDao = mock(Dao.class);
        mockIa = mock(Ia.class);

        Field daoField = Dao.class.getDeclaredField("instance");
        daoField.setAccessible(true);
        daoField.set(null, mockDao);

        Field iaField = Ia.class.getDeclaredField("instance");
        iaField.setAccessible(true);
        iaField.set(null, mockIa);

        // Initialisation de HandleMessage
        handleMessage = new HandleMessage();
    }

    /**
     * Teste la méthode reply pour vérifier si elle ajoute correctement un message
     * dans le DAO. Cette méthode est appelée à la fois par l'utilisateur et l'IA.
     */
    @Test
    void reply() {
        String testMessage = "Bonjour";
        boolean isIa = false;

        handleMessage.reply(testMessage, isIa);

        verify(mockDao, times(1)).addMessage(testMessage, isIa);
    }
    /**
     * Teste la méthode iaResponse pour s'assurer qu'elle appelle la méthode process
     * de l'IA avec les bons arguments et enregistre correctement la réponse de l'IA.
     */
    @Test
    void iaResponse() {
        String userInput = "Bonjour";
        String iaReply = "La réponse de l'ia";
        when(mockIa.process(userInput, null)).thenReturn(iaReply);

        handleMessage.iaResponse(userInput);

        verify(mockIa, times(1)).process(userInput, null);
        verify(mockDao, times(1)).addMessage(iaReply, true);
    }

    /**
     * Teste la méthode searchText Fonction de recherche via regex dans la database.
     */
    @Test
    void searchText() {
        String searchText = "texte de recherche";
        ArrayList<DataMessage> expectedMessages = new ArrayList<>();
        when(mockDao.search(searchText)).thenReturn(expectedMessages);

        ArrayList<DataMessage> result = handleMessage.searchText(searchText);

        assertEquals(expectedMessages, result);
    }
}
