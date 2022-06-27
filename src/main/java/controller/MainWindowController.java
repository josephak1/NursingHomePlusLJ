package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

/**
 * The <code>MainWindowController</code> contains the entire logic of the main window.
 * It determines which data is displayed and how to react to events.
 */
public class MainWindowController {

    // region Fields
    @FXML
    private BorderPane mainBorderPane;
    // endregion

    // region Events

    /**
     * handles the patients-click-event
     * @param e
     */
    @FXML
    private void handleShowAllPatient(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllPatientController controller = loader.getController();
    }

    /**
     * handles the caregiver-click-event
     * @param actionEvent
     */
    @FXML
    public void handleShowAllCaregiver(ActionEvent actionEvent)
    {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllCaregiverController controller = loader.getController();
    }

    /**
     * handles the treatments-click-event
     * @param e
     */
    @FXML
    private void handleShowAllTreatments(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllTreatmentController controller = loader.getController();
    }

    // endregion
}
