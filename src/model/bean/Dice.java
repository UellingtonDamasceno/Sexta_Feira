package model.bean;

import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class Dice implements Algorithm {

    private String description;

    public Dice() {
        this.description = "The Dice similarity measure is a way to calculate "
                + "the similarity between two objects that have binary attributes."
                + "This algorithm adds weight to the positive matches and does "
                + "not include negative matches.";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        return (1 - ((2 * a) / ((2 * a) + b + c)));
    }

}
