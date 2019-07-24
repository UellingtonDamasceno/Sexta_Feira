package facades;

import controllers.DataProcessingController;
import controllers.DatasetController;
import controllers.FileController;
import controllers.SimilarityController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.util.List;
import model.bean.Edge;
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
    private final SimilarityController simmilarityController;
    private static FacadeBackend facade;
    
    private FacadeBackend() {
        this.dataProcessingController = new DataProcessingController();
        this.datasetController = new DatasetController();
        this.fileController = new FileController();
        this.simmilarityController = new SimilarityController();
    }
    
     static public synchronized FacadeBackend getInstance(){
        return (facade == null) ? facade = new FacadeBackend() : facade;
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
    public List<Edge> calculateDistances(Instance referenceHero, Algorithms algorithm) throws IOException{
        Instances superPowerDataset = datasetController.getDataset(DatasetId.SUPER_POWER);
        return simmilarityController.calculateDistances(superPowerDataset, referenceHero, algorithm);
    }
    
    public Instances getDataset(){
        return datasetController.getDataset(DatasetId.SUPER_POWER);
    }
}
