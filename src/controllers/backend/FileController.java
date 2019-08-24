package controllers.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import util.Settings.Path;
import util.Settings.FileSettings;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.Loader;
import weka.core.converters.Saver;

/**
 *
 * @author Uellington Damasceno
 */
public class FileController {

    public Instances readFileArff(Path filePath) throws IOException {
        return this.loadData(new ArffLoader(), filePath);
    }

    public Instances convertCSVToArff(Path pathOriginal, Path pathDestiny) throws IOException {
        Instances instances = this.loadData(new CSVLoader(), pathOriginal);
        this.saveData(new ArffSaver(), instances, pathDestiny);
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
        File file = createFile(filePath);
        try (PrintStream pr = new PrintStream(new FileOutputStream(file))) {
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

    public void writerObject(Path filePath, Serializable object) throws FileNotFoundException, IOException {
        File destinityFile = this.createFile(filePath);
        destinityFile.createNewFile();
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(destinityFile));
        output.writeObject(object);
        output.close();
    }

    public Object readObject(Path filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath.getValue());
        if (file.length() > 0) {
            ObjectInputStream entrada;
            entrada = new ObjectInputStream(new FileInputStream(file));
            Object content = entrada.readObject();
            entrada.close();
            return content;
        }
        throw new IOException();
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

    public boolean allFileExist(List<String> allFiles) {
        boolean exist = true;
        for (String filePath : allFiles) {
            if (!(new File(filePath).exists())) {
                exist = false;
            }
        }
        return exist;
    }

    private File createFile(Path fullPath) throws IOException {
        File file = new File(fullPath.getValue());
        if (!file.exists()) {
            int directoryLimit = fullPath.getValue().lastIndexOf("\\");
            File directory = new File(fullPath.getValue().substring(0, directoryLimit));
            directory.mkdir();
            file.createNewFile();
        }
        return file;
    }
}
