package facades;

import controllers.frontend.ControllerScreens;
import controllers.frontend.ControllerStage;
import controllers.frontend.DashboardController;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeFrontEnd {

    private static FacadeFrontEnd facade;

    private ControllerStage controllerStage;
    private final ControllerScreens controllerScreens;
    private DashboardController dashboardController;
   
    
    private FacadeFrontEnd() {
        controllerScreens = new ControllerScreens();
    }

    public static synchronized FacadeFrontEnd getInstance() {
        return (facade == null) ? facade = new FacadeFrontEnd() : facade;
    }

    public void initialize(Stage mainStage, Scenes homeScreen) throws Exception {
        if (controllerStage == null) {
            this.dashboardController = (DashboardController) controllerScreens.getSceneController(Scenes.DASHBOARD);            
            Parent initialContent = controllerScreens.loadScreen(homeScreen);
            controllerStage = new ControllerStage(mainStage);
            controllerStage.changeStageContent(initialContent);
        }
    }
    
    public void changeStageContent(Scenes scene) throws Exception{
        Parent content = controllerScreens.loadScreen(scene);
        controllerStage.changeStageContent(content);
    }
    
    public Parent loadScreenFXML(Scenes scene) throws Exception{
        return controllerScreens.loadScreen(scene);
    }
    
    public boolean isScreenLoaded(Scenes scene){
        return controllerScreens.isScreenLoaded(scene);
    }
    
    public void newAlert(String titulo, String mensagem){
        controllerStage.newAlertError(titulo, mensagem);
    }
}
