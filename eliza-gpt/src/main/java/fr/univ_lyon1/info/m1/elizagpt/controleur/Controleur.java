package fr.univ_lyon1.info.m1.elizagpt.controleur;


import java.util.ArrayList;

import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao;


/** La classe controleur qui s'occupe de gérer les interactions entre la vue et le model. */
public final class Controleur  {

    private final HandleMessage handleMessage;
    private final JfxView view;
    private final Dao dao;




    /**constructeur de la classe Controleur. */
    public Controleur(final JfxView view) {
        this.handleMessage = new HandleMessage();
        this.view = view;
        dao = Dao.getInstance();
        ArrayList<DataMessage> res = dao.getAllMessage();
        this.view.refreshView(res);
    }



    /** la methode handleUserReply s'occupe de stocker le message dans la Dao.
     * puis le traiter avec la fonction iaRespond de la classe handleMessage
     * puis l'afficher dans la vue.
     * @param text message à traiter
     */

    public void handleUserReply(final String text) {
        if (!(text == null) && !text.isEmpty()) {
            handleMessage.reply(text, false);
            handleMessage.iaRespond(text);
            ArrayList<DataMessage> res = dao.getAllMessage();
            view.refreshView(res);
        }
    }

    /**
     * La fonction permet de supprimer un message de la
     * vue ainsi que de la base de donnée.
     * @param hash hash du message à supprimer
     */
    public void deleteMessage(final Integer hash) {
        dao.deleteMessage(hash);
        ArrayList<DataMessage> res = dao.getAllMessage();
        view.refreshView(res);
    }

    /**
     * La fonction envoie à la vue les messages correspondant
     * à la recherche en cours. De plus elle permet ce qui est
     * actuellement cherché.
     * @param text chaine de charactères recherchée
     */
    public void searchText(final String text) {
        if (text == null || text.isEmpty()) {
            view.changeSearchLabel("No active search");
        } else {
            view.changeSearchLabel("Searching for: " + text);
        }
        ArrayList<DataMessage> res = handleMessage.searchText(text);
        view.refreshView(res);
    }

    /**
     * permet de rafficher tous les messages de la base de données?
     */
    public void undoSearch() {
        view.refreshView(dao.getAllMessage());
    }
}

