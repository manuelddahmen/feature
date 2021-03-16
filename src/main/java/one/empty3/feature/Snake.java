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
    }
    public void classification(PixM pix) {
        Point3D vecTan0, vecTan;
        Point3D vecNor0, vecNor;
        
        List<Point3D> p = spline.getPoints().getElem();
        for(double t=0.; t<1.; t+=1./pix.getColumns()) {
            
        }
        double sumOut = 0.0;
        double sumIn = 0.0
        
        PixM pix2 = new PixM(pix.getColumns(),pix.getLines());

        for(int i=0; i<pix.getColumns(); i++) {
            for(int j=0; j<pix.getLines(); j++) {
                 
            }
        }
    }
   
    public boolean process(File in, File out) {
        try {
            PixM pix = new PixM(ImageIO.read(in));
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
}
