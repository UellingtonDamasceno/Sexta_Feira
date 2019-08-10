package controllers.backend;

import exceptions.CharacterNotFoundException;
import java.util.Enumeration;
import java.util.HashMap;
import util.Settings.Dataset;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class DatasetController {

    private final HashMap<Dataset, Instances> datasets;

    public DatasetController() {
        this.datasets = new HashMap();
    }

    public void addDataset(Dataset id, Instances dataset) {
        if(!datasets.containsKey(id)){
            datasets.put(id, dataset);
        }
    }

    public Instances getDataset(Dataset id) {
        return datasets.get(id);
    }

    public Instance getHeroByName(Dataset datasetId, int attribute, String name) throws CharacterNotFoundException {
        Instances dataset = getDataset(datasetId);
        Enumeration<Instance> enumerateInstances = dataset.enumerateInstances();
        while (enumerateInstances.hasMoreElements()) {
            Instance nextElement = enumerateInstances.nextElement();
            if (nextElement.stringValue(attribute).equals(name)) {
                return nextElement;
            }
        }
        throw new CharacterNotFoundException();
    }

    public String[] getPossibleCharacterSuggestions() {
        Instances dataset = getDataset(Dataset.SUPER_POWER_MERGE_HERO);
        Enumeration<Object> values = dataset.attribute(1).enumerateValues();
        String[] allValues = new String[dataset.attribute(1).numValues()];
        for (int i = 0; values.hasMoreElements(); i++) {
            allValues[i] = ((String) values.nextElement()).replace("'", "");
        }
        return allValues;
    }

    public String[] PossibleSuperPowerSuggestions() {
        Instances dataset = getDataset(Dataset.SUPER_POWER);
        Enumeration<Attribute> attributes = dataset.enumerateAttributes();
        String[] allAttributes = new String[dataset.numAttributes()];

        for (int i = 0; attributes.hasMoreElements(); i++) {
            Attribute attribute = attributes.nextElement();
            allAttributes[i] = attribute.name();
        }
        return allAttributes;
    }

}
