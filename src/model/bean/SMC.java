package model.bean;

import java.math.BigDecimal;
import java.math.MathContext;
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
        BigDecimal dividend = new BigDecimal(a + d);
        BigDecimal divisor = new BigDecimal(a + b + c + d);
        BigDecimal one = new BigDecimal(1);
        return one.subtract(dividend.divide(divisor, MathContext.DECIMAL64)).doubleValue();
    }

    @Override
    public double calculate(OccurrenceTable table) {
        return this.calculate(table.getA(), table.getB(), table.getC(), table.getD());
    }
}
