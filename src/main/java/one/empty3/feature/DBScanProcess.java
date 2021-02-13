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
DBSCAN(DB, distFunc, eps, minPts) {
    C := 0                                                  /* Cluster counter */
    for each point P in database DB {
        if label(P) ≠ undefined then continue               /* Previously processed in inner loop */
        Neighbors N := RangeQuery(DB, distFunc, P, eps)     /* Find neighbors */
        if |N| < minPts then {                              /* Density check */
            label(P) := Noise                               /* Label as Noise */
            continue
        }
        C := C + 1                                          /* next cluster label */
        label(P) := C                                       /* Label initial point */
        SeedSet S := N \ {P}                                /* Neighbors to expand */
        for each point Q in S {                             /* Process every seed point Q */
            if label(Q) = Noise then label(Q) := C          /* Change Noise to border point */
            if label(Q) ≠ undefined then continue           /* Previously processed (e.g., border point) */
            label(Q) := C                                   /* Label neighbor */
            Neighbors N := RangeQuery(DB, distFunc, Q, eps) /* Find neighbors */
            if |N| ≥ minPts then {                          /* Density check (if Q is a core point) */
                S := S ∪ N                                  /* Add new neighbors to seed set */
            }
        }
    }
}
where RangeQuery can be implemented using a database index for better performance, or using a slow linear scan:

RangeQuery(DB, distFunc, Q, eps) {
    Neighbors N := empty list
    for each point P in database DB {                      /* Scan all points in the database */
        if distFunc(Q, P) ≤ eps then {                     /* Compute distance and check epsilon */
            N := N ∪ {P}                                   /* Add to result */
        }
    }
    return N
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
