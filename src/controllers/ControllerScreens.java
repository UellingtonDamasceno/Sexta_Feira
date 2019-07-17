package controllers;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import util.Settings;
import util.Settings.Scenes;


/**
 *
 * @author Uellington Damasceno
 */
public class ControllerScreens {

    private final HashMap<Scenes, Parent> allScreens = new HashMap();
    private Scenes lastScreenLoaded;

    public ControllerScreens() {
        this.lastScreenLoaded = null;
    }

    public boolean isScreenLoaded(Scenes scene) {
        return lastScreenLoaded == scene;
    }

    public Parent loadScreen(Settings.Scenes scene) throws Exception {
        return (scene.isCache()) ? loadScreenInCache(scene) : loadFXML(scene);
    }

    private Parent loadScreenInCache(Settings.Scenes scene) throws Exception {
        if (!(lastScreenLoaded == scene) && !allScreens.containsKey(scene)) {
            Parent screenLoaded = loadFXML(scene);
            addNewScreen(scene, screenLoaded);
        }
        lastScreenLoaded = scene;
        return getScreen(scene);
    }

    private void addNewScreen(Scenes scene, Parent content) throws Exception {
        if (scene != null && content != null) {
            allScreens.put(scene, content);
        } else {
            throw new Exception("Id ou Conteudo nulo");
        }
    }

    public Parent getScreen(Scenes scene) throws Exception {
        if (scene != null) {
            if (allScreens.containsKey(scene)) {
                return allScreens.get(scene);
            } else {
                throw new Exception("Não existe tela com esse id");
            }
        } else {
            throw new Exception("Id da tela é nulo");
        }
    }

    public FXMLLoader getLoaderFXML(Scenes scene) {
        return new FXMLLoader(getClass().getResource(scene.getValue()));
    }

    public Object getSceneController(Scenes scene) {
        return getLoaderFXML(scene).getController();
    }

    private Parent loadFXML(Scenes scene) throws IOException {
        return getLoaderFXML(scene).load();
    }

}
