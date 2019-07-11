
package util;

/**
 *
 * @author Uellington Damasceno
 */
public class Settings {
    
    public enum FileSettings{
        CSV_DIVISOR(","), 
        STANDARD_CHARACTER("-"),
        NOT_A_NUMBER("NaN");
        private final String value;
        
        FileSettings(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
    
    public enum FilePath{
        RESOURCES("resources\\"),
        DATASET("resources\\dataset\\"),
        HEROES("resources\\dataset\\herois.csv"),
        SUPER_POWER("resources\\dataset\\superpoderes.csv");
        
        private final String value;
        
        FilePath(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
}
