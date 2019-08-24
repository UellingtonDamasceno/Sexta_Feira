package view;

import exceptions.ListIsEmpty;
import facades.FacadeBackend;
import facades.FacadeFrontend;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import util.Settings;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class SextaFeira extends Application {

    @Override
    public void start(Stage primaryStage) {
        FacadeBackend facadeBackend = FacadeBackend.getInstance();
        FacadeFrontend facadeFrontend = FacadeFrontend.getInstance();

        try {
            if (!facadeBackend.hasAlreadyBeenInitialized() || !facadeBackend.wasSuccessfullyClosed()) {
                facadeBackend.firstBoot();
            } else {
                facadeBackend.boot();
                facadeBackend.prediction(Settings.Dataset.SUPER_POWER_MERGE_HERO, Settings.PredictionClasses.INVISIBILITY);
            }
            facadeFrontend.initialize(primaryStage, Scenes.DASHBOARD);
        } catch (IOException e) {
            Logger.getLogger(SextaFeira.class.getName()).log(Level.SEVERE, null, e);
        } catch (ListIsEmpty e) {
            Logger.getLogger(SextaFeira.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception ex) {
            Logger.getLogger(SextaFeira.class.getName()).log(Level.SEVERE, null, ex);
        }

        primaryStage.setOnCloseRequest(new EventHandler() {
            @Override
            public void handle(Event event
            ) {
                try {
                    facadeBackend.finalize();
                } catch (IOException ex) {
                    //tratar se possivel
                }
            }
        }
        );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
