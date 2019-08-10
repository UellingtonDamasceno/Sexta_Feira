package facades;

import controllers.backend.AlgorithmController;
import exceptions.CharacterNotFoundException;
import controllers.backend.DataProcessingController;
import controllers.backend.DatasetController;
import controllers.backend.FileController;
import controllers.backend.SimilarityController;
import controllers.backend.PredictionController;
import exceptions.ListIsEmpty;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import model.bean.Result;
import model.bean.ResultsTree;
import util.Algorithm;
import util.Settings.Algorithms;
import util.Settings.DatasetId;
import util.Settings.Path;
import util.Settings.PredictionClasses;
import weka.classifiers.trees.J48;
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
    private final AlgorithmController algorithmController;
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
        List<String[]> datasetMerge = dataProcessingController.merge(heroFilePreProcessed, superPowerFilePreProcessed);

        fileController.witerCSV(Path.HEROES_CSV_PRE_PROCESSED, heroFilePreProcessed);
        fileController.witerCSV(Path.SUPER_POWER_CSV_PRE_PROCESSED, superPowerFilePreProcessed);
        fileController.witerCSV(Path.DATASET_FILE_CSV, datasetMerge);

        Instances heroesFileArff = fileController.convertCSVToArff(Path.HEROES_CSV_PRE_PROCESSED, Path.HEROES_FILE_ARFF);
        Instances superPowerFileArff = fileController.convertCSVToArff(Path.SUPER_POWER_CSV_PRE_PROCESSED, Path.SUPER_POWER_FILE_ARFF);
        Instances superPowerMergeHero = fileController.convertCSVToArff(Path.DATASET_FILE_CSV, Path.DATASET_FILE_ARFF);

        datasetController.addDataset(DatasetId.HEROES, heroesFileArff);
        datasetController.addDataset(DatasetId.SUPER_POWER, superPowerFileArff);
        datasetController.addDataset(DatasetId.SUPER_POWER_MERGE_HERO, superPowerMergeHero);

    }

    public List<Result> calculateDistances(String name, Algorithms algorithmType) throws IOException, CharacterNotFoundException {
        Instances superPowerDataset = datasetController.getDataset(DatasetId.SUPER_POWER_MERGE_HERO);
        Algorithm algorithm = algorithmController.algorithmFactory(algorithmType);
        Instance referenceHero = datasetController.getHeroByName(DatasetId.SUPER_POWER_MERGE_HERO, 1, name);
        return simmilarityController.calculateDistances(superPowerDataset, referenceHero, algorithm);
    }

    public ObservableList<Algorithms> getAlgorithmsPossibilities() {
        return algorithmController.getAlgorithmsPossibilities();
    }

    public Instance getCharacterByName(String name) throws CharacterNotFoundException {
        return datasetController.getHeroByName(DatasetId.SUPER_POWER_MERGE_HERO, 1, name);
    }

    public String[] getPossibleCharacterSuggestions() {
        return datasetController.getPossibleCharacterSuggestions();
    }

    public String[] getPossibleSuperPowerSuggestions() {
        return datasetController.PossibleSuperPowerSuggestions();
    }

    public void treesTest() throws Exception {
        LinkedList<J48> florest = predictionController.florest();
        Instances dataset = datasetController.getDataset(DatasetId.SUPER_POWER_MERGE_HERO);
        List<ResultsTree> result = predictionController.classifier(florest, dataset, PredictionClasses.values());
        fileController.writerObject(Path.RESULTS_TREES, (Serializable) result);
    }
}
