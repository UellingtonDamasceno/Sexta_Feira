package model.bean;

import controllers.PredictionController.Param;
import java.util.List;

/**
 *
 * @author Gabriela
 */
public class ResultsTree implements Comparable {

    private List<Param> params;
    private double result;

    public ResultsTree(List<Param> params, double result) {
        this.params = params;
        this.result = result;
    }

    public List<Param> getParams() {
        return this.params;
    }

    public double getResult() {
        return this.result;
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
        return params.toString();
    }
}
