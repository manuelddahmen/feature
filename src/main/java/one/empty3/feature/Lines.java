package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Lines extends ProcessFile {
    private PixM pixM;
    private ArrayList<List<Point3D>> lists;
    private double pz;
    private double py;
    private double px;

    @Override
    public boolean process(File in, File out) {
        lists = new ArrayList<List<Point3D>>();
        try {
            pixM = new PixM(ImageIO.read(in));
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());
            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    int listSize = 0;
                    int dist = 2;

                    listTmpCurve = new ArrayList<Point3D>();
                    listTmpCurve.add(new Point3D((double) i, (double) j, pixM.luminance(i, j)));
                    int cont = 0;
                    double valueDiff = 0.1;

                    double valueAvg = pixM.mean(i - dist / 2, j - dist / 2, dist + 1, dist + 1);
                    Point3D pNext = listTmpCurve.get(0);
                    while(true) {

                        if (valueAvg < 0.1) {
                            break;
                        }
                        neighborhood((int)(double)pNext.get(0), (int)(double)pNext.get(1), 2, valueAvg, valueDiff);
                        List<Point3D> points = new ArrayList<>();
                        if (listTmpX.size() <=1) {
                            break;
                        }
                        else {
                            listTmpCurve.add(new Point3D(px, py, pz));
                        }

                        for (List<Point3D> ps : lists)
                            for (Point3D p : ps)
                                for (int c = 0; c < listTmpCurve.size(); c++)
                                    if (listTmpCurve.get(c).equals(p))
                                        listTmpCurve.remove(c);

                        valueAvg = pixM.mean(i - dist / 2, j - dist / 2, dist + 1, dist + 1);

                    }

                    if (listTmpCurve.size() > 1)
                        lists.add(listTmpCurve);

                    System.gc();
                }
            }

            lists.forEach(point3DS -> {
                Color r = Colors.random();
                point3DS.forEach(point3D -> {
                    if(point3DS.size()>1)
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

    ArrayList<Point3D> listTmpCurve = new ArrayList();
    ArrayList<Double> listTmpX = new ArrayList();
    ArrayList<Double> listTmpY = new ArrayList();
    ArrayList<Double> listTmpZ = new ArrayList();

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

    private List<Point3D> neighborhood(int i, int j, int dist, double valueAvg, double valueDiff) {
        listTmpX.clear();
        listTmpY.clear();
        listTmpZ.clear();
        for (int x = 0; x < dist; x++) {
            for (int y = 0; y < dist; y++) {
                if (i + x != i && j + y != j) {
                    int x2 = i + (x - dist / 2);
                    int y2 = j + (y - dist / 2);
                    Point point = new Point(x2, y2);
                    double px = point.getX();
                    double py = point.getY();
                    double pz = pixM.luminance((int) point.getX(), (int) point.getY());
                    if (pz >= valueAvg - valueDiff && pz <= valueAvg + valueDiff) {
                        addTmp(px, py, pz);
                        break;
                    }
                }
            }
        }

        return null;
    }
}
