package controllers;

import java.text.DecimalFormat;
import java.util.LinkedList;
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
    private LinkedList<Param> allParams;
    private LinkedList<ResultsTree> results;

    public class Param {//tendi :)

        private String value;
        private double minValue;
        private double maxValue;
        private double selectionedInterval;//se quiser traduzir ekkee

        public Param(String value) {
            this(value, 0, 0);
        }

        public Param(String value, double minValue, double maxValue) {
            this.value = value;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public boolean hasMoreParams() {
            return (minValue + maxValue);
        }

        public double getMinValue() {
            return this.minValue;
        }

        public double getMaxValue() {
            return this.maxValue;
        }
    }

    public PredictionController() {
        this.tree = new J48();
        this.results = new LinkedList();
        this.allParams.add(new Param("-U"));
        this.allParams.add(new Param("-O)"));
        this.allParams.add(new Param("-C", 0.2, 0.9));// recebe valores entre 0.2 e 0,9 Hum... tendi.
        this.allParams.add(new Param("-M", 2, 10));
        this.allParams.add(new Param("-R"));
        this.allParams.add(new Param("-N", 2, 10));
        this.allParams.add(new Param("-B"));
        this.allParams.add(new Param("-S"));
        this.allParams.add(new Param("-L"));
        this.allParams.add(new Param("-A"));
        this.allParams.add(new Param("-J"));
        this.allParams.add(new Param("-Q", 1, 10));
        this.allParams.add(new Param("-doNotMakeSplitPointActualValue"));
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

    private List<J48> listTreeGenerator(List<Param> params) {
        List<J48> trees = new LinkedList();
        for (Param param : params) {// estou pensando na maneira mais eficiente. tenha fé.
            J48 treeSup = new J48();
            treeSup.setOptions(params);
        }
    }

//          this.allParams.add(new Param("-U"));
//        this.allParams.add(new Param("-O)"));
//        this.allParams.add(new Param("-C", 0.2, 0.9));// recebe valores entre 0.2 e 0,9 Hum... tendi.
//        this.allParams.add(new Param("-M", 2, 10));
//        this.allParams.add(new Param("-R"));
//        this.allParams.add(new Param("-N", 2, 10));
//        this.allParams.add(new Param("-B"));
//        this.allParams.add(new Param("-S"));
//        this.allParams.add(new Param("-L"));
//        this.allParams.add(new Param("-A"));
//        this.allParams.add(new Param("-J"));
//        this.allParams.add(new Param("-Q", 1, 10));
//        this.allParams.add(new Param("-doNotMakeSplitPointActualValue"));
    private J48 setOption(Param param, J48 tree) {
        switch (param.value) {
            case "-U": {
                tree.setUnpruned(true);
                break;
            }
            default:
        }
    }

    private void calculate() {
        LinkedList<J48> allTrees = new LinkedList();
        for (int i = 1; i < allParams.size(); i++) {
            allTrees.addAll(listTreeGenerator(allParams.subList(0, i)));
        }
    }

    private J48 genereteSubTree(List<Param> params, double minValue, double maxValue) {
        LinkedList<J48> subTrees = new LinkedList();
        for (Param param : params) {
            J48 subTree = new J48();
            if (!param.hasMoreParams()) {
            } else {
                for (int i = minValue; i < maxValue; i++) {

                }
            }
        }
    }
}
