package controllers;

import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class PredictionController {
    private J48 tree;
    
    public PredictionController(){
        this.tree = new J48();
    }
    
    public void createTree(Instances instance) throws Exception{
        tree.listOptions();
    }
}
