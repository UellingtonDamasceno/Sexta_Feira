package controllers.frontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.Settings;
import util.Settings.Icons;

/**
 *
 * @author Uellington Damasceno
 */
public class ControllerStage {

    private final Stage mainStage;

    public ControllerStage(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setResizable(false);
        this.mainStage.setTitle("Prediction module - Friday");
        //this.mainStage.getIcons().add(new Image(Icons.IRON_MAN.getValue()));
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
