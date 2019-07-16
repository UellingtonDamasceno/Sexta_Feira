
package util;

/**
 *
 * @author Uellington Damasceno
 */
public class Settings {
    
    public enum StandardValues{
        MINIMUM_ALLOWED_VALUE(0),
        NUMBER_ATTRIBUTE_HERO(11),
        NUMBER_ATTRIBUTE_SUPER_POWER(168);
        
        private final int value;
        
        StandardValues(int value){
            this.value = value;
        }
        
       public int getValue(){
           return this.value;
       }
    }
    
    public enum FileSettings{
        
        CSV_DIVISOR(","), 
        INVALID_CHARACTER("'"),
        NOT_A_NUMBER("NaN"),
        STANDARD_CHARACTER("-");
        
        private final String value;
        
        FileSettings(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
    
    public enum Files{
        HEROES,
        SUPER_POWER;
    }
    
    public enum Path{
        
        HEROES_FILE_ARFF("res\\dataset\\pre-processed\\heroes.arff"),
        HEROES_CSV_ORIGINAL("res\\dataset\\original\\herois.csv"),
        HEROES_CSV_PRE_PROCESSED("res\\dataset\\pre-processed\\heroes.csv"),
        SUPER_POWER_FILE_ARFF("res\\dataset\\pre-processed\\superpowers.arff"),
        SUPER_POWER_CSV_ORIGINAL("res\\dataset\\original\\superpoderes.csv"),
        SUPER_POWER_CSV_PRE_PROCESSED("res\\dataset\\pre-processed\\superpoderes.csv");
        
        private final String value;
        
        Path(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
    
    public enum Scenes{
        HOME("view\\...");
        
        private final String value;
        
        Scenes(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
}
