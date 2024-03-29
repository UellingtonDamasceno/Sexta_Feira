package controllers.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import util.Settings.FileSettings;
import util.Settings.Dataset;
import util.Settings.StandardValues;

/**
 *
 * @author Uellington Damasceno
 */
public class DataProcessingController {

    public List<String[]> standardize(List<String> contentFile, Dataset fileType, StandardValues value) {
        List<String[]> contentFilePreProcessed;
        if (fileType == Dataset.HEROES) {
            contentFilePreProcessed = replaceEmpatyValues(contentFile);
            if (contentFilePreProcessed.get(0)[0].trim().isEmpty()) {
                contentFilePreProcessed.get(0)[0] = "index";
            }
        }else{
            contentFilePreProcessed = new LinkedList();
            contentFile.forEach((string) -> {
                contentFilePreProcessed.add(string.split(FileSettings.CSV_DIVISOR.getValue()));
            });
        }
        return standardization(contentFilePreProcessed, value);
    }

    public List<String[]> merge(List<String[]> datasetA, List<String[]> datasetB) {
        ArrayList<String[]> result = new ArrayList();

        String[] header = new String[((datasetA.get(0).length) + (datasetB.get(0).length - 1))];

        System.arraycopy(datasetA.get(0), 0, header, 0, datasetA.get(0).length);
        System.arraycopy(datasetB.get(0), 1, header, datasetA.get(0).length, datasetB.get(0).length - 1);

        result.add(0, header);

        datasetA.forEach((contentA) -> {
            datasetB.stream().filter((contentB) -> (contentA[1].equals(contentB[0]))).map((contentB) -> {
                int length = contentA.length + (contentB.length - 1);
                String[] merge = new String[length];
                System.arraycopy(contentA, 0, merge, 0, contentA.length);
                System.arraycopy(contentB, 1, merge, contentA.length, contentB.length - 1);
                return merge;
            }).forEachOrdered((merge) -> {
                result.add(merge);
            });
        });
        return result;
    }

    private List<String[]> replaceEmpatyValues(List<String> content) {
        List<String[]> preProcessedContent = new LinkedList();
        content.stream().map(
                (string) -> string.replace(FileSettings.EMPATY.getValue(), ",-,")
        ).forEachOrdered(
                (string) -> {
                    preProcessedContent.add(string.split(FileSettings.CSV_DIVISOR.getValue()));
                }
        );
        return preProcessedContent;
    }

    private List<String[]> standardization(List<String[]> contentFile, StandardValues value) {
        LinkedList<String[]> linesToRemove = new LinkedList();
        contentFile.forEach((String[] line) -> {
            if (line.length < value.getValue()) {
                linesToRemove.add(line);
            } else {
                for (int i = 0; i < line.length; i++) {
                    if (line[i] == null || line[i].trim().isEmpty()) {
                        line[i] = FileSettings.STANDARD_CHARACTER.getValue();
                    } else if (line[i].contains(FileSettings.INVALID_CHARACTER.getValue())) {
                        line[i] = line[i].replace(FileSettings.INVALID_CHARACTER.getValue(), FileSettings.STANDARD_CHARACTER.getValue());
                    } else if(line[i].contains(FileSettings.NINE_NINE.getValue())){
                        linesToRemove.add(line);
                    } 
                    else {
                        line[i] = line[i].trim();
                    }
                }
            }
        });
        linesToRemove.forEach(contentFile::remove);
        return contentFile;
    }
}
