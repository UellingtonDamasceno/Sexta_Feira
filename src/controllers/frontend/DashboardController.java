package controllers.frontend;

import exceptions.CharacterNotFoundException;
import facades.FacadeBackend;
import facades.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.bean.Dice;
import model.bean.Jaccard;
import model.bean.Result;
import model.bean.SMC;
import org.controlsfx.control.textfield.TextFields;
import util.Settings.Algorithms;
import weka.core.Instance;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class DashboardController implements Initializable {

    @FXML    private TableColumn<Result, String> tcHero;
    @FXML    private TableColumn<Result, String> tcSimilarity;
    @FXML    private TextField txtGetHero;
    @FXML    private TextField txtAcCharacteristc;
    @FXML    private ComboBox<Algorithms> comboBoxAlgorithms;
    @FXML    private TableView<Result> tableResultado;
    @FXML    private Slider slider;
    @FXML    private TextField txtResultsNumber;
    @FXML    private Button calculate;
    @FXML    private Label labelInfos;

    @FXML    private Label selectedCName;
    @FXML    private Label selectedCCha;

    private FacadeBackend facadeb;
    private FacadeFrontend facadef;
    
    private String[] heroes;
    private String[] superPower;
    
    @FXML   private Tooltip tooltipAlgorithm;
    
    @FXML   private VBox vbMatch;
    @FXML   private Label char1;
    @FXML   private Label char2;
    
    @FXML   private Label onlyCH1;
    @FXML   private Label matchChs;
    @FXML   private Label onlyCh2;
    
    @FXML   private ComboBox<String> analisysTypeCombB;
    @FXML   private VBox predictionContainer;
    @FXML   private HBox similarityContainer;

    @FXML   private Button go;
    @FXML   private TextField preChar;
    
    @FXML   private VBox infoCh2Container;
    @FXML   private ImageView imgCh2;
    @FXML   private ImageView imgCh1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.facadeb = FacadeBackend.getInstance();
        this.facadef = FacadeFrontend.getInstance();

        heroes = facadeb.getPossibleCharacterSuggestions();
        superPower = facadeb.getPossibleSuperPowerSuggestions();

        initComboBoxAlgo();
        initComboBoxType();
        initTextFieldsAC();
        bindTextField();
        textAreaEmpty();
        textCHSelectedEmpty();
        vbMatch.setVisible(false);
        setContainetOff();
  
        go.setDisable(true);
        disableBtnCalculate();
    }
    
    private void setContainetOn(){
        infoCh2Container.setVisible(true);
    }
    private void setContainetOff(){
        infoCh2Container.setVisible(false);
    }    
    

    private void bindTextField() {
        txtResultsNumber.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
    }

    private void disableBtnCalculate(){
        calculate.setDisable(true);
    }
    
    private void ableBtnCalculate(){
        calculate.setDisable(false);
    }
    private void initComboBoxAlgo() {
        ObservableList<Algorithms> ol = facadeb.getAlgorithmsPossibilities();
        comboBoxAlgorithms.setItems(ol);
        clearTypesContainers();
    }
    
    private void clearTypesContainers(){
        predictionContainer.setVisible(false);
        similarityContainer.setVisible(false);        
    }
    
    private void setSimilarityOn(){
        predictionContainer.setVisible(false);
        similarityContainer.setVisible(true);         
    }

    private void setPredictionOn(){
        predictionContainer.setVisible(true);
        similarityContainer.setVisible(false);         
    }    

    private void initComboBoxType() {
        ObservableList<String> ol = FXCollections.observableArrayList();
        ol.addAll("Predição", "Similaridade");
        analisysTypeCombB.setItems(ol);
    }
    
    private void initTextFieldsAC() {
        TextFields.bindAutoCompletion(txtGetHero, heroes);
        TextFields.bindAutoCompletion(txtAcCharacteristc, superPower);
    }

    private void initTableCalculate(List<Result> results) {
        tcHero.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        tcSimilarity.setCellValueFactory(new PropertyValueFactory<>("strSimilarity"));
        tableResultado.getSortOrder().setAll(tcSimilarity);
        tableResultado.getItems().setAll(results);
    }  
    
    @FXML
    private void calculate(ActionEvent event) {
        textCHSelectedEmpty();
        List<Result> generatedList;
        try {
            generatedList = facadeb.calculateDistances(txtGetHero.getText(), comboBoxAlgorithms.getValue());
            ObservableList<Result> generated = FXCollections.observableArrayList(generatedList.subList(0, ((int)slider.getValue())));
            
            initTableCalculate(generated);
            if (generated != null) {
                tableResultado.setItems(generated);
            }
        } catch (IOException | CharacterNotFoundException ex) {
            facadef.newAlert("CharacterNotFoundException", "Não foi possível encontrar o personagem");
        }
    }

    @FXML
    private void loadCharacter(ActionEvent event) {
        Instance character;
        textAreaEmpty();
        textCHSelectedEmpty();
        try {
            character = facadeb.getCharacterByName(txtGetHero.getText());
            setLabel(character, labelInfos, char1);
            go.setDisable(false);
        } catch (CharacterNotFoundException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void txfEnterCalculationValue(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        try {
            int number = Integer.parseInt(txtResultsNumber.getText());
            if (ENTER.match(event) && number > 0) {
                slider.setValue(number);
            }
        } catch (NumberFormatException e) {
        }
        bindTextField();
    }

    private void stopBind(MouseEvent event) {
        txtResultsNumber.disableProperty();
    }

    private void textAreaEmpty() {
        labelInfos.setVisible(false);
    }

    private void textCHSelectedEmpty() {
        selectedCName.setVisible(false);
        selectedCCha.setVisible(false);
    }

    private void setLabel(Instance character, Label label, Label name) {
        String[] values = character.toString().split(",");
        String all = "";
        name.setText(values[1].replaceAll("'", ""));
        for (int i = 2; i <= 10; i++) {
            if (values[i].equals("-") || values[i].equals("-99")) {
                values[i] = "Unknown";
            }
            all += getAtribute(i) + values[i].replaceAll("'", "") + "\n";
        }
        label.setText(all);
        label.setVisible(true);
    }

    private String getAtribute(int a) {
        switch (a) {
            case 0:
                return "Index: ";
            case 1:
                return "Name: ";
            case 2:
                return "Gender: ";
            case 3:
                return "Eye Color: ";
            case 4:
                return "Race: ";
            case 5:
                return "Hair color: ";
            case 6:
                return "Height: ";
            case 7:
                return "Publiher: ";
            case 8:
                return "Skin Color: ";
            case 9:
                return "Alignment: ";
            case 10:
                return "Weight: ";
            default:
                break;
        }
        return null;
    }

    @SuppressWarnings("empty-statement")
    @FXML
    private void selectACHFromTable(MouseEvent event) {
        setContainetOff();
        vbMatch.setVisible(false);
        selectedCName.setVisible(false);
        String Ch = null;
        try{
            Ch = tableResultado.getSelectionModel().getSelectedItem().getCharacterName();
        }catch(NullPointerException e){
            
        }    

        if (Ch != null) {
            try {
                setContainetOn();
                selectedCName.setText(Ch);
                selectedCName.setVisible(true);
                Instance character = facadeb.getCharacterByName(Ch);
                setLabel(character, selectedCCha, char2);
                selectedCCha.setVisible(true);
                
                //Match area
                setMatchArea(Ch);
            } catch (CharacterNotFoundException ex) {
                facadef.newAlert("CharacterNotFoundException", "NÃ£o foi possÃ­vel encontrar o personagem");
                textCHSelectedEmpty();
            } catch(NullPointerException ex){
                facadef.newAlert("Null", "Nothig selected");
                textCHSelectedEmpty();
            }
        }
    }
    
    private void setMatchArea(String str){
        vbMatch.setVisible(true);
        onlyCH1.setText("");
        onlyCh2.setText("");
        matchChs.setText("");   
        try {
            String oc1 = "";
            String oc2 = "";
            String m = "";
            Instance a = facadeb.getCharacterByName(char1.getText());
            Instance b = facadeb.getCharacterByName(char2.getText());
            String[] splitCh1 = a.toString().split(",");
            String[] splitCh2 = b.toString().split(",");
            for (int i = 11; i < splitCh1.length; i++) {
                if(splitCh1[i].equals("True") && splitCh2[i].equals("True")){
                    m = m.concat(superPower[i-10] + "\n");
                }
                else if(splitCh1[i].equals("True") && splitCh2[i].equals("False")){
                    oc1 = oc1.concat(superPower[i-10] + "\n");
                }
                else if(splitCh1[i].equals("False") && splitCh2[i].equals("True")){
                    oc2 = oc2.concat(superPower[i-10] + "\n");
                }                
            }
            onlyCH1.setText(oc1);
            onlyCh2.setText(oc2);
            matchChs.setText(m);               
        } catch (CharacterNotFoundException ex) { }
    }

    @FXML
    private void comboBoxAlgorithmsOnAction(ActionEvent event) {
        if(comboBoxAlgorithms.getValue() != null){
            ableBtnCalculate();
            tooltipAlgorithm.setText(getAlgorithmDesc(comboBoxAlgorithms.getValue()));
        }         
    }
    
    private String getAlgorithmDesc(Algorithms al){
        switch (al) {
            case DICE: {
                return new Dice().getDescription();
            }
            case JACCARD_COEFFICIENT: {
                return new Jaccard().getDescription();
            }
            default: {
                return new SMC().getDescription();
            }
        }        
    }

    @FXML
    private void comboBoxTypesOnAction(ActionEvent event) {
        clearTypesContainers();
        switch (analisysTypeCombB.getValue()) {
            case "Predição":
                disableBtnCalculate();
                setPredictionOn();
                break;
            case "similaridade":
                disableBtnCalculate();
                setSimilarityOn();
                break;
            default:
                disableBtnCalculate();
                clearTypesContainers();
                break;
        }     
    }

    @FXML
    private void predictPower(ActionEvent event) {
        txtAcCharacteristc.getText();
        preChar.setText("");
    }

}