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

/**
 * The <code>NewTreatmentController</code> contains the entire logic of the NewTreatment view.
 * It determines which data is displayed and how to react to events.
 */
public class NewTreatmentController {
    // region Fields
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
    // endregion

    // region Methods
    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;
        createComboBoxData();
        showPatientData();
    }

    /**
     * Configures the labels to show the name of the current patient.
     */
    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    /**
     * Calls the create Method from the {@link TreatmentDAO}
     * @param treatment the {@link Treatment} to save on the database
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the caregiverCombobox with the surnames of all Caregivers
     */
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

    /**
     * searches a {@link Caregiver} from the caregiverList
     * @param surname the name to search for.
     * @return the {@link Caregiver} object with the right surname or null if no object was found.
     */
    private Caregiver searchInList(String surname){
        for (int i =0; i<this.caregiverList.size();i++){
            if(this.caregiverList.get(i).getSurname().equals(surname)){
                return this.caregiverList.get(i);
            }
        }
        return null;
    }

    // region Events

    /**
     * handles a add-click-event. Creates a Treatment.
     */
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

    /**
     * handles the selection of an element in the comboBox
     */
    @FXML
    public void handleComboBox(ActionEvent actionEvent) {
        String p = this.caregiverComboBox.getSelectionModel().getSelectedItem();
        caregiver = searchInList(p);
    }

    /**
     * handles a cancel-click-event
     */
    @FXML
    public void handleCancel(){
        stage.close();
    }
    // endregion
    // endregion

}