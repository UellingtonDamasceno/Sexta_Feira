package controllers;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class PredictionController {

    private final J48 tree;
    private Evaluation evaluation;
    private Instances instances;
    private int predictionClassIndex;

    public PredictionController() {
        this.tree = new J48();

    }

    public void prediction(Instances instances, int predictionClassIndex) throws Exception {
        this.instances = instances;
        instances.setClassIndex(predictionClassIndex);

        treeOptions();
        tree.buildClassifier(instances);
        evaluate();
    }

    private void treeOptions() throws Exception {

        switch (predictionClassIndex) {
            case 19: //flight
                tree.setOptions(new String[]{"-M", "10"});
                break;

            case 28: //super strength
                tree.setOptions(new String[]{"-M", "3"});
                break;

            case 12: //accelerated
                tree.setOptions(new String[]{"-O", "-B", "-M", "9"});
                break;

            case 9: // alignment
                tree.setOptions(new String[]{"-B", "-A"});
                break;

            case 145: //invisibility
                tree.setOptions(new String[]{"-B", "-A"});
                break;
        }
    }

    private void evaluate() throws Exception {
        evaluation = new Evaluation(instances);

        evaluation.crossValidateModel(tree, instances, 10, new Random(1));

    }

    @Override
    public String toString() {

        return ("\nClasse: " + instances.classAttribute().toString()
                + evaluation.toSummaryString());
    }

}
