package fr.univ_lyon1.info.m1.elizagpt.controleur;


import java.util.ArrayList;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;


/** La classe controleur qui s'occupe de gérer les interactions entre la vue et le model. */
public final class Controleur  {
    private final HandleMessage handleMessage;
    private final ArrayList<JfxView> views;
    private static Dao dao;
    private static Controleur instance;


    /**
     * Constructeur de la classe Controleur.
     * @param view Vue associée à ce controleur.
     */
    private Controleur(final JfxView view) {
        this.handleMessage = new HandleMessage();
        this.views = new ArrayList<>();
        this.views.add(view);
        dao = Dao.getInstance();

    }

    public static void setDao(final Dao daoMock) {
        dao = daoMock;
    }





    /**
     * La fonciton permet d'obtenir une instance du controleur.
     * Si aucun controleur n'était instancié, alors il est créé.
     * Sinon, la vu passé en paramètre est simplement ajoutée à une liste de vue.
     * @param view nouvelle vue ajoutée à la liste des vues du controleur.
     * @return une instance de controleur
     */
    public static Controleur getInstance(final JfxView view) {
        if (instance != null) {
            instance.views.add(view);
        } else {
            instance = new Controleur(view);
        }
        ArrayList<DataMessage> res = dao.getAllMessage();
        for (JfxView viewIterate : instance.views) {
            viewIterate.refreshView(res);
        }
        return instance;
    }


    /** la methode handleUserReply s'occupe de stocker le message dans la Dao.
     * puis le traiter avec la fonction iaRespond de la classe handleMessage
     * puis l'afficher dans la vue.
     * @param text message à traiter
     */

    public void handleUserReply(final String text) {
        if (!(text == null) && !text.isEmpty()) {
            handleMessage.reply(text, false);
            handleMessage.iaResponse(text);
            ArrayList<DataMessage> res = dao.getAllMessage();
            for (JfxView viewIterate : instance.views) {
                viewIterate.refreshView(res);
            }
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
        for (JfxView viewIterate : instance.views) {
            viewIterate.refreshView(res);
        }
    }

    /**
     * La fonction envoie à la vue les messages correspondant
     * à la recherche en cours. De plus elle permet ce qui est
     * actuellement cherché.
     * @param text chaine de charactères recherchée
     */
    public void searchText(final String text) {
        if (text == null || text.isEmpty()) {
            for (JfxView viewIterate : instance.views) {
                viewIterate.changeSearchLabel("No active search.");
            }
        } else {
            for (JfxView viewIterate : instance.views) {
                viewIterate.changeSearchLabel("Searching for: " + text);
            }
        }
        ArrayList<DataMessage> res = handleMessage.searchText(text);
        for (JfxView viewIterate : instance.views) {
            viewIterate.refreshView(res);
        }
    }

    /**
     * permet de rafficher tous les messages de la base de données?
     */
    public void undoSearch() {
        for (JfxView viewIterate : instance.views) {
            viewIterate.refreshView(dao.getAllMessage());
        }
    }


}

