package model.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class Dice implements Algorithm {

    private final String description;

    public Dice() {
        this.description = "The Dice similarity measure is a way to calculate\n "
                + "the similarity between two objects that have binary attributes.\n"
                + "This algorithm adds weight to the positive matches and does "
                + "not include negative matches.";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        BigDecimal dividend = new BigDecimal(2 * a);
        BigDecimal divisor = new BigDecimal((2 * a) + b + c);
        return dividend.divide(divisor, MathContext.DECIMAL64).doubleValue();
    }

    @Override
    public double calculate(OccurrenceTable table) {
        return this.calculate(table.getA(), table.getB(), table.getC(), table.getD());
    }
}
