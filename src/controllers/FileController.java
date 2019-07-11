package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import util.Settings.FilePath;
import util.Settings.FileSettings;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

/**
 *
 * @author Uellington Damasceno
 */
public class FileController {

    
    public Instances readFileCSVWithWeka(FilePath filePath) throws IOException{
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filePath.getValue()));
        return loader.getDataSet();    
    }   
    
    public Instances readFileArff(FilePath filePath) throws IOException{
        ArffLoader loader = new ArffLoader();
        loader.setSource(new File(filePath.getValue()));
        return loader.getDataSet();
    }
    
    public void saveDataInArffFormact(Instances data, FilePath filePath) throws IOException{
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(filePath.getValue()));
        saver.writeBatch();
    }
    
    public void saveDataInCSVFormact(Instances data, FilePath filePath) throws IOException{
        CSVSaver saver = new CSVSaver();
        saver.setInstances(data);
        saver.setFile(new File(filePath.getValue()));
        saver.writeBatch();
    }
    
    public LinkedList<String[]> readCSV(FilePath filePath) throws FileNotFoundException, IOException {
       
        BufferedReader br = new BufferedReader(new FileReader(filePath.getValue()));
        LinkedList<String[]> lines = new LinkedList<>();
        String line;
        
        while ((line = br.readLine()) != null) {
            lines.add(line.split(FileSettings.CSV_DIVISOR.getValue()));
        }
        System.out.println(lines);
        return lines;
    }

}
