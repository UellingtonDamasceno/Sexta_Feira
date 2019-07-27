package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import util.Settings.FileSettings;
import util.Settings.DatasetId;
import util.Settings.StandardValues;

/**
 *
 * @author Uellington Damasceno
 */
public class DataProcessingController {

    public List<String[]> standardizeValues(List<String> contentFile, DatasetId fileType) {
        List<String[]> contentFilePreProcessed = replaceEmpatyValues(contentFile);
        StandardValues standardNumber;

        if (fileType == DatasetId.HEROES) {
            contentFilePreProcessed = standardizeValuesHeroFile(contentFilePreProcessed);
            standardNumber = StandardValues.NUMBER_ATTRIBUTE_HERO;
        } else {
            //contentFilePreProcessed = replaceBinariesValues(contentFilePreProcessed);
            standardNumber = StandardValues.NUMBER_ATTRIBUTE_SUPER_POWER;
        }

        return standardization(contentFilePreProcessed, standardNumber);
    }

    private List<String[]> replaceBinariesValues(List<String[]> contentFile) {

        contentFile.forEach((String[] line) -> {
            for (int i = 0; i < line.length; i++) {
                line[i] = (line[i].equalsIgnoreCase("true"))
                        ? FileSettings.TRUE.getValue()
                        : (line[i].equalsIgnoreCase("false"))
                        ? FileSettings.FALSE.getValue()
                        : line[i];
            }
        });
        return contentFile;
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
        content.stream().map((string) -> string.replace(FileSettings.EMPATY.getValue(), ",-,")).forEachOrdered((string) -> {
            preProcessedContent.add(string.split(FileSettings.CSV_DIVISOR.getValue()));
        });
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
                    } else {
                        line[i] = line[i].trim();
                    }
                }
            }
        });
        linesToRemove.forEach(contentFile::remove);
        return contentFile;
    }

    private List<String[]> standardizeValuesHeroFile(List<String[]> contentFile) {
        if (contentFile.get(0)[0].trim().isEmpty()) {
            contentFile.get(0)[0] = "index";
        }
        return contentFile;
    }

}
