package facades;

import controllers.DataProcessingController;
import controllers.FileController;
import java.io.IOException;
import util.Settings.FilePath;
import weka.core.Instances;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeBackend {
    private final FileController fileController;
    private final DataProcessingController dataProcessingController;
    
    public FacadeBackend(){
        this.fileController = new FileController();
        this.dataProcessingController = new DataProcessingController();
    }
    
    public void initialize() throws IOException{
       Instances instance = fileController.readFileCSVWithWeka(FilePath.HEROES);
       fileController.saveDataInArffFormact(instance, FilePath.DATASET);
    }
}
