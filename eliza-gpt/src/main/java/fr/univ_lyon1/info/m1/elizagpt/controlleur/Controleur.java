package fr.univ_lyon1.info.m1.elizagpt.controlleur;


import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;


/** La classe controleur qui s'occupe de gérer les interactions entre la vue et le model. */
public final class Controleur {
        private final HandleMessage handleMessage;
        private JfxView view;
        private final Dao dao;



        /**constructeur de la classe Controleur. */
        public Controleur(final HandleMessage handleMessage,JfxView view) {
            this.handleMessage = handleMessage;
            this.view=view;
            dao = Dao.getInstance();

        }

    /** la methode handleUserReply s'occupe de stocker le message dans la Dao.
     * puis le traiter avec la fonction iaRespond de la classe handleMessage
     * puis l'afficher dans la vue.
     * @param message
     */
    public void  handleUserReply(final String message) {
            // ajouter le message à la base de données
            handleMessage.reply(message, userId);

            // Traitement de message
            handleMessage.iaRespond(message);

            // notifier l'utilisateur qu'il y'a une recherche en cours
            handleMessage.searchText( String message, Label searchTextLabel)


    }


        public void updateView(String message){
                view.sendMessage(handleMessage.iaRespond(message));

        }





}

