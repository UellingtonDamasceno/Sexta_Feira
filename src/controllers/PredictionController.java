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
        this.allParams.add(new Param("-M", 2, 10, true));
        this.allParams.add(new Param("-Q", 1, 10, true));
        //this.allParams.add(new Param("-N", 2, 10, true));

        //this.allParams.add(new Param("-U"));
        this.allParams.add(new Param("-O"));
        //this.allParams.add(new Param("-R"));
        //this.allParams.add(new Param("-B"));
        this.allParams.add(new Param("-S"));
        this.allParams.add(new Param("-L"));
        this.allParams.add(new Param("-A"));
        this.allParams.add(new Param("-J"));

        this.allParams.add(new Param("-doNotMakeSplitPointActualValue"));
    }

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

    public LinkedList<J48> florest() throws Exception {
        LinkedList<J48> florest = new LinkedList();
        for (int i = 1; i <= allParams.size(); i++) {
            System.out.println(allParams);
            florest.addAll(treesGenerator(allParams.subList(0, i)));
            System.out.println(florest.size());
        }
        return florest;
    }

    private LinkedList<J48> treesGenerator(List<Param> params) throws Exception {
        LinkedList<J48> trees = new LinkedList();
        LinkedList options = new LinkedList();
        for (Param param : params) {
            if (param.hasMoreParams()) {
                for (int i = 1; i <= params.size(); i++) {
                    trees.addAll(genereteSubTree(params.subList(0, i), param));
                }
            } else {
                J48 tree = new J48();
                options.add(param.getParam());
                tree.setOptions(listToArray(options));
                trees.add(tree);
            }
        }
        return trees;
    }

    private List<J48> genereteSubTree(List<Param> params, Param paramWichMoreValues) throws Exception {
        LinkedList<J48> subTrees = new LinkedList();
        LinkedList optionsList = new LinkedList();
        for (int i = paramWichMoreValues.getMinValue(); i < paramWichMoreValues.getMaxValue(); i++) {
            for (int j = 0; j < params.size(); j++) {
                Param param = params.get(j);
                J48 subTree = new J48();
                if (param.hasMoreParams()) {
                    String[] options = getOptions(params, String.valueOf(i));
                    subTree.setOptions(options);
                }
                subTrees.add(subTree);
            }
        }
        return subTrees;
    }

    private String[] getOptions(List<Param> params, String value) {
        LinkedList options = new LinkedList();
        for (int i = 0; i < params.size(); i++) {
            options.add(params.get(i).getParam());
            if (params.get(i).hasMoreParams()) {
                options.add(value);
            }
        }
        return listToArray(options);
    }

    private String[] getOptions(List<Param> params) {
        LinkedList options = new LinkedList();

        for (int i = 0; i < params.size(); i++) {
            Param p = params.get(i);
            if (!p.getParam().equals("-Q") && !p.getParam().equals("-N") && !p.getParam().equals("-M")) {
                options.add(p.getParam());
            }
        }

        return listToArray(options);
    }

    private String[] listToArray(List<String> options) {
        String[] o = new String[options.size()];
        for (int i = 0; i < o.length; i++) {
            o[i] = options.get(i);
        }
        return o;
    }
}
