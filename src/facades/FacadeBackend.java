package facades;

import controllers.DataProcessingController;
import controllers.FileController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.util.List;
import util.Settings.Algorithms;
import util.Settings.DatasetId;
import util.Settings.Path;
import weka.core.Instances;

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

    public void initialize() throws IOException, ListIsEmpty {
        List<String> contentHeroFile = fileController.readCSV(Path.HEROES_CSV_ORIGINAL);
        List<String> contentSuperPowerFile = fileController.readCSV(Path.SUPER_POWER_CSV_ORIGINAL);
        
        List<String[]> HeroFilePreProcessed = dataProcessingController.standardizeValues(contentHeroFile, DatasetId.HEROES);
        List<String[]> SuperPowerFilePreProcessed = dataProcessingController.standardizeValues(contentSuperPowerFile, DatasetId.SUPER_POWER);        
        
        fileController.witerCSV(Path.HEROES_CSV_PRE_PROCESSED, HeroFilePreProcessed);
        fileController.witerCSV(Path.SUPER_POWER_CSV_PRE_PROCESSED, SuperPowerFilePreProcessed);
        
        Instances heroesFileArff = fileController.convertCSVToArff(Path.HEROES_CSV_PRE_PROCESSED, Path.HEROES_FILE_ARFF);
        Instances superPowerFileArff = fileController.convertCSVToArff(Path.SUPER_POWER_CSV_PRE_PROCESSED, Path.SUPER_POWER_FILE_ARFF);
        
    }
    public void calculateDistances(Algorithms algorithm) throws IOException{
        Instances superPowerFileArff = fileController.readFileArff(Path.SUPER_POWER_FILE_ARFF);
        dataProcessingController.calculateDistances(superPowerFileArff, superPowerFileArff.get(1), algorithm);
    }
}
