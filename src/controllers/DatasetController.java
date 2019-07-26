package controllers;

import exceptions.CharacterNotFoundException;
import java.util.Enumeration;
import java.util.HashMap;
import util.Settings.DatasetId;
import weka.core.Attribute;
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

    public Instance getHeroByName(String name) throws CharacterNotFoundException {
        Instances dataset = getDataset(DatasetId.HEROES);
        Enumeration<Instance> enumerateInstances = dataset.enumerateInstances();

        while (enumerateInstances.hasMoreElements()) {
            Instance nextElement = enumerateInstances.nextElement();
            if (nextElement.stringValue(1).equals(name)) {
                return nextElement;
            }
        }
        throw new CharacterNotFoundException();
    }

    public Instances merge(Instances destinity, Instances toMerge) {
        toMerge.stream().filter((instance) -> (destinity.contains(instance))).forEachOrdered((instance) -> {
            destinity.add(instance);
        });
        return destinity;
    }

    public String[] getPossibleCharacterSuggestions() {
        Instances dataset = getDataset(DatasetId.HEROES);
        Enumeration<Object> values = dataset.attribute(1).enumerateValues();
        String[] allValues = new String[dataset.attribute(1).numValues()];
        for (int i = 0; values.hasMoreElements(); i++) {
            allValues[i] = ((String) values.nextElement()).replace("'", "");
        }
        return allValues;
    }

    public String[] PossibleSuperPowerSuggestions() {
        Instances dataset = getDataset(DatasetId.SUPER_POWER);
        Enumeration<Attribute> attributes = dataset.enumerateAttributes();
        String[] allAttributes = new String[dataset.numAttributes()];

        for (int i = 0; attributes.hasMoreElements(); i++) {
            Attribute attribute = attributes.nextElement();
            allAttributes[i] = attribute.name();
        }
        return allAttributes;

    }

}
