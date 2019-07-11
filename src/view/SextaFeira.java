package view;

import facades.FacadeBackend;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import util.Settings.FilePath;

/**
 *
 * @author Uellington Damasceno
 */
public class SextaFeira extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FacadeBackend facadeBackend = new FacadeBackend();
        System.out.println(FilePath.HEROES.getValue());
        try {
            facadeBackend.initialize();
        } catch (IOException ex) {
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
