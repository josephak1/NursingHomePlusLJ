package controller;

import datastorage.ConnectionBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Caregiver;
import model.ProgrammSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginViewController {

    @FXML
    TextField txfUserName;
    @FXML
    PasswordField txfUserPassword;

    public void handleOk(ActionEvent actionEvent) {
        String userName = txfUserName.getText();
        String userPassword = txfUserPassword.getText();

        try {
            Connection conn = ConnectionBuilder.getConnection();
            Statement st = conn.createStatement();

            String exSt = String.format("SELECT * FROM caregiver WHERE username = '%s' AND password = '%s'", userName, userPassword);
            ResultSet set = st.executeQuery(exSt);
            Caregiver c;
            if(set.next()){
                c = new Caregiver(set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getBoolean(6),
                        set.getString(7));

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
                BorderPane pane = loader.load();
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(pane));
                ProgrammSession.getSession().setActiveUser(c);
            }

            String l = "hi";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
