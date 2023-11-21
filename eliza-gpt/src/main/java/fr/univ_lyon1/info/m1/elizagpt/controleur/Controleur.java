package fr.univ_lyon1.info.m1.elizagpt.controleur;


import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;


/** La classe controleur qui s'occupe de relier entre la vue et le model. */
public final class Controleur {
    private static Controleur instance;
    private final HandleMessage handleMessage;
    private JfxView view;

    /**
     * Constructeur de la classe Controleur.
     * @param view Vue associée à ce controleur.
     */
    public Controleur(final JfxView view) {
        this.handleMessage = new HandleMessage();
        this.view = view;
    }

    /** la methode handleUserREply s'occupe de stocker le message dans la Dao.
     * puis le traiter avec la fonction iaRespond de la classe handleMessage
     * puis l'afficher dans la vue.
     * @param message
     */
    public void handleUserReply(final String message) {
        // Store the message in the database
        handleMessage.reply(message, 1);

        // Process the message using HandleMessage
        handleMessage.iaRespond(message);
    }
}

