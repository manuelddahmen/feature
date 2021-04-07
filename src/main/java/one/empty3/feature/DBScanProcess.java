package one.empty3.feature;

import java.util.*;
import java.io.*;
import java.awt.image.*;

import one.empty3.io.*;
import one.empty3.feature.kmeans.*;

import javax.imageio.ImageIO;

public class DBScanProcess extends ProcessFile {
    public List<double[]> ns(List<double[]> points, double eps, double[] ps) {
        List<double[]> n = new ArrayList();
        for (double[] p : points) {
            if (distance(p, ps) < eps) {
                n.add(p);
            }
        }
        return n;
    }


    List<double[]> points;
    double[] size;
    HashMap<Integer, List<double[]>> clusters = new HashMap();
    HashMap<double[], Integer> centroids = new HashMap();
    int pointsMax = 10000;
    double eps = 1.0;
    int minPts;
    int c = 0;


    PixM pix;

    public void dbscan() {


        size = new double[]{
                pix.getColumns(), pix.getLines(), 1.0, 1.0, 1.0
        };
        int count = 0;
        while (count < pointsMax) {
            for (double[] p : points) {
                if (centroids.get(p) != null && centroids.get(p) > -1) {
                    List<double[]> n = ns(points, eps, p);
                    if (n.size() < minPts) {
                        centroids.put(p, -1);
                        continue;
                    }
                    c = c + 1;
                    centroids.put(p, c);
                }

                List<double[]> N = ns(points, eps, p);
                for (double[] q : N) {
                    if (N.size() > minPts) {
                        centroids.put(q, c);
                    } else {
                        centroids.put(q, -1);
                    }
                }
                count++;
            }
        }
    }

    public double distance(double[] p1,
                           double[] p2) {
        double d = 0.0;
        for (int i = 0; i < 5; i++)
            d += (p1[i] - p2[i]) * (p1[i] - p2[i]) / size[i] / size[i];
        return Math.sqrt(d);
    }

    public double[] density(List<double[]> cluster, double[] centroid) {
        double[] den = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        for (double[] item : cluster) {
            for (int i = 0; i < 5; i++)
                den[i] += item[i] / distance(item, centroid);
        }
        return den;
    }

    //main method
    public boolean process(File in, File out) {

        // points.
        try {
            new MakeDataset(in,
                    new File(out.getAbsolutePath() + ".csv"), -1);

            K_Clusterer.main(new String[]{
                            in.getAbsolutePath(),
                            out.getAbsolutePath() + ".csv", out.getAbsolutePath()
                    }, -1
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        try {
            pix = PixM
                    .getPixM(ImageIO.read(in), 100);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return false;
        }
        PixM pix2 = new PixM(
                pix.getColumns(),
                pix.getLines()
        );


        ReadDataset r1 = new ReadDataset();
        r1.features.clear();
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Enter the filename with path");
        String file = out.getAbsolutePath() + ".csv";
        try {
            r1.read(file); //load data
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        points = r1.features;


        dbscan();


        clusters.forEach((i, l) -> {
            for (double[] p : l)
                for (int j = 0; j < 3; j++) {
                    pix2.setCompNo(j);
                    pix2.set((int) (float) (p[0]),
                            (int) (float) (p[1]),
                            1.0 * p[j]);
                }
        });
        try {
            ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out);
        } catch (Exception ex1) {
            ex1.printStackTrace();

        }
        return true;
    }
}
