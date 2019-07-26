package controllers;

import exceptions.CharacterNotFoundException;
import facades.FacadeBackend;
import facades.FacadeFrontEnd;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;
import util.Settings.Algorithms;
import weka.core.Instance;

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
    private ComboBox<Algorithms> comboBoxAlgorithms;
    @FXML
    private TableView<?> tableResultado;
    @FXML
    private Slider slider;
    @FXML
    private Label value;
    @FXML
    private Button add;
    @FXML
    private Button calculate;

    private FacadeFrontEnd facade;
    private FacadeBackend facadeb;

    String[] heroes;
    String[] superPower;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.facadeb = FacadeBackend.getInstance();

        heroes = facadeb.getPossibleCharacterSuggestions();
        superPower = facadeb.getPossibleSuperPowerSuggestions();

        initComboBox();
        initTextFieldsAC();

        add.setDisable(true);
        calculate.setDisable(true);
    }

    @FXML
    private void addNewCharacteristc(ActionEvent event) {
        String characteristic = txtAcCharacteristc.getText();
//        vboxContent.getChildren().add(getCaracteristic(characteristic));
    }

    @FXML
    private void showInfoCompare(ActionEvent event) {
    }

    @FXML
    private void changeTheValue(MouseEvent event) {
        value.setText(String.valueOf((int) slider.getValue()));
    }

    private void initComboBox() {
        ObservableList<Algorithms> ol = facadeb.getAlgorithmsPossibilities();
        comboBoxAlgorithms.setItems(ol);
    }

    private void initTextFieldsAC() {
        TextFields.bindAutoCompletion(txtGetHero, heroes);
        TextFields.bindAutoCompletion(txtAcCharacteristc, superPower);
    }

    @FXML
    private void calculate(ActionEvent event) {
        ObservableList<?> generatedList;

        /*
        facade.calculate();
        ObservableList<?> generatedList = getObservableListResults();
        if(generated != null){
            tableResultado.setItems(generated)
        }
        
         */
        //Tem que mudar o termo genérico ( ? ) tanto do observable quanto da tableView
        //Indicação:
        //Fazer uma classe Results com um obj e um Double score
        /*
        //Como transformar os objetos Results em ObservableList:
        //pode ser criada com qualquer lista
        public ObervableList<Results> getResults(){
            ArrayList<Results> results = new ArrayList();
            // adicione os objetos a esse array de resultados
            return  FXColections.observableArrayList(results);
        }
        => e ta pronto o sorvetinho
        
        =>Esse método também equivale para o ComboBOx
         */
        //ObservableList<Results> generatedList = facade.calculate();
        //Mudar lá em cima para:
        //    @FXML   private TableView<Results> tableResultado;
    }

    @FXML
    private void loadCharacter(ActionEvent event) {
        Instance character;
        try {
            character = facadeb.getCharacter(txtGetHero.getText());
            txtAreaInfoHero.setText(character.toString());
            add.setDisable(false);
            calculate.setDisable(false);

        } catch (CharacterNotFoundException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
