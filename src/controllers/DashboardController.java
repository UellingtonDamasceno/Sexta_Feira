package controllers;

import exceptions.CharacterNotFoundException;
import facades.FacadeBackend;
import facades.FacadeFrontEnd;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.bean.Result;
import org.controlsfx.control.textfield.TextFields;
import util.Settings.Algorithms;
import weka.core.Instance;

/**
 * FXML Controller class
 *
 * @author Uellington Damasceno
 */
public class DashboardController implements Initializable {

    @FXML    private VBox vboxContent;
    @FXML    private TableColumn<Result, String> tcHero;
    @FXML    private TableColumn<Result, String> tcSimilarity;
    @FXML    private TextField txtGetHero;
    @FXML    private TextField txtAcCharacteristc;
    @FXML    private ComboBox<Algorithms> comboBoxAlgorithms;
    @FXML    private TableView<Result> tableResultado;
    @FXML    private Slider slider;
    @FXML    private TextField txtResultsNumber;
    @FXML    private Button add;
    @FXML    private Button calculate;
    @FXML    private Label labelInfos;

    @FXML    private Label selectedCName;
    @FXML    private Label selectedCCha;

    private FacadeBackend facadeb;
    private FacadeFrontEnd facadef;
    
    private String[] heroes;
    private String[] superPower;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.facadeb = FacadeBackend.getInstance();
        this.facadef = FacadeFrontEnd.getInstance();

        heroes = facadeb.getPossibleCharacterSuggestions();
        superPower = facadeb.getPossibleSuperPowerSuggestions();

        initComboBox();
        initTextFieldsAC();
        bindTextField();
        textAreaEmpty();
        textCHSelectedEmpty();

        add.setDisable(true);
        calculate.setDisable(true);
    }

    private void bindTextField() {
        txtResultsNumber.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
    }

    @FXML
    private void addNewCharacteristc(ActionEvent event) {
        String characteristic = txtAcCharacteristc.getText();
//        vboxContent.getChildren().add(getCaracteristic(characteristic));
    }

    private void initComboBox() {
        ObservableList<Algorithms> ol = facadeb.getAlgorithmsPossibilities();
        comboBoxAlgorithms.setItems(ol);
    }

    private void initTextFieldsAC() {
        TextFields.bindAutoCompletion(txtGetHero, heroes);
        TextFields.bindAutoCompletion(txtAcCharacteristc, superPower);
    }

    private void initTable(List<Result> results) {
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
            ObservableList<Result> generated = FXCollections.observableArrayList(generatedList.subList(0, ((int)slider.getValue() - 1)));
            initTable(generated);
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
            setLabel(character, labelInfos);
            add.setDisable(false);
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
        txtResultsNumber.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
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

private void setLabel(Instance character, Label label) {
        String[] values = character.toString().split(",");
        String all = "";

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
        selectedCName.setVisible(false);
        String Ch = tableResultado.getSelectionModel().getSelectedItem().getCharacterName();

        if (Ch != null) {
            try {
                selectedCName.setText(Ch);
                selectedCName.setVisible(true);
                Instance character = facadeb.getCharacterByName(Ch);
                setLabel(character, selectedCCha);
                selectedCCha.setVisible(true);
            } catch (CharacterNotFoundException ex) {
                facadef.newAlert("CharacterNotFoundException", "Não foi possível encontrar o personagem");
                textCHSelectedEmpty();
            } catch(NullPointerException ex){
                facadef.newAlert("Null", "Nothig selected");
                textCHSelectedEmpty();
            }
        }
    }

    @FXML
    private void comboBoxAlgorithmsOnAction(ActionEvent event) {
        if(comboBoxAlgorithms.getValue() != null){
            calculate.setDisable(false);
        }         
    }

}