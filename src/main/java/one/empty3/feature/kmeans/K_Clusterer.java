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
import javax.imageio.ImageIO;

public class K_Clusterer extends ReadDataset {
    static int k = 8;

    public K_Clusterer() {
        // TODO Auto-generated constructor stub
    }

    //main method
    public static void main(String args[], int res) throws IOException {
        final PixM pix;
        try {
            if (res > 0)
                pix = PixM
                        .getPixM(ImageIO.read(new File(args[0])), res);
            else
                pix = new PixM(ImageIO.read(new File(args[0])));
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return;
        }
        PixM pix2 = new PixM(
                pix.getColumns(),
                pix.getLines()
        );

        File out = new File(args[2]);
        ReadDataset r1 = new ReadDataset();
        r1.features.clear();
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Enter the filename with path");
        String file = args[1];
        r1.read(file); //load data

        new Distance(pix.getColumns(),
                pix.getLines());

        Map<double[], Integer> clusters = new HashMap<>();
        Map<Integer, double[]> centroids = new HashMap<>();

        int max_iterations = 100000;
//System.out

        int ex = 0;
        double[] result = null;
        for (double[] coords : clusters.keySet()) {
            int numCluster = clusters.get(coords);
            int iter = 10000;
            
                int r = 0;

//System.out.println("Enter the no. of clusters");*/
                k = 4;
//System.out.println("Enter maximum iterations");
                //System.out.println("Enter distance metric 1 or 2: \n1. Euclidean\n2. Manhattan");
                int distance = 1;
                //Hashmap to store centroids with index
                // calculating initial centroids
                double[] x1 = new double[numberOfFeatures];
                for (int i = 0; i < k; i++) {


                    x1 = r1.features.get(++r);

                    centroids.put(i, x1);

                }
                //Hashmap for finding cluster indexes
                clusters = kmeans(r1.features, distance, centroids, k);
                // initial cluster print
	/*	for (double[] key : clusters.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.print(key[i] + ", ");
			}
			System.out.print(clusters.get(key) + "\n");
		}
		*/
                double db[] = new double[numberOfFeatures];
                //reassigning to new clusters
                for (int i = 0; i < max_iterations; i++) {
                    for (int j = 0; j < k; j++) {
                        List<double[]> list = new ArrayList<>();
                        for (double[] key : clusters.keySet()) {
                            if (clusters.get(key) == j) {
                                list.add(key);
//					for(int x=0;x<key.length;x++){
//						System.out.print(key[x]+", ");
//						}
//					System.out.println();
                            }
                        }
                        db = centroidCalculator(list);
                        centroids.put(j, db);

                    }
                    clusters.clear();
                    clusters = kmeans(features, distance, centroids, k);

                }

                //final cluster print
                System.out.println("\nFinal Clustering of Data");
                System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");

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
/*
		String dis="";
		if(distance ==1)
			 dis="Euclidean";
		else
			dis="Manhattan";
		System.out.println("\n*********Programmed by Shephalika Shekhar************\n*********Results************\nDistance Metric: "+dis);
		System.out.println("Iterations: "+max_iterations);
		System.out.println("Number of Clusters: "+k);
		System.out.println("WCSS: "+wcss);
		System.out.println("Press 1 if you want to continue else press 0 to exit..");
		//ex=sc.nextInt();*/
            

            result = coords;
        }
                 /*System.out.println("cluster no "
					   +numCluster+" centroid at ("+
					   coords[0]+" "+
					   coords[1]+")");
			    
				    */
        for (int j = 0; j < 3; j++) {
            pix2.setCompNo(j);
            pix.setCompNo(j);
            pix2.set(
                    (int) (float) (result[0]),
                    (int) (float) (result[1]),
                    //numCluster*1.0/k*0.5
                    pix.get((int) (float) (result[0]), (int) (float) (result[1])) / 2.0

            );
        }
        double[] cs = new double[]{1.0, 1.0, 0.0};
        centroids.forEach((i, db) -> {
            for (int j = 0; j < 3; j++) {
                pix2.setCompNo(j);
                pix2.set((int) (float) (db[0]),
                        (int) (float) (db[1]),
                        1.0 * cs[j]);
            }
        });
        try {
            ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return;
        }
        return;
    }

    //method to calculate centroids
    public static double[] centroidCalculator(List<double[]> a) {

        int count = 0;
        double x[] = new double[5];
        double sum = 0.0;
        double[] a2 = new double[5];
        double[] centroids = new double[5];
        for (int i = 0; i < a.size(); i++) {
            sum = 0.0;
            count = 0;

            for (int j = 0; j < a.get(i).length; j++) {

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
    public static Map<double[], Integer> kmeans(List<double[]> features, int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist = 0.0;
        for (double[] x : features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                if (distance == 1) {
                    dist = Distance.eucledianDistance(centroids.get(j), x);
                } else if (distance == 2) {
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
