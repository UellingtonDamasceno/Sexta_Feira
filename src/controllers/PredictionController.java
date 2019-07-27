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
    private Evaluation evaluation;
    private int predictionClass;
    private Instances instance;

    public PredictionController() throws Exception {
        this.tree = new J48();
        tree.setOptions(new String[]{"-B", "-A"});
    }

    public void createTree(Instances instance, int predictionClass) throws Exception {
        this.predictionClass = predictionClass;
        this.instance = instance;

        instance.setClassIndex(predictionClass);

        tree.buildClassifier(instance);
        evaluation = new Evaluation(instance);
        evaluation.crossValidateModel(tree, instance, 10, new Random(1));

        evaluationResults();
    }

    public void evaluationResults() {

        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println("\nClasse: " + instance.get(predictionClass).classAttribute().name());
        System.out.println("Quantidade de amostras classificadas corretamente: " + df.format(evaluation.pctCorrect()) + "%");
        System.out.println("Quantidade de amostras classificadas incorretamente: " + df.format(evaluation.pctIncorrect()) + "%");
        System.out.println("Índice Kappa: " + df.format(evaluation.kappa()));
    }
}
