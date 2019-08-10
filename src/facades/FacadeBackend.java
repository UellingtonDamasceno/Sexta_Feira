package facades;

import controllers.backend.EnumerationController;
import exceptions.CharacterNotFoundException;
import controllers.backend.DataProcessingController;
import controllers.backend.DatasetController;
import controllers.backend.FileController;
import controllers.backend.SimilarityController;
import controllers.backend.PredictionController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import model.bean.Result;
import util.Algorithm;
import util.Settings.Algorithms;
import util.Settings.Dataset;
import util.Settings.FileSettings;
import util.Settings.Path;
import util.Settings.PredictionClasses;
import util.Settings.StandardValues;
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
    private final EnumerationController enumerationController;
    private final PredictionController predictionController;

    private static FacadeBackend facade;

    private FacadeBackend() {
        this.dataProcessingController = new DataProcessingController();
        this.datasetController = new DatasetController();
        this.fileController = new FileController();
        this.simmilarityController = new SimilarityController();
        this.enumerationController = new EnumerationController();
        this.predictionController = new PredictionController();
    }

    static public synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public boolean hasAlreadyBeenInitialized() {
        List<String> allPaths = enumerationController.takeAllRegistredFilePaths();
        return fileController.allFileExist(allPaths);
    }

    public boolean wasSuccessfullyClosed() throws ClassNotFoundException {
        try {
            FileSettings value = (FileSettings) fileController.readObject(Path.SETTINGS_FILE);
            return value.equals(FileSettings.TRUE);
        } catch (IOException ex) {
            return false;
        }
    }

    public void firstBoot() throws IOException, ListIsEmpty {
        List<String> contentHFile = fileController.readCSV(Path.HEROES_CSV_ORIGINAL);
        List<String> contentSPFile = fileController.readCSV(Path.SUPER_POWER_CSV_ORIGINAL);

        List<String[]> heroFilePP;
        List<String[]> superPowerFilePP;

        heroFilePP = dataProcessingController.standardize(contentHFile, Dataset.HEROES, StandardValues.NUMBER_ATTRIBUTE_HERO);
        superPowerFilePP = dataProcessingController.standardize(contentSPFile, Dataset.SUPER_POWER, StandardValues.NUMBER_ATTRIBUTE_SUPER_POWER);

        List<String[]> datasetMerge = dataProcessingController.merge(heroFilePP, superPowerFilePP);
        fileController.witerCSV(Path.HEROES_CSV_PRE_PROCESSED, heroFilePP);
        fileController.witerCSV(Path.SUPER_POWER_CSV_PRE_PROCESSED, superPowerFilePP);
        fileController.witerCSV(Path.DATASET_FILE_CSV, datasetMerge);

        Instances heroes = fileController.convertCSVToArff(Path.HEROES_CSV_PRE_PROCESSED, Path.HEROES_FILE_ARFF);
        Instances superPowers = fileController.convertCSVToArff(Path.SUPER_POWER_CSV_PRE_PROCESSED, Path.SUPER_POWER_FILE_ARFF);
        Instances dataset = fileController.convertCSVToArff(Path.DATASET_FILE_CSV, Path.DATASET_FILE_ARFF);

        datasetController.addDataset(Dataset.HEROES, heroes);
        datasetController.addDataset(Dataset.SUPER_POWER, superPowers);
        datasetController.addDataset(Dataset.SUPER_POWER_MERGE_HERO, dataset);
        
        fileController.writerObject(Path.SETTINGS_FILE, FileSettings.FALSE);
    }

    public void boot() throws IOException {
        Instances heroesFileArff = fileController.readFileArff(Path.HEROES_FILE_ARFF);
        Instances superPowerFileArff = fileController.readFileArff(Path.SUPER_POWER_FILE_ARFF);
        Instances superPowerMergeHero = fileController.readFileArff(Path.DATASET_FILE_ARFF);

        datasetController.addDataset(Dataset.HEROES, heroesFileArff);
        datasetController.addDataset(Dataset.SUPER_POWER, superPowerFileArff);
        datasetController.addDataset(Dataset.SUPER_POWER_MERGE_HERO, superPowerMergeHero);
    }

    public List<Result> calculateDistances(String name, Algorithms algorithmType) throws IOException, CharacterNotFoundException {
        Instances superPowerDataset = datasetController.getDataset(Dataset.SUPER_POWER_MERGE_HERO);
        Algorithm algorithm = enumerationController.algorithmFactory(algorithmType);
        Instance referenceHero = datasetController.getHeroByName(Dataset.SUPER_POWER_MERGE_HERO, 1, name);
        return simmilarityController.calculateDistances(superPowerDataset, referenceHero, algorithm);
    }

    public ObservableList<Algorithms> getAlgorithmsPossibilities() {
        return enumerationController.getAlgorithmsPossibilities();
    }

    public Instance getCharacterByName(String name) throws CharacterNotFoundException {
        return datasetController.getHeroByName(Dataset.SUPER_POWER_MERGE_HERO, 1, name);
    }

    public String[] getPossibleCharacterSuggestions() {
        return datasetController.getPossibleCharacterSuggestions();
    }

    public String[] getPossibleSuperPowerSuggestions() {
        return datasetController.PossibleSuperPowerSuggestions();
    }

    public void prediction(Dataset datasetId, PredictionClasses predictionClass) throws Exception {
        Instances dataset = datasetController.getDataset(datasetId);
        predictionController.prediction(dataset, predictionClass);
    }

    public void finalize() throws IOException {
        fileController.writerObject(Path.SETTINGS_FILE, FileSettings.TRUE);
    }
}
