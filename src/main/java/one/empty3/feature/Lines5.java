package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.ColorTexture;
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
import java.util.concurrent.atomic.AtomicReference;

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

            for (double levels : Arrays.asList(1.0, 0.8, 0.6, 0.4, 0.3, 0.2/*,0.1,0.0*/)) {


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
                        /*
                        for (List<Point3D> ps : lists)
                            for (int k = 0; k < ps.size(); k++) {
                                Point3D p0 = ps.get(k);
                                for (int c = 0; c < listTmpCurve.size(); c++)
                                    if (listTmpCurve.get(c).equals(p0) &&
                                            lists.get(0).contains(p0)) {
                                        Point3D p1 = listTmpCurve.get(c);
                                        if (isInBound(p1))
                                            p[(int) (double) p1.getX()]
                                                    [(int) (double) p1.getY()] = 1;
                                        //listTmpCurve.remove(c);
                                        lists.get(0).remove(p0);
                                    }
                            }*/
                        if (listTmpCurve.size() == 1)
                            lists.get(0).add(listTmpCurve.get(0));
                        else if (listTmpCurve.size() > 1 && !lists.contains(listTmpCurve)) {
                            lists.add(listTmpCurve);
                            //listTmpCurve = new ArrayList<>();//!!
                        }
                    }
                }
            }


            List<List<Point3D>> lists2 = new ArrayList<>();
            int index3 = 0;
            while (index3 < lists.size() && lists.get(index3).size() == 0)
                index3++;
            if (index3 < lists.size() && lists.get(index3).size() > 0) {

                List<Point3D> point3DS = relierPoints(lists, lists.get(index3).get(0));
                int index = 0;
                do {
                    if (point3DS != null) {
                        index++;
                        lists2.add(point3DS);
                    }
                    while (index3 < lists.size() && lists.get(index3).size() == 0)
                        index3++;
                    if (index3 < lists.size()) {
                        point3DS = relierPoints(lists, lists.get(index3).get(index));
                    }
                } while (index3 < lists.size() && point3DS != null && point3DS.size() > 0 && index < lists.get(0).size() - 1);
            }

            List<LineSegment> lines = new ArrayList<>();
            List<List<Point3D>> list3 = new ArrayList<>();

            for (List<Point3D> listP : lists2) {
                int i = 0;
                int a = 0;
                int b = listP.size() - 1;
                list3.add(new ArrayList<>());
                if (listP.size() > 0) {
                    boolean passed = false;
                    list3.get(list3.size() - 1).add(listP.get(0));
                    for (Point3D point3D : listP) {
                        double distNormal = 1.0;//0.9??
                        if (isInBound(point3D)) {
                            int j = 0;
                            for (j = 1; j < listP.size(); j++) {
                                if (Point3D.distance(point3D, listP.get(j)) >= distNormal) {
                                    distNormal = Point3D.distance(point3D, listP.get(j));
                                    list3.get(list3.size() - 1).add(listP.get(j));
                                }  //j--;break;


                            }
                            if (j == listP.size()) {
                                break;
                            }
                            a = j - 1;
                            int k;
                            for (k = 0; k >= -listP.size(); k--) {
                                if (Point3D.distance(point3D, listP.get((listP.size() + k) % listP.size())) >= distNormal) {
                                    distNormal = Point3D.distance(point3D, listP.get((listP.size() + k) % listP.size()));
                                    list3.get(list3.size() - 1).add(listP.get((listP.size() + k) % listP.size()));
                                }
                            }
                            if (k == -listP.size()) {
                                break;
                            }
                            b = (k + 1 + listP.size()) % listP.size();

                            passed = true;
                        }

                    }
                    if (list3.get(list3.size() - 1).size() >= 2 && passed) {
                        Point3D p1 = list3.get(list3.size() - 1).get(a);
                        Point3D p2 = list3.get(list3.size() - 1).get(b);
                        list3.remove(list3.size() - 1);
                        list3.add(new ArrayList<>());
                        list3.get(list3.size() - 1).add(p1);
                        list3.get(list3.size() - 1).add(p2);

                    }
                }

            }

            List<Point3D> points = new ArrayList<>();

            List<Point3D> list4 = new ArrayList<>();
            for (int i = 0; i < list3.size(); i++) {
                List<Point3D> point3DS = list3.get(i);
                for (int i1 = 0; i1 < point3DS.size(); i1++) {
                    list4.add(point3DS.get(i1));
                }
            }


            ArrayList<Point3D> pointsCourants = new ArrayList<Point3D>();

            AtomicReference<Point3D> p1 = new AtomicReference<>(null);
            AtomicReference<Point3D> p2 = new AtomicReference<>(null);

            ColorTexture colorTexture = new ColorTexture(Color.WHITE);
            for (int i=0; i<list4.size(); i++) {
                p1.set(list4.get(i));
                points.add(p1.get());
                for (int j=0; j<list4.size(); j++) {
                    Point3D[][] extremes = {new Point3D[2], new Point3D[2]};
                    Double[] distMaxMinP1 = {2., 1000.0};
                    p2.set(list4.get(j));
                    if (!(p1.get()==p2.get())) {
                        if (Point3D.distance(p1.get(), p2.get()) <= distMaxMinP1[0] && isInBound(p1.get()) && isInBound(p2.get())) {
                            extremes[0][0] = p1.get();
                            extremes[0][1] = p2.get();
                            //distMaxMinP1[0] = Point3D.distance(p1.get(), p2.get());
                        }
                        else
                            continue;
                        if (Point3D.distance(p1.get(), p2.get()) >= distMaxMinP1[1] && isInBound(p1.get()) && isInBound(p2.get())) {
                            extremes[1][0] = p1.get();
                            extremes[1][1] = p2.get();
                            distMaxMinP1[1] = Point3D.distance(p1.get(), p2.get());
                            pointsCourants.add(p2.get());
                        }
                        else continue;
                    }
                    if(p1.get()!=null && p2.get()!=null
                            &&extremes[0][0]!=null && extremes[0][1]!=null) {
                        lines.add(new LineSegment(extremes[0][0], extremes[0][1],
                                colorTexture));
                        list4.remove(extremes[0][0]);
                        list4.remove(extremes[0][1]);
                    }
                }
            }


            BufferedImage bLines = new BufferedImage(o.getColumns(), o.getLines(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bLines.getGraphics();


            g.setColor(Color.GRAY);
            points.forEach(p12 -> {
                g.drawLine((int) (double) (p12.getX()), (int) (double) (p12.getY()),
                        (int) (double) (p12.getX()), (int) (double) (p12.getY()));
            });


            g.setColor(Color.WHITE);
            for (LineSegment line : lines) {
                if (line.getLength() >= 0.9) {
                    Point3D pDraw1 = line.getOrigine().plus(
                            line.getOrigine().plus(line.getExtremite().moins(line.getOrigine().mult(0.0))));
                    Point3D pDraw2 = line.getOrigine().plus(
                            line.getOrigine().plus(line.getExtremite().moins(line.getOrigine().mult(1.0))));
                    int x1 = (int) (double) pDraw1.getX();
                    int y1 = (int) (double) pDraw1.getY();
                    int x2 = (int) (double) pDraw2.getX();
                    int y2 = (int) (double) pDraw2.getY();
                    if (isInBound(pDraw1) && isInBound(pDraw2)) {
                        g.drawLine(x1, y1, x2, y2);
                    }
                }
            }

            g.setColor(Color.RED);
            /*list3.forEach(new Consumer<List<Point3D>>() {
                @Override
                public void accept(List<Point3D> point3DS) {
                    for (int i = 0; i < point3DS.size(); i++) {
                        if (i < point3DS.size() - 1) {
                            Point3D p0 = point3DS.get(i);
                            Point3D p1 = point3DS.get(i + 1);

                            g.drawLine((int) (double) p0.get(0),
                                    (int) (double) p0.get(1),
                                    (int) (double) p1.get(0),
                                    (int) (double) p1.get(1));
                        }
                    }
                }
            });*/
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
