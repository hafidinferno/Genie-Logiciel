package fr.univ_lyon1.info.m1.elizagpt.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import fr.univ_lyon1.info.m1.elizagpt.controleur.Controleur;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy.CompleteWordSearch;
import fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy.RegexSearch;
import fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy.SubString;
import fr.univ_lyon1.info.m1.elizagpt.model.searchStrategy.TypeRecherche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private final Image trashIcon;
    private final Controleur controleur;
    /**
     * Create the main view of the application.
     */
    public JfxView(final Stage stage, final int width, final int height)
            throws FileNotFoundException {
        stage.setTitle("Eliza GPT");
        controleur = Controleur.getInstance(this);

        final BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #000000");
        trashIcon = new Image(new FileInputStream("data/trashIcon.png"));


        final Pane search = createSearchWidget();


        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);
        dialog.setStyle("-fx-background-color: #282828");
        dialogScroll.setContent(dialog);
        // scroll to bottom by default:
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());

        dialogScroll.setFitToWidth(true);


        final Pane input = createInputWidget();


        // Everything's ready: add it to the scene and display it
        root.setTop(search);
        root.setCenter(dialog);
        root.setBottom(input);
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        controleur.syncVue();
        stage.show();
    }

    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: "
                                + "linear-gradient(#0896d3 30%, #1160cc 70%); "
            + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color:"
                                + " linear-gradient(#a356ee 30%, #6d00ea 70%);"
            + BASE_STYLE;


    /**
     * Cette fonction permet de reconstruire notre vue
     * lorsqu'un nouveau message a été envoyer ou plus
     * généralement quand il y a un changement lié à notre base
     * de données.
     * @param messageArray Liste des messages à afficher
     */
    public void refreshView(final ArrayList<DataMessage> messageArray) {
        dialog.getChildren().clear();
        for (DataMessage message : messageArray) {
            HBox motherHBox = new HBox();
            HBox hBox = new HBox();
            final Label label = new Label(message.getText());
            label.setTextFill(Color.WHITE);
            Button deleteMessageButton = new Button();
            ImageView icon = new ImageView(trashIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            deleteMessageButton.setGraphic(icon);
            deleteMessageButton.setOnMouseClicked(e -> controleur.deleteMessage(message.getHash()));
            if (message.isIa()) {
                hBox.setStyle(ELIZA_STYLE);
                motherHBox.setAlignment(Pos.BASELINE_LEFT);
            } else {
                hBox.setStyle(USER_STYLE);
                motherHBox.setAlignment(Pos.BASELINE_RIGHT);
            }
            hBox.getChildren().add(label);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().add(deleteMessageButton);
            motherHBox.getChildren().add(hBox);
            dialog.getChildren().add(motherHBox);
        }
    }

    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);

        searchText = new TextField();
        searchText.setStyle("-fx-background-color: #595959;"
                + "-fx-text-fill: #FFFFFF;");

        final Button send = new Button("Search");
        ComboBox<TypeRecherche> listeDeroulante = createListDeroulante();
        searchText.setOnAction(e -> {
            controleur.searchText(searchText.getText());
            searchText.setText("");
        });
        firstLine.getChildren().addAll(searchText, listeDeroulante);
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
     * Fonction permettant de connaître la chaine de caractères
     * recherchée, mais aussi l'état de la recherche.
     * @param searchingText état de la recherche.
     */
    public void changeSearchLabel(final String searchingText) {
        searchTextLabel.setText(searchingText);
        searchTextLabel.setTextFill(Color.WHITE);
    }

    private ComboBox<TypeRecherche> createListDeroulante() {
        ObservableList<TypeRecherche> listSearchStrategy = FXCollections.observableArrayList(
                new RegexSearch(),
                new SubString(),
                new CompleteWordSearch()
        );

        ComboBox<TypeRecherche> listeDeroulante = new ComboBox<>();
        listeDeroulante.setItems(listSearchStrategy);
        listeDeroulante.getSelectionModel().select(1);
        listeDeroulante.valueProperty().addListener(
                (ov, oldStrategy, newStrategy) ->
                        controleur.setStrategy(newStrategy));
        controleur.setStrategy(listSearchStrategy.get(1));

        return listeDeroulante;
    }




    private Pane createInputWidget() throws FileNotFoundException {
        Image imageImp = new Image(new FileInputStream("data/send.png"));
        ImageView sendIcon = new ImageView(imageImp);
        sendIcon.setFitWidth(15);
        sendIcon.setFitHeight(15);
        final Pane input = new HBox();
        final HBox line = new HBox();


        text = new TextField();
        text.setStyle("-fx-background-color: #595959;"
                + "-fx-text-fill: #FFFFFF;");

        text.setOnAction(e -> {
            controleur.handleUserReply(text.getText());
            text.setText("");
        });
        final Button send = new Button();
        send.setGraphic(sendIcon);
        send.setOnAction(e -> {
            controleur.handleUserReply(text.getText());
            text.setText("");
        });

        line.getChildren().addAll(text, send);
        line.setAlignment(Pos.BASELINE_LEFT);
        input.getChildren().addAll(line);
        return input;
    }
}
