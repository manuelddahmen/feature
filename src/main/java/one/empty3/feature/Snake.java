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
        PixM pix2 = new PixM(pix.getColumns(),pix.getLines());
        List<Point3D> p = spline.getPoints().getElem();
       
        double sumOut = 0.0;
        double sumIn = 0.0;

        for(double t=0.; t<1.; t+=1./pix.getColumns()) {
            pix2.setCompNo(0);
            Point3D p = spline.calculerPoint3D(t);
            pix2.set((int)(p.getX((), (int)(p.getY()), 1.0);// si get(x,y)>0 ??? separer les courbes
        }       
        for(int i=0; i<pix.getColumns(); i++) {
            boolean pOut = true;
            for(int j=0; j<pix.getLines(); j++) {
                 if(pix2.get(i,j)==1.0) {
                     pOut = !pOut;
                     
                 } 
                 if(pOut) {
                     pix2.set(i,j,0.0);
                     avgOut += pix.get(i,j);
                     cptOut ++;
                 } else {
                    pix2.set(i,j,1.0);
                    avgIn += pix.get(i,j);
                    cptIn ++;
                 }

            }
        }
        avgOut /= cptOut;
        avgIn /= cptOut;

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
        return true;
    }
}
