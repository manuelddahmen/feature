package one.empty3.feature;


import java.io.*;
import java.util.*;
import one.empty3.io.ProcessFile;
import one.empty3.library.*;
/***
1 prendre les points entre t=0.0 et t=1.0
  extraire un polygone interieur et exterieur,
  comme gauche/droite.
2 calculer somme(i-moyenneI)2 a' chaque
  point.
3 de? 
***/
public class Snake {

    private BSpline spline;
    private List<double[]> in, out;

    private double energy() {
        double e;
        return e;

        List<Point3D> p = spline.getControlPoints();

        double sumOut;
        double sumIn;

        for(int i=0; i<pix.getColumns(); i++) {
            for(int j=0; j<pix.getLines(); j++) {
            }
        }
    }
    public void classification(PixM pix) {
    }
    public boolean process(File in, File out) {
        try {
            PixM pix = new PixM(ImageIO.read(in));
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
}
