package model.bean;

import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class Jaccard implements Algorithm {

    private final String description;

    public Jaccard() {
        this.description = "The \"Jaccard\" similarity measure is a simple "
                + "and widely used way to calculate the similarity "
                + "between two objects that have binary attributes."
                + " This algorithm does not include negative matches.";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        return (1 - (a / (a + b + c)));
    }

}
