package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.ProgrammSession;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The <code>TreatmentController</code> contains the entire logic of the Treatment view.
 * It determines which data is displayed and how to react to events.
 */
public class TreatmentController {
    // region Fields
    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblCarelevel;
    @FXML
    private Label lblCaregiver;
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
    private Button btnChange;
    @FXML
    private Button btnCancel;

    private AllTreatmentController controller;
    private Stage stage;
    private Patient patient;
    private Caregiver caregiver;
    private Treatment treatment;
    // endregion

    // region Methods

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     * @param controller the currently active {@link AllTreatmentController}
     * @param stage the currently active {@link Stage}
     * @param treatment the {@link Treatment} to be displayed
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller= controller;
        PatientDAO pDao = DAOFactory.getDAOFactory().createPatientDAO();
        CaregiverDAO cDao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.caregiver = cDao.read((int) treatment.getCid());
            this.treatment = treatment;
            showData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * shows the data of the current {@link Treatment}
     */
    private void showData(){
        this.lblPatientName.setText(patient.getSurname()+", "+patient.getFirstName());
        this.lblCarelevel.setText(patient.getCareLevel());
        this.lblCaregiver.setText(caregiver.getSurname());
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate());
        this.datepicker.setValue(date);
        this.txtBegin.setText(this.treatment.getBegin());
        this.txtEnd.setText(this.treatment.getEnd());
        this.txtDescription.setText(this.treatment.getDescription());
        this.taRemarks.setText(this.treatment.getRemarks());
    }

    // region Events

    /**
     * handles the change-click-event
     */
    @FXML
    public void handleChange(){
        this.treatment.setDate(this.datepicker.getValue().toString());
        this.treatment.setBegin(txtBegin.getText());
        this.treatment.setEnd(txtEnd.getText());
        this.treatment.setDescription(txtDescription.getText());
        this.treatment.setRemarks(taRemarks.getText());
        doUpdate();
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * calls the update Method of the {@link TreatmentDAO}
     */
    private void doUpdate(){
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.update(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles the cancel-click-event
     */
    @FXML
    public void handleCancel(){
        stage.close();
    }

    // endregion
    // endregion

}