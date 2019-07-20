package model.bean;

import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class SMC implements Algorithm{
    private String description;
    
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        return ((a+d)/(a+b+c+d));
    }
    
}
