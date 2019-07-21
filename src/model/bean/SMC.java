package model.bean;

import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class SMC implements Algorithm {

    private final String description;

    public SMC() {
        this.description = "The Simple Matching Coefficient similarity measure "
                + "is a way to calculate the similarity between two objects that "
                + "have binary attributes. This algorithm includes negative matches.";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        return ((a + d) / (a + b + c + d));
    }

}
