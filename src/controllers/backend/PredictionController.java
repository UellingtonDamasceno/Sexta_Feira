package controllers.backend;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import model.bean.ResultsTree;
import util.Settings;
import util.Settings.PredictionClasses;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class PredictionController {

    private LinkedList<ResultsTree> results;

    public List<ResultsTree> classifier(List<J48> florest, Instances instance, PredictionClasses[] predictionClasses) throws Exception {
        for (Settings.PredictionClasses predictionClass : predictionClasses) {
            for (J48 currentTree : florest) {
                results.add(createTree(currentTree, instance, predictionClass));
            }
        }
        System.out.println(results.size());
        System.out.println("Terminou!!!");
        return results;
    }

    private ResultsTree createTree(J48 tree, Instances instance, PredictionClasses predictionClass) throws Exception {
        instance.setClassIndex(predictionClass.getValue());

        tree.buildClassifier(instance);
        Evaluation evaluation = new Evaluation(instance);
        evaluation.crossValidateModel(tree, instance, 10, new Random(1));
        evaluationResults(instance, predictionClass.getValue(), evaluation);

        ResultsTree result = new ResultsTree(tree.getOptions(), evaluation.pctCorrect(), predictionClass.getValue());
        System.out.println(Arrays.toString(tree.getOptions()));
        return result;
    }

    public void evaluationResults(Instances instance, int predictionClass, Evaluation evaluation) {
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println("\nClasse: " + instance.get(predictionClass).classAttribute().name());
        System.out.println("Quantidade de amostras classificadas corretamente: " + df.format(evaluation.pctCorrect()) + "%");
        System.out.println("Quantidade de amostras classificadas incorretamente: " + df.format(evaluation.pctIncorrect()) + "%");
        System.out.println("Índice Kappa: " + df.format(evaluation.kappa()));
    }
}
