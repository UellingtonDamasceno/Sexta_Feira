package controllers.backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.bean.Dice;
import model.bean.Jaccard;
import model.bean.SMC;
import util.Algorithm;
import util.Settings;
import util.Settings.Algorithms;

/**
 *
 * @author Uellington Damasceno
 */
public class AlgorithmController {

    public ObservableList<Algorithms> getAlgorithmsPossibilities() {
        return FXCollections.observableArrayList(Algorithms.values());
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
