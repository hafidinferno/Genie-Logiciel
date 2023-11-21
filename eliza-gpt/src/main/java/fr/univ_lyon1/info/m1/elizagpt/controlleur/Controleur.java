package fr.univ_lyon1.info.m1.elizagpt.controlleur;

import java.util.ArrayList;
import java.util.List;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.HashAndMessage;
import javafx.scene.control.Label;


/** La classe controleur qui s'occupe de gérer les interactions entre la vue et le model. */
public final class Controleur  {
        private final HandleMessage handleMessage;
        private JfxView view;
        private final Dao dao;
        private final int userId;
        private final Label searchTextLabel=null;
        private String message;
        private final Integer hash;



        /**constructeur de la classe Controleur. */
        public Controleur(final String message, final HandleMessage handleMessage, final JfxView view) {
            this.message=message;
            this.handleMessage = handleMessage;
            this.view = view;
            dao = Dao.getInstance();
            userId = 1;

        }

    /** setter du constructeur
     * initialiser la valeur du message par le input de l'utilisateur
     * @param messaage
     */
    public void setMessage(String messaage){
            this.message=messaage;
        }

    /** getter du constructeur pour prendre la valeur du message
     * pour le traiter et le stocker dans la base de données
     * @return
     */
        public String getMessage(){
            return message;
        }


/** Stocker les messages dans la base de données. */


    public void stockMessage(String message){
        if (userId==1){
            dao.addMessage(message,userId);
        }

    }

    /** la methode handleUserReply s'occupe de stocker le message dans la Dao.
     * puis le traiter avec la fonction iaRespond de la classe handleMessage
     * puis l'afficher dans la vue.
     * @param message
     */

    public void handleUserReply(final String message) {


            // Traitement de message
            handleMessage.iaRespond(message);

            // Recherche dans la liste des messages
             ArrayList<HashAndMessage> searchResults = dao.search(message, searchTextLabel);

            // Stocker le message pour l'afficher par la vue .
            handleMessage.iaRespond(message);

            // Obtention de tous les messages
            ArrayList<HashAndMessage> allMessages = dao.getAllMessage();

            // Suppression des messages sélectionnés

                dao.deleteMessage(hash);




    }


    public void updateView(){
           view.handleUserReply(getMessage());

    }








}

