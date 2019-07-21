package controllers;

import model.bean.Dice;
import model.bean.Jaccard;
import model.bean.OccurrenceTable;
import model.bean.SMC;
import util.Algorithm;
import util.Settings.Algorithms;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class PredictionController {

    private OccurrenceTable occurrenceTableGerator(Instances dataset, Instance referenceHero, Algorithm algorithm) {
        dataset.forEach((Instance instance) -> {
            OccurrenceTable table = this.relationGerator(referenceHero, instance);
            algorithm.calculate(table.getA(), table.getB(), table.getC(), table.getD());
        });
        return null;
    }

    public double calculateDistances(Instances dataset, Instance referenceHero, Algorithms algorithmType) {
        Algorithm algorithm = algorithmFactory(algorithmType);
        OccurrenceTable table = occurrenceTableGerator(dataset, referenceHero, algorithm);
        return 0;
    }

    private OccurrenceTable relationGerator(Instance reference, Instance toCompare) {
        OccurrenceTable table = new OccurrenceTable();
        for (int current = 1; current < reference.numAttributes(); current++) {
            String relation = Double.toString(reference.value(current)).substring(0, 1);
            relation += Double.toString(toCompare.value(current)).charAt(0);
            switch (relation) {
                case "00": {
                    table.addD();
                    break;
                }
                case "01": {
                    table.addB();
                    break;
                }
                case "10": {
                    table.addC();
                    break;
                }
                case "11": {
                    table.addA();
                    break;
                }
            }
        }
        return table;
    }

    private Algorithm algorithmFactory(Algorithms algorithmType) {
        switch (algorithmType) {
            case DICE: {
                return new Dice();
            }
            case JACCARD_COEFFICIENT: {
                return new Jaccard();
            }
            default: {
                return new SMC();
            }
        }
    }
}
