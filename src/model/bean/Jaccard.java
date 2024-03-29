package model.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class Jaccard implements Algorithm {

    private final String description;

    public Jaccard() {
        this.description = "The \"Jaccard\" similarity measure is a simple\n "
                + "and widely used way to calculate the similarity\n "
                + "between two objects that have binary attributes.\n"
                + " This algorithm does not include negative matches.";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        BigDecimal dividend = new BigDecimal(a);
        BigDecimal divisor = new BigDecimal(a + b + c);
        return dividend.divide(divisor, MathContext.DECIMAL128).doubleValue();
    }

    @Override
    public double calculate(OccurrenceTable table) {
        return this.calculate(table.getA(), table.getB(), table.getC(), table.getD());
    }

}
