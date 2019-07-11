package controllers;

import java.util.LinkedList;

/**
 *
 * @author Uellington Damasceno
 */
public class DataProcessingController {

    private final static int MINIMUM_ALLOWED_VALUE = 0;

    public LinkedList<String[]> standardizeNumericalValues(LinkedList<String[]> contentFile) {
        System.out.println("Initializing standartization of numerical values");
        contentFile.forEach((String[] line) -> {
            for(String string : line){
                try {
                    
                    
                } catch (NumberFormatException e) {
                }
            }
        });
        return contentFile;
    }
    
}
