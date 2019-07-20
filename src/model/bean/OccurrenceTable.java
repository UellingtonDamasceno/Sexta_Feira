package model.bean;

/**
 *
 * @author Uellington Damasceno
 */
public class OccurrenceTable {
    private Integer a;
    private Integer b;
    private Integer c;
    private Integer d;
    
    public OccurrenceTable(){
        this(0,0,0,0);
    }
    
    public OccurrenceTable(int a, int b, int c, int d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public void addA(){
        this.a++;
    }
    
    public void addB(){
        this.b++;
    }
    
    public void addC(){
        this.c++;
    }
    
    public void addD(){
        this.d++;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }
    
    @Override
    public String toString(){
        return "A: ".concat(a.toString())
                .concat("\nB: ")
                .concat(b.toString())
                .concat("\nC: ")
                .concat(c.toString())
                .concat("\nD: ")
                .concat(d.toString());
    }
}
