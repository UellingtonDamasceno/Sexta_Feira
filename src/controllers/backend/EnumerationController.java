package controllers.backend;

import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.bean.Dice;
import model.bean.Jaccard;
import model.bean.SMC;
import util.Algorithm;
import util.Settings;
import util.Settings.Algorithms;
import util.Settings.Path;

/**
 *
 * @author Uellington Damasceno
 */
public class EnumerationController {

    public ObservableList<Algorithms> getAlgorithmsPossibilities() {
        return FXCollections.observableArrayList(Algorithms.values());
    }
    
    public List<String> takeAllRegistredFilePaths(){
        List<String> allPaths = new LinkedList();
        for (Path value : Path.values()) {
            allPaths.add(value.getValue());
        }
        return allPaths;
    }

    public Algorithm algorithmFactory(Settings.Algorithms algorithmType) {
        switch (algorithmType) {
            case DICE: {
                return new Dice();
            }
            case JACCARD_COEFFICIENT: {
                return new Jaccard();
            }
            default: {
                return new SMC();
            }
        }
    }

}
