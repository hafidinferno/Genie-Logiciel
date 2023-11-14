package fr.univ_lyon1.info.m1.elizagpt.controlleur;

import fr.univ_lyon1.info.m1.elizagpt.controlleur.HandleMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageId;





public class Controleur {
        private static Controleur instance;
        private final int userId;
        private HandleMessage handleMessage;
        private JfxView view;

        // Private constructor
        private Controleur(HandleMessage handleMessage) {
            this.handleMessage = handleMessage;
            userId=1;
        }


        public void handleUserReply(String message) {
            // Store the message in the database
            handleMessage.reply(message,userId);

            // Process the message using HandleMessage
            handleMessage.iaRespond(message);

            displayResponse(handleMessage.iaRespond(message));


        }





}

