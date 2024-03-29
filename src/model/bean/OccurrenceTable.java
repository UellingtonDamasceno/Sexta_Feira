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

    public OccurrenceTable() {
        this(0, 0, 0, 0);
    }

    public OccurrenceTable(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void addInPosition(String value) {
        switch (value) {
            case "FF":
                this.d++;
                break;
            case "FT":
                this.b++;
                break;
            case "TF":
                this.c++;
                break;
            case "TT":
                this.a++;
                break;
            default: System.out.println(value);
        }
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
    public String toString() {
        return "A: ".concat(a.toString())
                .concat("\nB: ")
                .concat(b.toString())
                .concat("\nC: ")
                .concat(c.toString())
                .concat("\nD: ")
                .concat(d.toString());
    }
}
