package model.bean;

import util.Settings.PredictionClasses;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;

/**
 *
 * @author Gabriela
 */
public class ResultsTree implements Comparable {

    private final J48 tree;
    private final Evaluation evaluation;
    private final PredictionClasses predictionClasse;

    public ResultsTree(J48 params, Evaluation evaluation, PredictionClasses predictionClasses) {
        this.tree = params;
        this.evaluation = evaluation;
        this.predictionClasse = predictionClasses;
    }

    public J48 getParams() {
        return this.tree;
    }

    public double getResult() {
        return this.evaluation.pctCorrect();
    }
    
    public PredictionClasses getPredictionClasses(){
        return this.predictionClasse;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ResultsTree) {
            ResultsTree other = (ResultsTree) o;
            if (this.getResult() == other.getResult()) {
                return 0;
            } else if (this.getResult() < other.getResult()) {
                return -1;
            } else {
                return 1;
            }
        }
        return -99;
    }

    @Override
    public String toString() {
        return tree.toString();
    }
}
