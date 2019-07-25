package controllers;

import java.util.LinkedList;
import java.util.List;
import model.bean.Dice;
import model.bean.Edge;
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
public class SimilarityController {

    private List<Edge> calculate(Instances dataset, Instance referenceHero, Algorithm algorithm) {
        List<Edge> relationship = new LinkedList();

        dataset.forEach((Instance instance) -> {
            OccurrenceTable table = tableGenerator(referenceHero, instance);
            double similarity = algorithm.calculate(table);
            relationship.add(new Edge(instance, similarity));
        });
        return relationship;
    }

    public List<Edge> calculateDistances(Instances dataset, Instance referenceHero, Algorithms algorithmType) {
        return calculate(dataset, referenceHero, algorithmFactory(algorithmType));      
    }

    private OccurrenceTable tableGenerator(Instance reference, Instance toCompare) {
        OccurrenceTable table = new OccurrenceTable();
        for (int current = 1; current < reference.numAttributes(); current++) {
            String relation = Double.toString(reference.value(current)).substring(0, 1);
            relation += Double.toString(toCompare.value(current)).charAt(0);
            table.addInPosition(relation);
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
