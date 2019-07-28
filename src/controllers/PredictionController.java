package controllers;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import model.bean.Param;
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

    private LinkedList<Param> allParams;
    private LinkedList<ResultsTree> results;

    
    public PredictionController() {
        this.results = new LinkedList();
        this.allParams = new LinkedList();
        //this.allParams.add(new Param("-U"));
        this.allParams.add(new Param("-O"));
        //this.allParams.add(new Param("-C", 0.2, 0.9, 0.1));
        this.allParams.add(new Param("-M", 2, 10, 1));
        // this.allParams.add(new Param("-R"));
        //this.allParams.add(new Param("-N", 2, 10, 1));
        this.allParams.add(new Param("-B"));
        //this.allParams.add(new Param("-S"));
        this.allParams.add(new Param("-L"));
        this.allParams.add(new Param("-A"));
        this.allParams.add(new Param("-J"));
        this.allParams.add(new Param("-Q", 1, 10, 1));
        this.allParams.add(new Param("-doNotMakeSplitPointActualValue"));
    }

    public void classifier(List<J48> florest, Instances instance, PredictionClasses[] predictionClasses) throws Exception {
        for (Settings.PredictionClasses predictionClass : predictionClasses) {
            for (J48 currentTree : florest) {
                results.add(createTree(currentTree, instance, predictionClass));
            }
        }
    }

    private ResultsTree createTree(J48 tree, Instances instance, PredictionClasses predictionClass) throws Exception {
        instance.setClassIndex(predictionClass.getValue());

        tree.buildClassifier(instance);
        Evaluation evaluation = new Evaluation(instance);
        evaluation.crossValidateModel(tree, instance, 10, new Random(1));
        evaluationResults(instance, predictionClass.getValue(), evaluation);
        
        ResultsTree result = new ResultsTree(tree, evaluation, predictionClass);
        return result;
    }

    public void evaluationResults(Instances instance, int predictionClass, Evaluation evaluation) {
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println("\nClasse: " + instance.get(predictionClass).classAttribute().name());
        System.out.println("Quantidade de amostras classificadas corretamente: " + df.format(evaluation.pctCorrect()) + "%");
        System.out.println("Quantidade de amostras classificadas incorretamente: " + df.format(evaluation.pctIncorrect()) + "%");
        System.out.println("Índice Kappa: " + df.format(evaluation.kappa()));
    }

    public LinkedList<J48> florest() throws Exception {
        LinkedList<J48> florest = new LinkedList();
        for (int i = 1; i < allParams.size(); i++) {
            florest.addAll(treesGenerator(allParams.subList(0, i)));
        }
        return florest;
    }

    private LinkedList<J48> treesGenerator(List<Param> params) throws Exception {
        LinkedList<J48> trees = new LinkedList();
        for (Param param : params) {
            if (param.hasMoreParams()) {
                for (int i = 0; i < params.size(); i++) {
                    trees.addAll(genereteSubTree(params.subList(0, i), param));
                }
            } else {
                J48 tree = new J48();
                tree.setOptions(getOptions(params));
                trees.add(tree);
            }
        }
        return trees;
    }

    private List<J48> genereteSubTree(List<Param> params, Param paramWichMoreValues) throws Exception {
        LinkedList<J48> subTrees = new LinkedList();
        for (double i = paramWichMoreValues.getMinValue(); i < paramWichMoreValues.getMaxValue(); i += paramWichMoreValues.getUp()) {
            for (Param param : params) {
                J48 subTree = new J48();
                if (param.hasMoreParams() && !(param.equals(paramWichMoreValues))) {
                    String[] options = getOptions(params);
                    options[options.length - 1] = options[options.length - 1] + i;
                    subTree.setOptions(options);
                } else {
                    subTree.setOptions(getOptions(params));
                }
                subTrees.add(subTree);
            }
        }
        return subTrees;
    }

    private String[] getOptions(List<Param> params) {
        String[] options = new String[params.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = params.get(i).getParam();
        }
        return options;
    }
}
