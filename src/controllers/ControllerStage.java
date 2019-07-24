package controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerStage {

    private final Stage mainStage;

    public ControllerStage(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setResizable(false);
        this.mainStage.setTitle("Prediction module - Sexta Feira");
        
//        this.mainStage.getIcons().add(new Image("res\\images\\icons8_ironman.png"));
    }

    public void changeStageContent(Parent content) {
        this.mainStage.close();
        this.mainStage.setScene(new Scene(content));
        this.mainStage.centerOnScreen();
        this.mainStage.show();
    }

    public void newAlertError(String title, String mensege) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(mensege);
        a.show();
    }

}
