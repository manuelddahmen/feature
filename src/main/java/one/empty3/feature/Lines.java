package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lines extends ProcessFile {
    private PixM pixM;
    private double pz;
    private double py;
    private double px;
    private double distMax = 40.;
    private Random random = new Random();
    Point3D [][] mapPoints;

    public List<Point3D> relierPoints(List<List<Point3D>> points, Point3D p0) {
        List<Point3D> list = new ArrayList<>();

        List<Point3D> p = points.get(0);

/*        for (int i = 0; i < p.size(); i++) {
            Point3D proche = near(p0, mapPoints);
            if (proche == null)
                return list;
            else {
                p.remove(proche);
                list.add(proche);
            }
        }*/
        Point3D proche = near(p0, mapPoints);
        if (proche == null) {
            p.remove(proche);
            return list;
        } else {
            p.remove(proche);
            list.add(proche);
        }

        return list;
    }

    private Point3D near(Point3D point3D, List<Point3D> p) {
        double distMax1 = 10000;
        double dist = distMax;
        Point3D pRes = null;
        for (Point3D p2 : p) {
            if (Point3D.distance(point3D, p2) < distMax1 && p2!=point3D && !p2.equals(point3D)) {
                dist = Point3D.distance(point3D, p2);
                pRes = p2;
                if(dist<2.0)
                    return pRes;
            }
        }
        return pRes;
    }
    private Point3D near(Point3D p0, Point3D[][] map) {
        int distMax1 = 2;
        double dist = distMax;
        Point3D pRes = null;
        int x0 = (int)(double)p0.getX();
        int y0 = (int)(double)p0.getY();
        for (int i = Math.max(x0 - distMax1 / 2, 0); i<x0+distMax/2&&i>=0&&i<pixM.getColumns(); i++) {
            for (int j = Math.max(y0 - distMax1 / 2, 0); j<y0+distMax1/2&&j>=0&&j<pixM.getLines(); j++) {
                Point3D p2 = mapPoints[i][j];
                if (p2!=null&&Point3D.distance(p0, p2) < distMax1 && p2!=p0 && !p2.equals(p0)) {
                    dist = Point3D.distance(p0, p2);
                    pRes = p2;
                    map[i][j] = null;
                if(dist<2.0)
                    return pRes;
                }
            }
        }
        return pRes;
    }

    public double r() {
        return (random.doubles().iterator().nextDouble() + 1.) / 2;
    }
    @Override
    public boolean process(File in, File out) {
        ArrayList<List<Point3D>> lists = new ArrayList<List<Point3D>>();
        lists.add(new ArrayList<>());
        listTmpCurve = new ArrayList<Point3D>();
        try {
            pixM = new PixM(ImageIO.read(in));
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());

            int[][] p = new int[pixM.getColumns()][pixM.getLines()];
            mapPoints = new Point3D[pixM.getColumns()][pixM.getLines()];

            for (int x = 0; x < pixM.getColumns(); x++)
                for (int y = 0; y < pixM.getLines(); y++)
                    p[x][y] = 0;

            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    Point3D a = new Point3D((double) i, (double) j, pixM.luminance(i, j));



                    listTmpCurve.add(a);

                    double valueMin = 0.4;

                    double valueDiff = 0.1;


                    int x = i;
                    int y = j;

                    double valueAvg = pixM.luminance(x, y);

                    int cont = 1;

                    while (valueAvg >= valueMin && cont == 1) {
                        cont = 0;

                        p[x][y] = 1;

                        neighborhood((int) (double) x, (int) (double) y, valueAvg, valueDiff, valueMin);


                        if (listTmpX.size() < 1) {
                            cont = 0;
                        } else if (p[(int) px][(int) py] == 0) {
                            Point3D point3D = new Point3D(px, py, pz);
                            listTmpCurve.add(point3D);
                            p[(int) px][(int) py] = 1;
                            mapPoints[(int) px][(int) py] = point3D;


                            if (!(px >= 0 && px < pixM.getColumns() && py >= 0 && py < pixM.getLines()))
                                cont = 0;
                            x = (int) px;
                            y = (int) py;
                            cont = 1;
                        } else if (p[(int) px][(int) py] == 1) {
                            cont = 0;
                        }

                    }
                    for (List<Point3D> ps : lists)
                        for (Point3D p0 : ps)
                            for (int c = 0; c < listTmpCurve.size(); c++)
                                if (listTmpCurve.get(c).equals(p0)) {
                                    listTmpCurve.remove(c);
                                }

                    valueAvg = pixM.luminance(x, y);

                    if (listTmpCurve.size() == 1) {
                        lists.get(0).add(listTmpCurve.get(0));
                    } else if (listTmpCurve.size() > 1) {
                        lists.add(listTmpCurve);
                    }
                    for (Point3D point3D : listTmpCurve) {
                        mapPoints[(int)(double)point3D.getX()] [(int)(double)point3D.getY()] = point3D;
                    }
                }
            }




            List<List<Point3D>> lists2 = new ArrayList<>();
            List<Point3D> point3DS = relierPoints(lists, lists.get(0).get(0));
            int index = 0;
            do {
                if (point3DS != null) {
                    index++;
                    lists2.add(point3DS);
                }
                point3DS = relierPoints(lists, lists.get(0).get(index));
            } while (point3DS != null && point3DS.size() > 0 && index < lists.get(0).size() - 1);


            lists2.forEach(p3s -> {
                Color r = new Color((float)r(),(float)r(),(float)r());
                p3s.forEach(point3D -> {
                    o.setValues((int) (double) (point3D.getX()), (int) (double) (point3D.getY()), r.getRed() / 255., r.getGreen() / 255., r.getBlue() / 255.);
                });
            });
            ImageIO.write(o.normalize(0.0, 1.0).getImage(), "jpg", out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    ArrayList<Point3D> listTmpCurve = new ArrayList<Point3D>();
    ArrayList<Double> listTmpX = new ArrayList<Double>();
    ArrayList<Double> listTmpY = new ArrayList<Double>();
    ArrayList<Double> listTmpZ = new ArrayList<Double>();

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
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                int x2 = i + (x - 1);
                int y2 = j + (y - 1);
                if (x2 != i && y2 != j) {
                    Point point = new Point(x2, y2);
                    double px = point.getX();
                    double py = point.getY();
                    double pz = pixM.luminance((int) point.getX(), (int) point.getY());
                    if (pz >= valueAvg - valueDiff && pz <= valueAvg + valueDiff && pz > valueMin) {
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
