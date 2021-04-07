package one.empty3.feature;

import one.empty3.io.ProcessFile;

import java.io.File;

import one.empty3.feature.kmeans.*;


public class KMeans extends ProcessFile {
    public boolean process(File in, File out) {

        // init centroids with random colored
        // points.
        try {
            new MakeDataset(in,
                    new File(out.getAbsolutePath() + ".csv"), -1);

            K_Clusterer.main(new String[]{
                            in.getAbsolutePath(),
                            out.getAbsolutePath() + ".csv", out.getAbsolutePath()
                    }, 50
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
