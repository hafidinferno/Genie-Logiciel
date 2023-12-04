package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import fr.univ_lyon1.info.m1.elizagpt.controleur.Controleur;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
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


/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
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
        controleur = new Controleur(this);

        final Pane input = createInputWidget();
        root.getChildren().add(input);

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


    /**
     * Cette fonction permet de reconstruire notre vue
     * lorsqu'un nouveau message a été envoyer ou plus
     * généralement quand il y un changement lié à notre base
     * de données.
     * @param messageArray Liste des message à afficher
     */
    public void refreshView(final ArrayList<DataMessage> messageArray) {
        dialog.getChildren().clear();
        for (DataMessage message : messageArray) {
            HBox hBox = new HBox();
            final Label label = new Label(message.getText());
            hBox.getChildren().add(label);
            if (message.isIa()) {
                label.setStyle(ELIZA_STYLE);
                hBox.setAlignment(Pos.BASELINE_LEFT);
            } else {
                label.setStyle(USER_STYLE);
                hBox.setAlignment(Pos.BASELINE_RIGHT);
            }
            dialog.getChildren().add(hBox);
            hBox.setOnMouseClicked(e -> controleur.deleteMessage(message.getHash()));
        }
    }

    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        searchText = new TextField();
        searchText.setOnAction(e -> {
            controleur.searchText(searchText.getText());
            searchText.setText("");
        });
        firstLine.getChildren().add(searchText);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            controleur.searchText(searchText.getText());
            searchText.setText("");
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> controleur.undoSearch());
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }

    /**
     * Fonction permettant de connaître la chaine de charactères
     * recherchée mais aussi l'état de la recherche.
     * @param searchingText état de la recherche.
     */
    public void changeSearchLabel(final String searchingText) {
        searchTextLabel.setText(searchingText);
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
