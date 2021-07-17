package one.empty3.feature.kmeans;
/*
 * Programmed by Shephalika Shekhar
 * Class for Kmeans Clustering implemetation
 */

import java.io.IOException;
import java.util.*;

import one.empty3.feature.*;

import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;

public class K_Clusterer extends ReadDataset {
    static int k = 8;

    public K_Clusterer() {
        // TODO Auto-generated constructor stub
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
            ReadDataset r1 = new ReadDataset();
            //Scanner sc = new Scanner(System.in);
            //System.out.println("Enter the filename with path");
            String file = args[1];
            r1.features.clear();
            r1.read(file); //load data


            Map<double[], Integer> clusters = new HashMap<>();
            Map<Integer, double[]> centroids = new HashMap<>();

            int max_iterations = 100 * pix.getColumns() * pix.getLines();
//System.out

            int ex = 0;
            double[] result = null;
            do {
                ex++;
                for (double[] coords : clusters.keySet()) {
                    int numCluster = clusters.get(coords);
                    int r = 0;
                    k = 4;
                    int distance = 1;
                    double[] x1 = new double[numberOfFeatures];
                    for (int i = 0; i < k; i++) {


                        x1 = r1.features.get(++r);

                        centroids.put(i, x1);

                    }
                    distance = 1;
                    clusters = kmeans(features, distance, centroids, k);
                    double db[];
                    //reassigning to new clusters
                    for (int i = 0; i < pix.getColumns() * pix.getLines(); i++) {
                        for (int j = 0; j < k; j++) {
                            List<double[]> list = new ArrayList<>();
                            for (double[] key : clusters.keySet()) {
                                if (clusters.get(key) == j) {
                                    list.add(key);
                                }
                            }
                            db = centroidCalculator(list);
                            centroids.put(j, db);


                        }
                    }
                }
                clusters.clear();
                clusters = kmeans(features, 1, centroids, k);



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
            } while (ex < 1000);

            double[] cs = new double[]{1.0, 1.0, 0.0};
            clusters.forEach(new BiConsumer<double[], Integer>() {
                @Override
                public void accept(double[] doubles, Integer integer) {
                    centroids.forEach((i, db) -> {
                        for (int j = 0; j < 3; j++) {
                            pix2.setCompNo(j);
                            pix2.set((int) (float) (db[0]),
                                    (int) (float) (db[1]),
                                    1.0 * cs[j]);
                        }

                    });
                }
            });
            ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out);

            return;
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return;
        }

    }

    //method to calculate centroids
    public double[] centroidCalculator(List<double[]> a) {

        int count = 0;
        double x[] = new double[5];
        double[] a2 = new double[5];
        double[] centroids = new double[5];
        for (int i = 0; i < a.size(); i++) {
            count = 0;

            for (int j = 2; j < 3; j++) {

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
    public Map<double[], Integer> kmeans(List<double[]> features, int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist = 0.0;
        for (double[] x : features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                //if (distance == 1) {
                dist = Distance.eucledianDistance(centroids.get(j), x);
                /*} else if (distance == 2) {
                    dist = Distance.manhattanDistance(centroids.get(j), x);
                }*/
                /*if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }*/

            }
            clusters.put(x, k1);
        }

        return clusters;

    }

}
