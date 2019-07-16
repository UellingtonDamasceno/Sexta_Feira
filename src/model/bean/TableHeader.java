package model.bean;

/**
 *
 * @author Uellington Damasceno
 */
public class TableHeader {
    
    private final int id;
    private final String label;
    
    public TableHeader(int id, String label){
        this.id = id;
        this.label = label;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getLabel(){
        return this.label;
    }
    
    @Override
    public String toString(){
        return "{\nid:"
                .concat(String.valueOf(this.id))
                .concat(",\n")
                .concat("label:")
                .concat(this.label)
                .concat("\n}");
    }
}
