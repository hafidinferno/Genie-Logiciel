package fr.univ_lyon1.info.m1.elizagpt.controleur;


import java.util.ArrayList;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchFunction;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;


/**
 * La classe Controleur s'occupe de gérer les
 * interactions entre la vue et le model.
 * */
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

    /**
     * La fonction permet d'obtenir une instance du controleur.
     * Si aucun controleur n'était instancié, alors il est créé.
     * Sinon, la vue passée en paramètre est simplement ajoutée à une liste de vue.
     * @param view nouvelle vue ajoutée à la liste des vues du controleur.
     * @return une instance de controleur
     */
    public static Controleur getInstance(final JfxView view) {
        if (instance != null) {
            instance.views.add(view);
        } else {
            instance = new Controleur(view);
        }
        return instance;
    }


    /**
     * La methode handleUserReply s'occupe de stocker le message dans la Dao.
     * Puis le traiter avec la fonction iaRespond de la classe handleMessage
     * ensuite l'afficher dans la vue.
     * @param text message à traiter
     */
    public void handleUserReply(final String text) {
        if (!(text == null) && !text.isEmpty()) {
            handleMessage.reply(text, false);
            handleMessage.iaResponse(text);
            syncVue();
        }
    }

    /**
     * La fonction permet de supprimer un message de la
     * vue ainsi que de la base de donnée.
     * @param hash hash du message à supprimer
     */
    public void deleteMessage(final Integer hash) {
        dao.deleteMessage(hash);
        syncVue();
    }

    /**
     * La fonction envoie à la vue les messages correspondant
     * à la recherche en cours. De plus elle permet ce qui est
     * actuellement cherché.
     * @param text sous chaine de caractères recherchée
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
        ArrayList<DataMessage> res = handleMessage.search(text);
        for (JfxView viewIterate : instance.views) {
            viewIterate.refreshView(res);
        }
    }

    /**
     * Permet d'afficher à nouveau tous les
     * messages de la base de données
     * en arrêtant la recherche.
     */
    public void undoSearch() {
        for (JfxView view : views) {
            view.changeSearchLabel("");
        }
        syncVue();
    }

    /**
     * Fonction permettant de modifier la stratégie de recherche en cours.
     * @param strategy Fonction de stratégie de recherche qui sera utilisée.
     */
    public void setStrategy(final SearchFunction strategy) {
        dao.setSearchStrategy(strategy);
    }

    /**
     * La fonction permet de rafraîchir les vues
     * en affichant l'ensemble des messages.
     */
    public void syncVue() {
        ArrayList<DataMessage> res = dao.getAllMessage();
        for (JfxView view : views) {
            view.refreshView(res);
        }
    }

}

