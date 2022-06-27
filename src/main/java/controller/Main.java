package controller;

import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Patient;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * the Main clss containing the logic for initializing the Programm.
 */
public class Main extends Application {

    // region Fields
    private Stage primaryStage;
    // endregion

    // region Methods

    /**
     * start method called by the derived {@link Application} class
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    /**
     * Loads the first window to be displayed and configures the primaryStage
     */
    public void mainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setTitle("NHPlus");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();

            this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    ConnectionBuilder.closeConnection();
                    Platform.exit();
                    System.exit(0);
                }
            });
            checkExpiredPatients();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * checks if any archived {@link Patient} in the database has exceeded the legal time for being saved.
     * Deletes affected rows if necessary.
     */
    public void checkExpiredPatients(){
        PatientDAO pDao = DAOFactory.getDAOFactory().createPatientDAO();
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Patient> allPatients;
        LocalDate currentDate = LocalDate.now();
        try {
            allPatients = pDao.readAll();
            for (Patient p : allPatients) {
                LocalDate from = p.getArchived();
                if (from != null){
                    long years = ChronoUnit.YEARS.between(p.getArchived(), currentDate);
                    if (years >= 10){
                        tDao.deleteByPid(p.getPid());
                        pDao.deleteById(p.getPid());
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * start of the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    // endregion
}