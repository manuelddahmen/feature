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

            Random random = new Random();
            for(int i = 0; i<k; i++) {
                centroids.put(i, new double [] {
                        random.nextDouble()*pix.getColumns()-1,
                        random.nextDouble()*pix.getLines()-1,
                        0, 0, 0

                });
            }

            do {
                ex++;
                for (double[] coords : clusters.keySet()) {
                    int r = 0;
                    int distance = 1;
                    double[] x1;

                    for (int i = 0; i < k; i++) {
                        x1 = features.get(++r);
                        centroids.put(i, x1);
                    }
                    clusters = kmeans(distance, centroids, k);
                    double db[];
                    //reassigning to new clusters
                    for (int j = 0; j < k; j++) {
                        List<double[]> list = new ArrayList<>();
                        for (double[] key : clusters.keySet()) {
                            if (clusters.get(key) == j) {
                                list.add(key);
                            }
                        }
                        db = centroidCalculator(j, list);
                        centroids.put(j, db);


                    }

                }
                clusters.clear();
                clusters = kmeans(1, centroids, k);

            } while (ex < 10);

            //Calculate WCSS
            double wcss = 0;

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
            clusters.forEach((doubles, integer) -> centroids.forEach((i, db) -> {
                for (int j1 = 0; j1 < 3; j1++) {
                    pix2.setCompNo(j1);
                    pix2.setValues((int) (float) (db[0]),
                            (int) (float) (db[1]),
                            db[2], db[3], db[4]);
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
        double x[] = new double[5];
        double[] a2 = new double[5];
        double[] centroids = new double[5];
        for (int i = 0; i < a.size(); i++) {
            count = 0;

            for (int j = 0; j < 3; j++) {

                centroids[j] += a.get(i)[j];
                a2[j] += 1;

            }

        }


        for (int j = 0; j < 5; j++) {
            centroids[j] /= a2[j];
        }

        return centroids;

    }

    //method for putting features to clusters and reassignment of clusters.
    public Map<double[], Integer> kmeans(int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        double dist = 0.0;
        for (double[] x : features) {
            if(x.length!=5)
                System.out.println("Error kmeans");
            int k1 = -1;
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                //if (distance == 1) {
                dist = Distance.eucledianDistance(centroids.get(j), x);
                /*} else if (distance == 2) {
                    dist = Distance.manhattanDistance(centroids.get(j), x);
                }*/
                if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }

            }
            if(k1>=0)
                clusters.put(x, k1);
        }

        return clusters;

    }

}
