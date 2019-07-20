package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
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
        return this.loadData(new CSVLoader(), filePath);
    }

    public Instances readFileArff(Path filePath) throws IOException {
        return this.loadData(new ArffLoader(), filePath);
    }

    public void saveDataInArffFormact(Instances data, Path filePath) throws IOException {
        saveData(new ArffSaver(), data, filePath);
    }

    public void saveDataInCSVFormact(Instances data, Path filePath) throws IOException {
        saveData(new CSVSaver(), data, filePath);
    }

    public Instances convertCSVToArff(Path pathOriginal, Path pathDestiny) throws IOException {
        Instances instances = this.readFileCSVWithWeka(pathOriginal);
        this.saveDataInArffFormact(instances, pathDestiny);
        return instances;
    }

    public List<String> readCSV(Path filePath) throws FileNotFoundException, IOException {
        List<String> lines;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.getValue()))) {
            lines = new LinkedList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
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
