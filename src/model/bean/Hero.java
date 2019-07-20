package model.bean;

import java.util.List;

/**
 *
 * @author Uellington Damasceno
 */
public class Hero {
    private final int id;
    private String name;
    private List<Attributeee> attributes;
    
    public Hero(int id, String name, List<Attributeee> attributes){
        this.id = id;
        this.name = name;
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attributeee> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attributeee> attributes) {
        this.attributes = attributes;
    }
    
        @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Hero){
            Hero other = (Hero) obj;
            return (this.hashCode() == other.hashCode());
        }
        return false;
    }
}
