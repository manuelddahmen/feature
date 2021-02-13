package one.empty3.feature;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import one.empty3.feature.io.*;
import one.empty3.feature.kmeans.*;

public class DBScanProcess extends ProcessFile {
     public List<double[]> ns(List<double[]> points, double eps, double [] ps) {
   List<double[]> n = new ArrayList();
   for(double[] p : points) {
        if(distance(p, ps)<eps) {
            n.add(p);
        }
   }
   return n;
}

public boolean process(File in, File out){
// init centroids with random colored
        // points.
        try {
             new MakeDataset(in,
                  new File(out.getAbsolutePath()+".csv"), 50);
            
                  main(new String[] {
                      in.getAbsolutePath(),
                        out.getAbsolutePath()+".csv", out.getAbsolutePath()
                     }, 50
              );
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
     }
	List<double[]> points ;
	double [] size;
	HashMap<Integer, List<double[]>> clusters = new HashMap();
           HashMap<double[], int> centroids = new HashMap();
           int pointsMax = 100000;
           double eps = 1.0;
           int minPts;
int c = 0;
     public void dbscan(String [] args) {
           

      size = new double[]{
          img.getWidth(), img.getHeight(), 1.0, 1.0, 1.0
     };
           
           while(count<pointsMax) {
                 for(double[] p : points) {
if(label(p)>-1) {
       List<double[]> n = ns(points, eps, p);
if(n.size()<minPts)
      {centroids.put(p, -1);
continue;
}
c=c+1;
centroids.put(p, c);
}

List<double[]> N = ns(points, eps, p);
for (double[] q : N) {
      if(ns(points, eps, q)>minPts) {
           centroids.put(q, c);
} else {
         centroids.put(q, -1);
}
}
                 count++;
           }
        }
     }
     public double distance(double [] p1,
          double [] p2) {
             double d =0.0;
             for(int i=0;i<5;i++)
                d+=  (p1[i]-p2[i])*(p1[i]-p2[i])/size[i]/size[i];
             return Math.sqrt(d);
      }
      public double[] density(List<double[]> cluster, double[] centroid) {
            double den = new double[]{0.0,0.0,0.0,0.0,0.0};
            for(double[] item : cluster) { 
               for(int i=0;i<5;i++)
                 den[i]+=item[i]/distance(item, centroid);
            }
           return den;
           }
     //main method
	public static void main(String args[], int res) throws IOException {
		final PixM pix;
		try {
         		pix = PixM
                  .getPixM( ImageIO.read(new File(args[0])), res);
     		} catch (Exception ex1){
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
		String file= args[1] ;
		r1.read(file); //load data
		
            List<double[]> db = r1.features;
          
          DBScanProcess pf = new DBScanProcess();
          pf.init();
          pf.process(new File(args[0]), out);
          
          centroids.forEach( (i, db) -> {
	    for(int j=0; j<3; j++) {
		pix2.setCompNo(j);
	        pix2.set((int)(float)(db[0]),
		    (int)(float)(db[1]),
	            1.0 * cs[j] );
         }
     });
          try {
       ImageIO.write( pix2.normalize(0.0, 1.0).getImage(), "jpg", out);
     } catch (Exception ex1){
	   ex1.printStackTrace();
         return;
     }
     return ;
}
}
