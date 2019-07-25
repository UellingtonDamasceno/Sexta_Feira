package util;

import model.bean.OccurrenceTable;

/**
 *
 * @author Uellington Damasceno
 */
public interface Algorithm {
    
    public String getDescription();
    
    public double calculate(int a, int b, int c, int d);
    
    public double calculate(OccurrenceTable table);
}
