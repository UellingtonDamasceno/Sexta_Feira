package controllers;

import facades.FacadeBackend;
import facades.FacadeFrontEnd;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class DashboardController implements Initializable {

    @FXML
    private TextArea txtAreaInfoHero;
    @FXML
    private VBox vboxContent;
    @FXML
    private TextArea txtAreaInfoSelectedHero;
    @FXML
    private TableColumn<?, ?> tcHero;
    @FXML
    private TableColumn<?, ?> tcSimilarity;
    @FXML
    private TextField txtGetHero;
    @FXML
    private TextField txtAcCharacteristc;
    @FXML
    private ComboBox<?> comboBoxAlgorithms;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FacadeFrontEnd facade = FacadeFrontEnd.getInstance();
        FacadeBackend facadeb = FacadeBackend.getInstance();
    }

    @FXML
    private void addNewCharacteristc(ActionEvent event) {
    }

    @FXML
    private void showInfoCompare(ActionEvent event) {
    }

}
