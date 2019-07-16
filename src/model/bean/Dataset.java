package model.bean;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Uellington Damasceno
 */
public class Dataset { 

    private final List<TableHeader> tableHeader;
    private final List<Hero> heroes;

    public Dataset(List<TableHeader> tableHeader, List<Hero> heroes) {
        this.tableHeader = tableHeader;
        this.heroes = heroes;
    }
    
    public Hero getHero(Hero target){
       int indexHero = heroes.indexOf(target);
       return heroes.get(indexHero);
    }
    
    public int getTotalHero(){
        return heroes.size();
    }
    
    public int getTotalAttributes(){
        return tableHeader.size();
    }
    
    public Iterator iteratorListHero(){
        return heroes.iterator();
    }
    
    public Iterator iteratorListTableHeader(){
        return tableHeader.iterator();
    }

    public void printHeader(){
        System.out.println(tableHeader);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.tableHeader.hashCode();
        hash = 37 * hash + this.heroes.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Dataset){
            Dataset other = (Dataset) obj;
            return (this.hashCode() == other.hashCode());
        }
        return false;
    }
    
}