package view;

import exceptions.ListIsEmpty;
import facades.FacadeBackend;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import util.Settings;

/**
 *
 * @author Uellington Damasceno
 */
public class SextaFeira extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FacadeBackend facadeBackend = new FacadeBackend();     
        try {   
            facadeBackend.calculateDistances(Settings.DatasetId.HEROES, Settings.Algorithms.SMC);
        } catch (IOException ex) {
            Logger.getLogger(SextaFeira.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
