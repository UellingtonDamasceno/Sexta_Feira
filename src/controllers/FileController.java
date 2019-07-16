package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import util.Settings.Path;
import util.Settings.FileSettings;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.converters.Loader;
import weka.core.converters.Saver;

/**
 *
 * @author Uellington Damasceno
 */
public class FileController {

    public Instances readFileCSVWithWeka(Path filePath) throws IOException {
        CSVLoader loader = new CSVLoader();
        return this.loadData(loader, filePath);
    }

    public Instances readFileArff(Path filePath) throws IOException {
        ArffLoader loader = new ArffLoader();
        return this.loadData(loader, filePath);
    }

    public void saveDataInArffFormact(Instances data, Path filePath) throws IOException {
        ArffSaver saver = new ArffSaver();
        saveData(saver, data, filePath);
    }

    public void saveDataInCSVFormact(Instances data, Path filePath) throws IOException {
        CSVSaver saver = new CSVSaver();
        saveData(saver, data, filePath);
    }

    public void convertCSVToArff(Path pathOriginal, Path pathDestiny) throws IOException {
        Instances instances = this.readFileCSVWithWeka(pathOriginal);
        this.saveDataInArffFormact(instances, pathDestiny);
    }

    public List<String[]> readCSV(Path filePath) throws FileNotFoundException, IOException {
        List<String[]> lines;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.getValue()))) {
            lines = new LinkedList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.split(FileSettings.CSV_DIVISOR.getValue()));
            }
            System.out.println(lines.get(0).length);
        }
        return lines;
    }

    public void witerCSV(Path filePath, List<String[]> content) throws IOException {
        try (PrintStream pr = new PrintStream(new FileOutputStream(filePath.getValue()))) {
            content.forEach((strings) -> {
                for (int i = 0; i < strings.length; i++) {
                    if (i < strings.length - 1) {
                        pr.print(strings[i] + FileSettings.CSV_DIVISOR.getValue());
                    } else {
                        pr.println(strings[i]);
                    }
                }
            });

        }

    }

    private void saveData(Saver saver, Instances data, Path filePath) throws IOException {
        saver.setInstances(data);
        saver.setFile(new File(filePath.getValue()));
        saver.writeBatch();
    }

    private Instances loadData(Loader loader, Path filePath) throws IOException {
        loader.setSource(new File(filePath.getValue()));
        return loader.getDataSet();
    }
}
