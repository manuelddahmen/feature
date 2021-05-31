package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.ColorTexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Lines3 extends ProcessFile {
    private PixM pixM;
    private double pz;
    private double py;
    private double px;
    private double distMax = 40.;
    private Random random = new Random();


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
        ArrayList<List<Point3D>> lists = new ArrayList<List<Point3D>>();
        lists.add(new ArrayList<>());
        listTmpCurve = new ArrayList<Point3D>();
        try {
            pixM = new PixM(ImageIO.read(in));
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());

            int[][] p = new int[pixM.getColumns()][pixM.getLines()];

            for (int x = 0; x < pixM.getColumns(); x++)
                for (int y = 0; y < pixM.getLines(); y++)
                    p[x][y] = 0;

            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {

                    double valueMin = 0.4;

                    double valueDiff = 0.1;


                    int x = i;
                    int y = j;

                    double valueAvg = pixM.luminance(x, y);

                    if (p[x][y] == 0)
                        listTmpCurve.add(new Point3D((double) x, (double) y, valueAvg));


                    int cont = 1;

                    while (valueAvg >= valueMin && cont == 1) {

                        p[x][y] = 1;


                        neighborhood((int) (double) x, (int) (double) y, valueAvg, valueDiff, valueMin);

                        if (listTmpX.size() > 0) {
                            getTmp(0);
                            x = (int) px;
                            y = (int) py;

                            if (!(x >= 0 && x < pixM.getColumns() && y >= 0 && y < pixM.getLines()))
                                break;

                            if (p[x][y] == 0) {
                                listTmpCurve.add(new Point3D((double) x, (double) y, pz));

                                cont = 1;

                                valueAvg = pixM.luminance(x, y);

                            } else cont = 0;
                        } else cont = 0;

                    }
                    for (List<Point3D> ps : lists)
                        for (Point3D p0 : ps)
                            for (int c = 0; c < listTmpCurve.size(); c++)
                                if (listTmpCurve.get(c).equals(p0))
                                    listTmpCurve.remove(c);
                    if (listTmpCurve.size() == 1)
                        lists.get(0).add(listTmpCurve.get(0));
                    else if (listTmpCurve.size() > 1)
                        lists.add(listTmpCurve);
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

            final double[][] xyMoyen = {new double[2]};
            double [] ab = new double[2];
            List<Point3D> moyennes = new ArrayList<>();
            lists2.forEach(p3s -> {
                xyMoyen[0][0] = 0.0;
                xyMoyen[0][1] = 0.0;

                final int[] i = {0};
                p3s.forEach(point3D -> {
                    if(isInBound(point3D)) {
                        xyMoyen[0][0] += point3D.getX();
                        xyMoyen[0][1] += point3D.getY();
                    }

                    i[0]++;

                });

                moyennes.add(new Point3D(xyMoyen[0][0]/lists2.get(i[0]).size(), xyMoyen[0][1]/lists2.get(i[0]).size(), 0.0));
            });
            List<double[]> coefficients = new ArrayList<>();

            lists2.forEach(p3s -> {
                xyMoyen[0][0] = 0.0;
                xyMoyen[0][1] = 0.0;
                final double[] b = {0};
                final double[] a = { 0 };
                final int[] i = {0};
                p3s.forEach(point3D -> {
                    b[0] += (point3D.getY() - moyennes.get(i[0]).getY())/(point3D.getX() - moyennes.get(i[0]).getX());
                    a[0] += moyennes.get(i[0]).getY() - b[0] *moyennes.get(i[0]).getX();

                });

                coefficients.add(new double[]{a[0], b[0]});
            });
            lists2.forEach(p3s -> {
                Color r = new Color((float) r(), (float) r(), (float) r());
                double xA;
                double yA;
                p3s.forEach(point3D -> {
                    if(isInBound(point3D)) {
                        o.setValues((int) (double) (point3D.getX()), (int) (double) (point3D.getY()), r.getRed() / 255., r.getGreen() / 255., r.getBlue() / 255.);
                    }


                });
            });


            List<List<Point3D>> points = new ArrayList<>();
            CourbeParametriquePolynomialeBezier[] courbeParametriquePolynomialeBeziers = new CourbeParametriquePolynomialeBezier[lists2.size()];

            int [] i =new int[] {0};
            lists2.forEach(p3s -> {
                Color r = new Color((float) r(), (float) r(), (float) r());
                final Point3D[][] extremes = {new Point3D[2], new Point3D[2]};
                final Double[] distMaxMinP1 = {2.5, 1000.0};
                List<Point3D> pointsCurrent = new ArrayList<>();
                points.add(pointsCurrent);

                p3s.forEach(point3D1 -> {
                    Point3D p1 = point3D1;
                    p3s.forEach(point3D2 -> {
                        Point3D p2 = point3D2;
                        if (Point3D.distance(p1, p2) >= distMaxMinP1[0] && isInBound(p1) && isInBound(p2)) {
                            extremes[0][0] = p1;
                            extremes[0][1] = p2;
                            distMaxMinP1[0] = Point3D.distance(p1, p2);
                        }
                        if (Point3D.distance(p1, p2) <= distMaxMinP1[1] && isInBound(p1) && isInBound(p2)) {
                            extremes[1][0] = p1;
                            extremes[1][1] = p2;
                            distMaxMinP1[1] = Point3D.distance(p1, p2);
                            pointsCurrent.add(p2);
                        }
                    });
                });
                if(extremes[0][0]!=null && extremes[0][1]!=null && isInBound(extremes[0][0]) && isInBound(extremes[0][1])){
                    lines.add(new LineSegment(extremes[0][0], extremes[0][1], new ColorTexture(r)));
                }
                // Calculer le segment AB qui approxime un maximum de points dans l'ensemble
                    /*b = (yB − yA)/(xB − xA); a = (yA − bxA). :?yA = yMoyen, xA = xMoyen*/

                // Recouper les lignes par db min max


                /*CourbeParametriquePolynomialeBezier parametricCurve = new CourbeParametriquePolynomialeBezier();
                p3s.forEach(point3D -> parametricCurve.getCoefficients().getData1d().add(point3D));
                courbeParametriquePolynomialeBeziers[i[0]++] = parametricCurve;
*/

                if(pointsCurrent.size()>2)
                    lines.add(new LineSegment(extremes[1][0], extremes[1][1], new ColorTexture(r)));

            });

            BufferedImage bLines = new BufferedImage(o.getColumns(), o.getLines(), BufferedImage.TYPE_INT_RGB);
            for (LineSegment line : lines) {
                if(line.getLength()>2) {
                    for (double c = 0.0; c <= 1.0; c += 1 / line.getLength()) {
                        Point3D pDraw = line.getOrigine().plus(
                                line.getOrigine().plus(line.getExtremite().moins(line.getOrigine().mult(c))));
                        int x = (int) (double) pDraw.getX();
                        int y = (int) (double) pDraw.getY();
                        if (isInBound(pDraw))
                            bLines.setRGB(x, y, line.texture().getColorAt(0, 0));
                    }
                }
            }
            points.forEach(new Consumer<List<Point3D>>() {
                @Override
                public void accept(List<Point3D> point3DS) {

                }
            });
            ImageIO.write(o.normalize(0.0, 1.0).getImage(), "jpg", out);
            ImageIO.write(bLines, "jpg",
                    new File(out.getAbsolutePath()+"-blines.jpg"));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isInBound(Point3D p1) {
        return p1.get(0)>=0&&p1.get(0)< pixM.getColumns()&&p1.get(1)>=0&&p1.get(1)<pixM.getLines();
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
                    && px>=0.0&&px<pixM.getColumns()&&py>=0&&py<pixM.getLines()) {
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
