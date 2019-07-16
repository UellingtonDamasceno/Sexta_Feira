package facades;

import controllers.DataProcessingController;
import controllers.FileController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.util.List;
import util.Settings.Files;
import util.Settings.Path;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend {

    private final FileController fileController;
    private final DataProcessingController dataProcessingController;

    public FacadeBackend() {
        this.fileController = new FileController();
        this.dataProcessingController = new DataProcessingController();
    }

    public void initializeWithWeka() throws IOException {
        fileController.convertCSVToArff(Path.HEROES_CSV_PRE_PROCESSED, Path.HEROES_FILE_ARFF);
    }

    public void initialize() throws IOException, ListIsEmpty {
        List<String[]> contentHeroFile = fileController.readCSV(Path.HEROES_CSV_ORIGINAL);
        List<String[]> contentSuperPowerFile = fileController.readCSV(Path.SUPER_POWER_CSV_ORIGINAL);
        
        contentHeroFile = dataProcessingController.standardizeValues(contentHeroFile, Files.HEROES);
        contentSuperPowerFile = dataProcessingController.standardizeValues(contentSuperPowerFile, Files.SUPER_POWER);        
        
        fileController.witerCSV(Path.HEROES_CSV_PRE_PROCESSED, contentHeroFile);
        fileController.witerCSV(Path.SUPER_POWER_CSV_PRE_PROCESSED, contentSuperPowerFile);
        
        fileController.convertCSVToArff(Path.SUPER_POWER_CSV_PRE_PROCESSED, Path.SUPER_POWER_FILE_ARFF);
        fileController.convertCSVToArff(Path.HEROES_CSV_PRE_PROCESSED, Path.HEROES_FILE_ARFF);
    }
}
