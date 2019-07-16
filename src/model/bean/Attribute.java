package model.bean;

/**
 *
 * @author Uellington Damasceno
 */
public class Attribute {
    private final int id;
    private String attributeLabel;
    private String value;

    public Attribute(int id, String attributeLabel, String value) {
        this.id = id;
        this.attributeLabel = attributeLabel;
        this.value = value;
    }

    public int getId() {
        return id;
    }
    
    public String getAttributeLabel() {
        return attributeLabel;
    }

    public void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Attribute){
            Attribute other = (Attribute) obj;
            return (this.hashCode() == other.hashCode());
        }
        return false;
    }
    
    @Override
    public String toString(){
        return "{\nid:".concat(String.valueOf(this.id))
                .concat(",\n")
                .concat("Attribute: ")
                .concat(this.attributeLabel)
                .concat(",\n")
                .concat("value: ")
                .concat(this.value)
                .concat("\n}");
    }
}

