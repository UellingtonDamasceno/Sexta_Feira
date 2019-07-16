package controllers;

import java.util.LinkedList;
import java.util.List;
import util.Settings.FileSettings;
import util.Settings.Files;
import util.Settings.StandardValues;

/**
 *
 * @author Uellington Damasceno
 */
public class DataProcessingController {

    public List<String[]> standardizeValues(List<String[]> contentFile, Files fileType) {
        int standardNumber;
        LinkedList<String[]> linesToRemove = new LinkedList();

        if (fileType == Files.HEROES) {
            standardNumber = StandardValues.NUMBER_ATTRIBUTE_HERO.getValue();
            if (contentFile.get(0)[0].isEmpty()) {
                contentFile.get(0)[0] = "index";
            }
        } else {
            standardNumber = StandardValues.NUMBER_ATTRIBUTE_SUPER_POWER.getValue();
        }

        contentFile.forEach((String[] line) -> {
            if (line.length < standardNumber) {
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

}
