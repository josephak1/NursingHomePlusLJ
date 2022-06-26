package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTreatmentController {
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblFirstname;
    @FXML
    private TextField txtBegin;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextArea taRemarks;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ComboBox<String> caregiverComboBox;

    private AllTreatmentController controller;
    private Patient patient;

    private Caregiver caregiver;
    private Stage stage;
    private ArrayList<Caregiver> caregiverList;
    private ObservableList<String> myComboBoxData =
            FXCollections.observableArrayList();

    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;
        createComboBoxData();
        showPatientData();
    }

    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    @FXML
    public void handleAdd(){
        LocalDate date = this.datepicker.getValue();
        String s_begin = txtBegin.getText();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), caregiver.getCid(),
                date, begin, end, description, remarks);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createComboBoxData(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregiverList) {
                this.myComboBoxData.add(caregiver.getSurname());
            }
            caregiverComboBox.setItems(myComboBoxData);
            caregiverComboBox.getSelectionModel().select(0);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private Caregiver searchInList(String surname){
        for (int i =0; i<this.caregiverList.size();i++){
            if(this.caregiverList.get(i).getSurname().equals(surname)){
                return this.caregiverList.get(i);
            }
        }
        return null;
    }

    public void handleComboBox(ActionEvent actionEvent) {
        String p = this.caregiverComboBox.getSelectionModel().getSelectedItem();
        caregiver = searchInList(p);
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }


}