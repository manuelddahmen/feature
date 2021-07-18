package one.empty3.feature.kmeans;
/*
 * Programmed by Shephalika Shekhar
 * Class for Kmeans Clustering implemetation
 */

import java.io.IOException;
import java.util.*;

import one.empty3.feature.*;
import one.empty3.library.Point3D;

import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;

public class K_Clusterer extends ReadDataset {
    static int k = 4;

    public K_Clusterer() {
        super();
    }

    //main method
    public void main(String args[], int res) throws IOException {

        final PixM pix;
        try {
            if (res > 0)
                pix = PixM.getPixM(ImageIO.read(new File(args[0])), res);
            else
                pix = new PixM(ImageIO.read(new File(args[0])));
            PixM pix2 = new PixM(
                    pix.getColumns(),
                    pix.getLines()
            );
            File out = new File(args[2]);
            //Scanner sc = new Scanner(System.in);
            //System.out.println("Enter the filename with path");
            String file = args[1];
            features.clear();
            read(file); //load data


            Map<double[], Integer> clusters = new HashMap<>();
            Map<Integer, double[]> centroids = new HashMap<>();

            int ex = 0;

            k = 4;

            int r =0;
            for (int i = 0; i < k; i++) {

                double[] x1 = features.get(r++);
                centroids.put(i, x1);

            }
            clusters = kmeans(1, centroids, k);
            int max_iterations = 10000;
            double db[] = new double[numberOfFeatures];

            for(int i = 0; i<max_iterations; i++) {
                    //reassigning to new clusters
                for (int j = 0; j < k; j++) {
                    List<double[]> list = new ArrayList<>();
                    for (double[] key : clusters.keySet()) {
                        if (clusters.get(key)==j) {
                            list.add(key);
//					for(int x=0;x<key.length;x++){
//						System.out.print(key[x]+", ");
//						}
//					System.out.println();
                        }
                    }
                    db = centroidCalculator(j, list);
                    centroids.put(j, db);

                }
                clusters.clear();
                clusters = kmeans(1, centroids, k);

            }
            double wcss = 0.0;
            for (int i = 0; i < k; i++) {
                double sse = 0;
                for (double[] key : clusters.keySet()) {
                    if (clusters.get(key) == i) {
                        sse += Math.pow(Distance.eucledianDistance(key, centroids.get(i)), 2);
                    }
                }
                wcss += sse;
            }

            double[] cs = new double[]{1.0, 1.0, 0.0};
            clusters.forEach((doubles, integer) -> centroids.forEach((i, db1) -> {
                for (int j1 = 0; j1 < 3; j1++) {
                    pix2.setCompNo(j1);
                    pix2.setValues((int) (float) (db1[0]),
                            (int) (float) (db1[1]),
                            db1[2], db1[3], db1[4]);
                }

            }));
            ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out);

            return;
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return;
        }

    }

    //method to calculate centroids
    public double[] centroidCalculator(int id, List<double[]> a) {

        int count = 0;
        //double x[] = new double[ReadDataset.numberOfFeatures];
        double sum=0.0;
        double[] centroids = new double[ReadDataset.numberOfFeatures];
        for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
            sum=0.0;
            count = 0;
            for(double[] x:a){
                count++;
                sum = sum + x[i];
            }
            centroids[i] = sum / count;
        }
        return centroids;
    }

    //method for putting features to clusters and reassignment of clusters.
    public Map<double[], Integer> kmeans(int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist=0.0;
        for(double[] x:features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                if(distance==1){
                    dist = Distance.eucledianDistance(centroids.get(j), x);
                }
                else if(distance==2){
                    dist = Distance.manhattanDistance(centroids.get(j), x);
                }
                if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }

            }
            clusters.put(x, k1);
        }

        return clusters;
    }

}
