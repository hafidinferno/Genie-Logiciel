package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import fr.univ_lyon1.info.m1.elizagpt.controleur.Controleur;
import fr.univ_lyon1.info.m1.elizagpt.model.HashAndMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;


/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private final VBox dialog;
    private TextField text = null;

    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageProcessor processor = new MessageProcessor();
    private final Random random = new Random();
    private final Controleur controleur;
    /**
     * Create the main view of the application.
     */
        // TODO: style error in the following line. Check that checkstyle finds it, and then fix it.
    public JfxView(final Stage stage, final int width, final int height) {
        stage.setTitle("Eliza GPT");

        final VBox root = new VBox(10);

        final Pane search = createSearchWidget();
        root.getChildren().add(search);

        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);
        dialogScroll.setContent(dialog);
        // scroll to bottom by default:
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());
        root.getChildren().add(dialogScroll);
        dialogScroll.setFitToWidth(true);

        final Pane input = createInputWidget();
        root.getChildren().add(input);
        replyToUser("Bonjour");

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
    }

    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    //replyToUser doit se transformer en ``createDialogBox(final String text)``
    // et appeler le controlleur pour ajouter le message à la dao
    //same code that the sendMessage function. We have to simplify both functions.
    private void replyToUser(final String text) {
        controleur.handleUserReply(text);
<<<<<<< HEAD
        HBox hBox = new HBox();
=======

        /*HBox hBox = new HBox();
>>>>>>> 0bf42804e99869aca1a58b7dcfff8ae90baebe48
        final Label label = new Label(text);
        hBox.getChildren().add(label);
        label.setStyle(USER_STYLE);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        dialog.getChildren().add(hBox);
        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
        });*/
        // TODO: a click on this hbox should delete the message.
    }

<<<<<<< HEAD
    private void sendMessage(final String text) {

        HBox hBox = new HBox();
        final Label label = new Label(text);
        hBox.getChildren().add(label);
        label.setStyle(ELIZA_STYLE);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        dialog.getChildren().add(hBox);
        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
        });

        String normalizedText = processor.normalize(text);

        Pattern pattern;
        Matcher matcher;

        // First, try to answer specifically to what the user said
        pattern = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            replyToUser("Bonjour " + matcher.group(1) + ".");
            return;
        }
        pattern = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            if (getName() != null) {
                replyToUser("Votre nom est " + getName() + ".");
            } else {
                replyToUser("Je ne connais pas votre nom.");
            }
            return;
        }
        pattern = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            replyToUser("Le plus " + matcher.group(1)
                    + " est bien sûr votre enseignant de MIF01 !");
            return;
        }
        pattern = Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            final String startQuestion = processor.pickRandom(new String[]{
                    "Pourquoi dites-vous que ",
                    "Pourquoi pensez-vous que ",
                    "Êtes-vous sûr que ",
            });
            replyToUser(startQuestion + processor.firstToSecondPerson(matcher.group(1)) + " ?");
            return;
        }


        pattern = Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            Random random = new Random();

            boolean randomBoolean = random.nextBoolean();

            if (randomBoolean) {
                replyToUser("Je vous renvoie la question");
            } else {
                replyToUser("Ici c'est moi qui pose des questions");
            }
            return;
        }


        // Nothing clever to say, answer randomly
        if (random.nextBoolean()) {
            replyToUser("Il faut beau aujourd'hui, vous ne trouvez pas ?");
            return;
        }
        if (random.nextBoolean()) {
            replyToUser("Je ne comprends pas.");
            return;
        }
        if (random.nextBoolean()) {
            replyToUser("Hmmm, hmm ...");
            return;
        }
        // Default answer
        if (getName() != null) {
            replyToUser("Qu'est-ce qui vous fait dire cela, " + getName() + " ?");
        } else {
            replyToUser("Qu'est-ce qui vous fait dire cela ?");
        }
    }

=======
>>>>>>> 0bf42804e99869aca1a58b7dcfff8ae90baebe48
    /**
     * Cette fonction permet de reconstruire notre vue
     * lorsqu'un nouveau message a été envoyer ou plus
     * généralement quand il y un changement lié à notre base
     * de données.
     * @param messageArray
     */
    public void refreshView(final ArrayList<HashAndMessage> messageArray) {
        dialog.getChildren().clear();
        boolean isIa = true; //à modifier
        for (HashAndMessage message : messageArray) {
            HBox hBox = new HBox();
            final Label label = new Label(message.getText());
            hBox.getChildren().add(label);
            if (isIa) {
                label.setStyle(ELIZA_STYLE);
            } else {
                label.setStyle(USER_STYLE);
            }
            isIa = !isIa;
            hBox.setAlignment(Pos.BASELINE_RIGHT);
            dialog.getChildren().add(hBox);
            hBox.setOnMouseClicked(e -> {
                //Controleur.deleteMessage(message.getHash());
                //À rremplacer par un callback qui appel le controlleur
                // et qui effectue bien l'action souhaitée
                //ici : supprimer un message de notre appli et de la vue.
            });
        }
    }

    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        searchText = new TextField();
        searchText.setOnAction(e -> {
            //controleur.searchText(searchText);
        });
        firstLine.getChildren().add(searchText);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            //controleur.searchText(searchText);
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            throw new UnsupportedOperationException("TODO: implement undo for search");
        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }


    private Pane createInputWidget() {
        final Pane input = new HBox();
        text = new TextField();
        text.setOnAction(e -> {
            controleur.handleUserReply(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            controleur.handleUserReply(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }
}
