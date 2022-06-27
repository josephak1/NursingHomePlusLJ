package controller;

import datastorage.PatientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Patient;
import model.ApplicationSession;
import utils.DateConverter;
import datastorage.DAOFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view.
 * It determines which data is displayed and how to react to events.
 */
public class AllPatientController {

    // region Fields
    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TableColumn<Patient, Integer> colID;
    @FXML
    private TableColumn<Patient, String> colFirstName;
    @FXML
    private TableColumn<Patient, String> colSurname;
    @FXML
    private TableColumn<Patient, String> colDateOfBirth;
    @FXML
    private TableColumn<Patient, String> colCareLevel;
    @FXML
    private TableColumn<Patient, String> colRoom;
    @FXML
    private TableColumn<Patient, Integer> colCid;

    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtBirthday;
    @FXML
    TextField txtCarelevel;
    @FXML
    TextField txtRoom;

    private ObservableList<Patient> tableviewContent = FXCollections.observableArrayList();
    private PatientDAO dao;

    // endregion

    // region Methods

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        if (ApplicationSession.getSession().getActiveUser().getIsAdmin()) {
            readAllAndShowInTableView();
        }
        else{
            readAllActiveAndShowInTableView();
        }

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colID.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("pid"));
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
        this.colSurname.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
        this.colDateOfBirth.setCellValueFactory(new PropertyValueFactory<Patient, String>("dateOfBirth"));
        this.colCareLevel.setCellValueFactory(new PropertyValueFactory<Patient, String>("careLevel"));
        this.colRoom.setCellValueFactory(new PropertyValueFactory<Patient, String>("roomnumber"));

        if (ApplicationSession.getSession().getActiveUser().getIsAdmin()) {
            //CellFactory zum Schreiben innerhalb der Tabelle
            this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
            this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
            this.colDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());
            this.colCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());
            this.colRoom.setCellFactory(TextFieldTableCell.forTableColumn());
        }
        else{
            this.btnDelete.setDisable(true);
        }
        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * updates a patient by calling the update-Method in the {@link PatientDAO}
     * @param t row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link PatientDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        try {
            allPatients = dao.readAll();
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readActive in {@link PatientDAO} and shows patients in the table
     */
    private void readAllActiveAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        try {
            allPatients = dao.readActive();
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtBirthday.clear();
        this.txtCarelevel.clear();
        this.txtRoom.clear();
    }

    // region Events

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new birthdate value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setDateOfBirth(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new carelevel value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setCareLevel(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new roomnumber value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditRoomnumber(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setRoomnumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles a archive-click-event. Calls the archive method in the {@link PatientDAO}s
     */
    @FXML
    public void handleArchive() {
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            String date = LocalDate.now().toString();

            dao.archiveById(selectedItem.getPid(), date);
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a add-click-event. Creates a patient and calls the create method in the {@link PatientDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        String birthday = this.txtBirthday.getText();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        String carelevel = this.txtCarelevel.getText();
        String room = this.txtRoom.getText();
        try {
            Patient p = new Patient(firstname, surname, date, carelevel, room, null);
            dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (ApplicationSession.getSession().getActiveUser().getIsAdmin()){
            readAllAndShowInTableView();
        }
        else{
            readAllActiveAndShowInTableView();
        }

        clearTextfields();
    }

    // endregion
    // endregion

}