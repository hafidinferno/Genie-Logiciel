package fr.univ_lyon1.info.m1.elizagpt.view;
import fr.univ_lyon1.info.m1.elizagpt.model.DataMessage;


import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import java.util.ArrayList;

class JfxViewTest extends ApplicationTest {

    private JfxView jfxView;

    @Override
    public void start(final Stage stage) {
        jfxView = new JfxView(stage, 400, 400); // Initialize with some size
    }

    @Test
    void testRefreshView() {
        ArrayList<DataMessage> messages = new ArrayList<>();
        messages.add(new DataMessage("User message", false));
        messages.add(new DataMessage("AI response", true));

        interact(() -> jfxView.refreshView(messages));


    }


    @Test
    void changeSearchLabel() {

    }
}
