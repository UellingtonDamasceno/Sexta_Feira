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
    
    private OccurrenceTable occurrenceTableGerator(Instances dataset, Instance referenceHero) {
        OccurrenceTable table = new OccurrenceTable();
        dataset.forEach((Instance instance) -> {
            for (int current = 1; current < referenceHero.numAttributes(); current++) {
                String relation = Double.toString(referenceHero.value(current)).substring(0, 1);
                relation += Double.toString(instance.value(current)).charAt(0);
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
        });
        return table;
    }
    
    public double calculateDistances(OccurrenceTable table, Algorithms algorithmType) {
        Algorithm algorithm = algorithmFactory(algorithmType);
        return algorithm.calculate(table.getA(), table.getB(), table.getC(), table.getD());
    }
    
    private Algorithm algorithmFactory(Algorithms algorithmType){
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
