package controllers;

import java.util.HashMap;
import util.Settings.DatasetId;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class DatasetController {

    private final HashMap<DatasetId, Instances> datasets;

    public DatasetController() {
        this.datasets = new HashMap();
    }

    public void addDataset(DatasetId id, Instances dataset) {
        datasets.put(id, dataset);
    }

    public Instances getDataset(DatasetId id) {
        return datasets.get(id);
    }

    public Instance getHeroByName(String name) {
        Instances datasetHero = getDataset(DatasetId.HEROES); 
//        Instance hero = datasetHero
        return null;
    }

    public Instances merge(Instances destinity, Instances toMerge) {
        toMerge.stream().filter((instance) -> (destinity.contains(instance))).forEachOrdered((instance) -> {
            destinity.add(instance);
        });
        return destinity;
    }

    private int getIndexHero(String name) {
        //getDataset(DatasetId.HEROES));
        return 0;
    }
}
