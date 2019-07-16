package controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerStage {

    private final Stage mainStage;

    public ControllerStage(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setResizable(false);
        this.mainStage.setTitle("");
        this.mainStage.getIcons().add(new Image(""));
    }

    public void changeStageContent(Parent content) {
        this.mainStage.close();
        this.mainStage.setScene(new Scene(content));

//        mainStage.getScene().setRoot(content);
        this.mainStage.centerOnScreen();
        this.mainStage.show();
    }

    public void newAlert(String title, String mensege) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(mensege);
        a.show();
    }

}
