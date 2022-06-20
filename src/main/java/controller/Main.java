package controller;

import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
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

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    public void mainWindow() {
        try {
            // FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));
            // BorderPane pane = loader.load();
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
    public void checkExpiredPatients(){
        PatientDAO dao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        LocalDate currentDate = LocalDate.now();
        try {
            allPatients = dao.readAll();
            for (Patient p : allPatients) {
                LocalDate from = p.getArchived();
                if (from != null){
                    long years = ChronoUnit.YEARS.between(p.getArchived(), currentDate);
                    if (years >= 10){
                        dao.deleteById(p.getPid());
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}