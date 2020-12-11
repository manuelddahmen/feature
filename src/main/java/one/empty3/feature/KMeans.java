package one.empty3.feature ;

import one.empty3.io.ProcessFile;
import java.io.File ;
import one.empty3.feature.kmeans.*;


public class KMeans extends ProcessFile {
    public boolean process(File in, File out) {
        
        // init centroids with random colored
        // points.
        
        new MakeDataset(in,
           new File(out.getAbsolutePath()+".csv"));
        
        
        return true;
    }
}
