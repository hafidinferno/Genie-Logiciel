package fr.univ_lyon1.info.m1.elizagpt.controleur;


import java.util.ArrayList;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.HashAndMessage;



/** La classe controleur qui s'occupe de gérer les interactions entre la vue et le model. */
public final class Controleur  {

    private final HandleMessage handleMessage;
    private final JfxView view;
    private final Dao dao;




    /**constructeur de la classe Controleur. */
    public Controleur(final HandleMessage handleMessage, final JfxView view) {
        this.handleMessage = handleMessage;
        this.view = view;
        dao = Dao.getInstance();

    }



    /** la methode handleUserReply s'occupe de stocker le message dans la Dao.
     * puis le traiter avec la fonction iaRespond de la classe handleMessage
     * puis l'afficher dans la vue.
     * @param message
     */

    public void handleUserReply(final String message) {

        handleMessage.reply(message, 1);

        // Traitement de message
        handleMessage.iaRespond(message);

        //Prendre tous les messages de la base de données

        ArrayList<HashAndMessage> res = dao.getAllMessage();

        // rafraichir la vue

        view.refreshView(res);

    }


}

