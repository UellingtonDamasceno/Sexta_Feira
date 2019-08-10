package controllers.backend;

import java.util.Random;
import util.Settings.PredictionClasses;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class PredictionController {

    private final J48 tree;

    public PredictionController() {
        this.tree = new J48();
    }

    public void prediction(Instances instances, PredictionClasses predictionClass) throws Exception {
        instances.setClassIndex(predictionClass.getValue());
        tree.setOptions(treeOptions(predictionClass));
        tree.buildClassifier(instances);
        
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(tree, instances, 10, new Random(1));
    }

    private String[] treeOptions(PredictionClasses predictionClass) {

        switch (predictionClass) {
            case FLIGHT: {
                return new String[]{"-M", "10"};
            }
            case SUPER_STRENGTH: {
                return new String[]{"-M", "3"};
            }
            case ACCELERATED_HEALING: {
                return new String[]{"-O", "-B", "-M", "9"};
            }
            case ALIGNMENT: {
                return new String[]{"-B", "-A"};
            }
            case INVISIBILITY: {
                return new String[]{"-B", "-A"};
            }
            default: {
                throw new AssertionError(predictionClass.name());
            }
        }
    }

}
