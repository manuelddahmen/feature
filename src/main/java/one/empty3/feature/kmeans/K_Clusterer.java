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

	public K_Clusterer() {
		// TODO Auto-generated constructor stub
	}
//main method
	public static void main(String args[]) throws IOException {
		PixM pix = new PixM(50,50);
		File out = new File(args[1]);
		ReadDataset r1 = new ReadDataset();
		r1.features.clear();
		//Scanner sc = new Scanner(System.in);
		//System.out.println("Enter the filename with path");
		String file= args[0] ;
		r1.read(file); //load data
		
		Map<double[], Integer> clusters = new HashMap<>();
		
		int ex=0;
		do{
			
//System.out.println("Enter the no. of clusters");*/
		int k = 8;
//System.out.println("Enter maximum iterations");
		int max_iterations = 1000;
//System.out.println("Enter distance metric 1 or 2: \n1. Euclidean\n2. Manhattan");
		int distance = 0;
		//Hashmap to store centroids with index
		Map<Integer, double[]> centroids = new HashMap<>();
		// calculating initial centroids
		double[] x1 = new double[numberOfFeatures];
		int r =0;
		for (int i = 0; i < k; i++) {
			
			x1=r1.features.get(r++);
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
					if (clusters.get(key)==j) {
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
			clusters = kmeans(r1.features,distance, centroids, k);
			
		}

		//final cluster print
		System.out.println("\nFinal Clustering of Data");
		System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");
/*for (double[] key : clusters.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.print(key[i] + "\t \t");
			}
			System.out.print(clusters.get(key) + "\n");
		}
*/
		//Calculate WCSS
		double wcss=0;
		
		for(int i=0;i<k;i++){
			double sse=0;
			for (double[] key : clusters.keySet()) {
				if(key.length>1) {
				double [] key1 = new double [] {key[0],
				  key[1]};
				if (clusters.get(key)==i) {
					sse+=Math.pow(Distance.eucledianDistance(key1, centroids.get(i)),2);
				}
				}
			}
			wcss+=sse;
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
		}while(ex==1);
		clusters.forEach(
	            (coords, numCluster) -> {
		         System.out.println("cluster no "
					   +numCluster+" centroid at ("+
					   coords[0]+" "+
					   coords[1]+")");
			    pix.set(
				    (int)(float)(coords[0]),
				    (int)(float)(coords[1]),
				    numCluster
			    
			    );
		    }
		     );

     try {
       ImageIO.write( pix.normalize(0.0, 1.0).getImage(), "jpg", out);
     } catch (Exception ex1){
	   ex1.printStackTrace();
         return;
     }
     return ;
	}
	
	//method to calculate centroids
	public static double[] centroidCalculator(List<double[]> a) {
		
		int count = 0;
		//double x[] = new double[ReadDataset.numberOfFeatures];
		double sum=0.0;
		double[] centroids = new double[ReadDataset.numberOfFeatures];
		for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
			sum=0.0;
			count = 0;
			int j=0;
			for(double[] x : a){
				if(j>1) {
					
					sum = sum + x[i];
					count++;
				}
				
				j++;
				
				
			}
			
			centroids[i] = sum / count/3;
		}
		return centroids;

	}

	//method for putting features to clusters and reassignment of clusters.
	public static Map<double[], Integer> kmeans(List<double[]> features,int distance, Map<Integer, double[]> centroids, int k) {
		Map<double[], Integer> clusters = new HashMap<>();
		int k1 = 0;
		double dist=0.0;
		for(double[] x : features) {
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
