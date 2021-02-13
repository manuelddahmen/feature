package one.empty3.feature;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import one.empty3.feature.io.*;


public class DBScan extends ProcessFile{
     public List<double> ns(List<double> points, double eps, double [] ps) {
   List<> n = new ArrayList();
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
            
                  K_Clusterer.main(new String[] {
                      in.getAbsolutePath(),
                        out.getAbsolutePath()+".csv", out.getAbsolutePath()
                     }, 50
              );
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
     }
     public static main(String [] args) {
           List<double[]> points ;

double [] size = new double[]{
          img.getWidth(), img.getHeight(), 1.0, 1.0, 1.0
     };
           HashMap<int, List<double>> clusters = new HashMap();
           HashMap<double[], int> centroids = new HashMap();
           int pointsMax = 100000;
           double eps = 1.0;
           int minPts;
int c = 0;
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
