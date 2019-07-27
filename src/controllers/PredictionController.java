package controllers;

import java.text.DecimalFormat;
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
    private String[] treeOptions, evaluationOptions;
    private Evaluation evaluation;
    private int predictionClass;

    public PredictionController() throws Exception {
        this.tree = new J48();
        this.treeOptions = new String[]{"-B", "-A"};
    }

    public void createTree(Instances instance, int predictionClass) throws Exception {
        this.predictionClass = predictionClass;

        instance.setClassIndex(predictionClass);
        System.out.println("\nClasse: " + instance.get(predictionClass).classAttribute().name());
        tree.buildClassifier(instance);
        evaluation = new Evaluation(instance);
        evaluation.crossValidateModel("J48", instance, 10, evaluationOptions, new Random(1));

        evaluationResults();
    }

    public void evaluationResults() {

        DecimalFormat df = new DecimalFormat("0.0000");

        System.out.println("Quantidade de amostras classificadas corretamente: " + df.format(evaluation.pctCorrect()) + "%");
        System.out.println("Quantidade de amostras classificadas incorretamente: " + df.format(evaluation.pctIncorrect()) + "%");
        System.out.println("Índice Kappa: " + df.format(evaluation.kappa()));

    }
}
