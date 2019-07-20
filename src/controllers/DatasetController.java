package controllers;

import java.util.HashMap;
import util.Settings.DatasetId;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class DatasetController {
    private HashMap<DatasetId, Instances> datasets;

    public DatasetController() {
        this.datasets = new HashMap();
    }

    public void addDataset(DatasetId id, Instances dataset) {
        datasets.put(id, dataset);
    }

    public Instances getDataset(DatasetId id) {
        return datasets.get(id);
    }
    
   
    
}
