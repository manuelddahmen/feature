package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/***
 * Segmentation
 */
public class Lines5 extends ProcessFile {


    ArrayList<Point3D> listTmpCurve = new ArrayList<>();
    ArrayList<Double> listTmpX = new ArrayList<>();
    ArrayList<Double> listTmpY = new ArrayList<>();
    ArrayList<Double> listTmpZ = new ArrayList<>();
    private PixM pixM;
    private double pz;
    private double py;
    private double px;
    private double distMax;
    private Random random = new Random();

    public Lines5() {
    }

    public List<Point3D> relierPoints(List<List<Point3D>> points, Point3D p0) {
        List<Point3D> list = new ArrayList<>();

        List<Point3D> p = points.get(0);

        for (int i = 0; i < p.size(); i++) {
            Point3D proche = proche(p0, p);
            if (proche == null)
                return list;
            else {
                p.remove(proche);
                list.add(proche);
            }
        }

        return list;
    }

    private Point3D proche(Point3D point3D, List<Point3D> p) {
        double dist = distMax;
        Point3D pRes = null;
        for (Point3D p2 : p) {
            if (Point3D.distance(point3D, p2) < dist && p2 != point3D && !p2.equals(point3D)) {
                dist = Point3D.distance(point3D, p2);
                pRes = p2;
            }
        }
        return pRes;
    }

    public double r() {
        return (random.doubles().iterator().nextDouble() + 1.) / 2;
    }

    @Override
    public boolean process(File in, File out) {
        try {
            pixM = null;
            pixM = new PixM(ImageIO.read(in));
            ArrayList<List<Point3D>> lists = new ArrayList<>();
            lists.add(new ArrayList<>());
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());

            double valueDiff = 0.1;

            int[][] p = new int[pixM.getColumns()][pixM.getLines()];//!!

            for (double levels : Arrays.asList(1.0, 0.9, 0.8, 0.7, 0.6/*, 0.5, 0.4, 0.3, 0.2,0.1,0.0*/)) {


                pz = 0.0;
                py = 0.0;
                px = 0.0;
                distMax = (pixM.getColumns() + pixM.getLines()) >> 1;//???
                random = new Random();
                listTmpCurve = new ArrayList<Point3D>();
                listTmpX = new ArrayList<Double>();
                listTmpY = new ArrayList<Double>();
                listTmpZ = new ArrayList<Double>();


                for (int x = 0; x < pixM.getColumns(); x++)
                    for (int y = 0; y < pixM.getLines(); y++)
                        p[x][y] = 0;

                for (int i = 0; i < pixM.getColumns(); i++) {
                    for (int j = 0; j < pixM.getLines(); j++) {


                        int x = i;
                        int y = j;
                        if (!isInBound(new Point3D((double) x, (double) y, 0.0)))
                            continue;
                        double valueAvg = pixM.luminance(x, y);

                        if (p[x][y] == 0) {
                            listTmpCurve.add(new Point3D((double) x, (double) y, valueAvg));
                        } else {
                            continue;
                        }

                        int cont = 1;

                        while (valueAvg >= levels - valueDiff && valueAvg <= levels + valueDiff && cont == 1 && p[x][y] == 0) {//2nd condition

                            p[x][y] = 1;


                            neighborhood((int) (double) x, (int) (double) y, valueAvg, valueDiff, levels);
//
                            while (listTmpX.size() > 0) {
                                getTmp(0);
                                x = (int) px;
                                y = (int) py;
                                removeTmp(0);
                                if (!isInBound(new Point3D(px, py, 0.0)))
                                    break;

                                if (p[x][y] == 0) {
                                    listTmpCurve.add(new Point3D((double) x, (double) y, levels));

                                    cont = 1;

                                    valueAvg = pixM.luminance(x, y);

                                } else cont = 0;
                            }

                        }

                        if (listTmpCurve.size() == 1)
                            lists.get(0).add(listTmpCurve.get(0));
                        else if (listTmpCurve.size() > 1 && !lists.contains(listTmpCurve)) {
                            lists.add(listTmpCurve);
                            //listTmpCurve = new ArrayList<>();//!!
                        }
                    }
                }
            }

            ArrayList<Point3D> list2 = new ArrayList<Point3D>();


            for (List<Point3D> point3DS : lists) {
                list2.addAll(point3DS);
            }

            List<LineSegment> lines = new ArrayList<>();
            List<List<Point3D>> list3 = new ArrayList<>();

            for (Point3D point3D : list2) {
                final double distNormal = 1.1;//0.9??
                list3.add(new ArrayList<>());
                list3.get(list3.size() - 1).add(point3D);
                distMax = 0.0;

                if (isInBound(point3D)) {
                    int j = 0;

                    for (j = 0; j < list2.size(); j++) {
                        Point3D current = list2.get(j);
                        Point3D prev = list3.get(list3.size() - 1).get(
                                list3.get(list3.size() - 1).size() - 1);

                        if (prev != current && current != point3D &&
                                Point3D.distance(prev, current) <= distNormal &&
                                Point3D.distance(point3D, current) >= distMax) {
                            list3.get(list3.size() - 1).add(current);
                        }

                    }
                }

                if (list3.get(list3.size() - 1).size() < 2) {
                    list3.remove(list3.size() - 1);
                }
            }


            BufferedImage bLines = new BufferedImage(o.getColumns(), o.getLines(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bLines.getGraphics();

            g.setColor(Color.RED);
            list3.forEach(point3DS -> {
                Point3D p1 = point3DS.get(0);
                Point3D p2 = point3DS.get(point3DS.size() - 1);

                g.drawLine((int) (double) p1.get(0),
                        (int) (double) p1.get(1),
                        (int) (double) p2.get(0),
                        (int) (double) p2.get(1));
            });

            ImageIO.write(bLines, "jpg", out);
            return true;
        } catch (
                IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean isInBound(Point3D p1) {
        return p1.get(0) >= 0 && p1.get(0) < pixM.getColumns() && p1.get(1) >= 0 && p1.get(1) < pixM.getLines();
    }

    public void addTmp(double x, double y, double z) {
        listTmpX.add(x);
        listTmpY.add(y);
        listTmpZ.add(z);
    }

    public void removeTmp(int i) {
        listTmpX.remove(i);
        listTmpY.remove(i);
        listTmpZ.remove(i);
    }

    public void getTmp(int i) {
        px = listTmpX.get(i);
        py = listTmpY.get(i);
        pz = listTmpZ.get(i);
    }

    private void neighborhood(int i, int j, double valueAvg, double valueDiff, double valueMin) {
        listTmpX.clear();
        listTmpY.clear();
        listTmpZ.clear();
        listTmpCurve.clear();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int x2 = i + (x - 1);
                int y2 = j + (y - 1);
                if (x2 != i && y2 != j) {
                    Point point = new Point(x2, y2);
                    px = point.getX();
                    py = point.getY();
                    pz = pixM.luminance((int) point.getX(), (int) point.getY());
                    if (pz >= valueAvg - valueDiff && pz <= valueAvg + valueDiff && pz > valueMin
                            && px >= 0.0 && px < pixM.getColumns() && py >= 0 && py < pixM.getLines()) {
                        addTmp(px, py, pz);
                        break;
                    }
                }
            }
        }
    }

    public double getDistMax() {
        return distMax;
    }

    public void setDistMax(double distMax) {
        this.distMax = distMax;
    }
}
