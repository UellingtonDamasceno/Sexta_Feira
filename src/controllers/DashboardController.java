package controllers;

import facades.FacadeBackend;
import facades.FacadeFrontEnd;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class DashboardController implements Initializable {

    @FXML   private TextArea txtAreaInfoHero;
    @FXML   private VBox vboxContent;
    @FXML   private TextArea txtAreaInfoSelectedHero;
    @FXML   private TableColumn<?, ?> tcHero;
    @FXML   private TableColumn<?, ?> tcSimilarity;
    @FXML   private TextField txtGetHero;
    @FXML   private TextField txtAcCharacteristc;
    @FXML   private ComboBox<String> comboBoxAlgorithms;
    @FXML   private TableView<?> tableResultado;
    @FXML   private Slider slider;
    @FXML   private Label value;
    @FXML   private Button add;
    @FXML   private Button calculate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FacadeFrontEnd facade = FacadeFrontEnd.getInstance();
        FacadeBackend facadeb = FacadeBackend.getInstance();
        initComboBox();
        initTextFieldsAC();
        add.setDisable(true);
        calculate.setDisable(true);
    }

    @FXML
    private void addNewCharacteristc(ActionEvent event) {
        String caracteristic = txtAcCharacteristc.getText();
        //facade.addNewCharacteristc(characteristic);
    }

    @FXML
    private void showInfoCompare(ActionEvent event) {
    }

    @FXML
    private void changeTheValue(MouseEvent event) {
        value.setText(String.valueOf((int)slider.getValue()));
    }

    private void initComboBox(){
        /*
        ObservableList<String> ol = facade.getAlgorithmsPossibilities();
        comboBoxAlgorithms.setItems(ol)
        */
    }
    
    private void initTextFieldsAC(){
        String[] heroes = null;
        String[] superPower = null;
        //String[] heroes = new String(facade.getPossibleCharacterSuggestions());
        //String[] superPower = new String(facade.getPossibleSuperPowerSuggestions());
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
        /*
        Character c = getCharacter(txtGetHero)/
        
        if(c != null){
            txtAreaInfoHero.setText(c.getInfos);
        }
        add.setDisable(false);
        calculate.setDisable(false);        
        */
    }
}
