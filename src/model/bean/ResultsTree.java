package model.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Gabriela
 */
public class ResultsTree implements Comparable, Serializable {

    private final String[] options;
    private final double result;
    private final int predictionClasse;

    public ResultsTree(String[] options, double result, int predictionClasses) {
        this.options = options;
        this.result = result;
        this.predictionClasse = predictionClasses;
    }

    public String[] getOptions() {
        return this.options;
    }

    public double getResult() {
        return this.result;
    }
    
    public int getPredictionClasses(){
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
        return Arrays.toString(this.options);
    }
}
