package model.bean;

import util.Algorithm;

/**
 *
 * @author Uellington Damasceno
 */
public class Dice implements Algorithm{
    private String description;
    
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double calculate(int a, int b, int c, int d) {
        return ((2*a)/((2*a)+b+c));
    }
    
}
