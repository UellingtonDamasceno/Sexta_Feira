package facades;

import controllers.DataProcessingController;
import controllers.DatasetController;
import controllers.FileController;
import controllers.PredictionController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.util.List;
import util.Settings.Algorithms;
import util.Settings.DatasetId;
import util.Settings.Path;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend {

    private final DataProcessingController dataProcessingController;
    private final DatasetController datasetController;
    private final FileController fileController;
    private final PredictionController predictionController;
    
    public FacadeBackend() {
        this.dataProcessingController = new DataProcessingController();
        this.datasetController = new DatasetController();
        this.fileController = new FileController();
        this.predictionController = new PredictionController();
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
        
        datasetController.addDataset(DatasetId.HEROES, heroesFileArff);
        datasetController.addDataset(DatasetId.SUPER_POWER, superPowerFileArff);
        
    }
    public void calculateDistances(DatasetId datasetId, Instance referenceHero, Algorithms algorithm) throws IOException{
        datasetController.addDataset(DatasetId.SUPER_POWER, fileController.readFileArff(Path.SUPER_POWER_FILE_ARFF));
        Instances superPowerDataset = datasetController.getDataset(DatasetId.SUPER_POWER);
        predictionController.calculateDistances(superPowerDataset, referenceHero, algorithm);
    }
}
