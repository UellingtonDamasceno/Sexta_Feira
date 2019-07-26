package view;

import facades.FacadeBackend;
import facades.FacadeFrontEnd;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class SextaFeira extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            FacadeBackend.getInstance().initialize();
            FacadeFrontEnd.getInstance().initialize(primaryStage, Scenes.DASHBOARD);
        } catch (Exception ex) {
            Logger.getLogger(SextaFeira.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
