package controllers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import model.bean.Result;
import model.bean.OccurrenceTable;
import util.Algorithm;
import util.Settings.StandardValues;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class SimilarityController {
    
    private List<Result> calculate(Instances dataset, Instance referenceHero, Algorithm algorithm) {
        List<Result> relationship = new LinkedList();
        
        dataset.forEach((Instance instance) -> {
            OccurrenceTable table = tableGenerator(referenceHero, instance);
            double similarity = algorithm.calculate(table);
            relationship.add(new Result(instance.stringValue(1), similarity));
        });
        return relationship;
    }

    public List<Result> calculateDistances(Instances dataset, Instance referenceHero, Algorithm algorithm) {
        List<Result> list = calculate(dataset, referenceHero, algorithm);
        Collections.sort(list, Collections.reverseOrder());
        return list;        
    }

    private OccurrenceTable tableGenerator(Instance reference, Instance toCompare) {
        OccurrenceTable table = new OccurrenceTable();
        for (int current = StandardValues.NUMBER_ATTRIBUTE_HERO.getValue(); current < reference.numAttributes(); current++) {
            //System.out.println(toCompare.stringValue(current));
            String relation = reference.stringValue(current).substring(0, 1);
            relation += toCompare.stringValue(current).charAt(0);
            table.addInPosition(relation);
        }
        return table;
    }

}
