package facades;

import controllers.AlgorithmController;
import exceptions.CharacterNotFoundException;
import controllers.DataProcessingController;
import controllers.DatasetController;
import controllers.FileController;
import controllers.SimilarityController;
import controllers.PredictionController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import model.bean.Result;
import util.Algorithm;
import util.Settings.Algorithms;
import util.Settings.DatasetId;
import util.Settings.Path;
import util.Settings.PredictionClasses;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend {

    private final AlgorithmController algorithmController;
    private final DataProcessingController dataProcessingController;
    private final DatasetController datasetController;
    private final FileController fileController;
    private final SimilarityController simmilarityController;
    private final PredictionController predictionController;
    private static FacadeBackend facade;


    private FacadeBackend() {
        this.dataProcessingController = new DataProcessingController();
        this.datasetController = new DatasetController();
        this.fileController = new FileController();
        this.simmilarityController = new SimilarityController();
        this.algorithmController = new AlgorithmController();
        this.predictionController = new PredictionController();
    }

    static public synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public void initialize() throws IOException, ListIsEmpty, Exception {
        List<String> contentHeroFile = fileController.readCSV(Path.HEROES_CSV_ORIGINAL);
        List<String> contentSuperPowerFile = fileController.readCSV(Path.SUPER_POWER_CSV_ORIGINAL);

        List<String[]> heroFilePreProcessed = dataProcessingController.standardizeValues(contentHeroFile, DatasetId.HEROES);
        List<String[]> superPowerFilePreProcessed = dataProcessingController.standardizeValues(contentSuperPowerFile, DatasetId.SUPER_POWER);

        List<String[]> datasetMerge = datasetController.merge(heroFilePreProcessed, superPowerFilePreProcessed);

        fileController.witerCSV(Path.HEROES_CSV_PRE_PROCESSED, heroFilePreProcessed);
        fileController.witerCSV(Path.SUPER_POWER_CSV_PRE_PROCESSED, superPowerFilePreProcessed);
        fileController.witerCSV(Path.DATASET_FILE_CSV, datasetMerge);

        Instances heroesFileArff = fileController.convertCSVToArff(Path.HEROES_CSV_PRE_PROCESSED, Path.HEROES_FILE_ARFF);
        Instances superPowerFileArff = fileController.convertCSVToArff(Path.SUPER_POWER_CSV_PRE_PROCESSED, Path.SUPER_POWER_FILE_ARFF);
        Instances superPowerMergeHero = fileController.convertCSVToArff(Path.DATASET_FILE_CSV, Path.DATASET_FILE_ARFF);

        datasetController.addDataset(DatasetId.HEROES, heroesFileArff);
        datasetController.addDataset(DatasetId.SUPER_POWER, superPowerFileArff);
        datasetController.addDataset(DatasetId.SUPER_POWER_MERGE_HERO, superPowerMergeHero);

        predictionController.createTree(superPowerMergeHero, PredictionClasses.FLIGHT.getValue());
        predictionController.createTree(superPowerMergeHero, PredictionClasses.SUPER_STRENGTH.getValue());
        predictionController.createTree(superPowerMergeHero, PredictionClasses.ACCELERATED_HEALING.getValue());
        predictionController.createTree(superPowerMergeHero, PredictionClasses.ALIGNMENT.getValue());
        predictionController.createTree(superPowerMergeHero, PredictionClasses.INVISIBILITY.getValue());

    }

    public List<Result> calculateDistances(String name, Algorithms algorithmType) throws IOException, CharacterNotFoundException {
        Instances superPowerDataset = datasetController.getDataset(DatasetId.SUPER_POWER);
        Algorithm algorithm = algorithmController.algorithmFactory(algorithmType);
        Instance referenceHero = datasetController.getHeroByName(DatasetId.SUPER_POWER, 0, name);
        return simmilarityController.calculateDistances(superPowerDataset, referenceHero, algorithm);
    }

    public ObservableList<Algorithms> getAlgorithmsPossibilities() {
        return algorithmController.getAlgorithmsPossibilities();
    }

    public Instance getCharacter(String name) throws CharacterNotFoundException {
        return datasetController.getHeroByName(DatasetId.HEROES, 1, name);
    }

    public String[] getPossibleCharacterSuggestions() {
        return datasetController.getPossibleCharacterSuggestions();
    }

    public String[] getPossibleSuperPowerSuggestions() {
        return datasetController.PossibleSuperPowerSuggestions();
    }

    public void calculatePrediction() {

    }

}
