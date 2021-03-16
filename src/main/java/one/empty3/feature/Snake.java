package one.empty3.feature;


import java.io.*;
import java.util.*;
import one.empty3.io.ProcessFile;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.*;
import javax.imageio.ImageIO;
/***
1 prendre les points entre t=0.0 et t=1.0
  extraire un polygone interieur et exterieur,
  comme gauche/droite.
2 calculer somme(i-moyenneI)2 a' chaque
  point.
3 de? 
***/
public class Snake {

    private CourbeParametriquePolynomialeBezier spline;
    private List<double[]> in, out;

   PixM pix, pix3;
  

  
    public void classification(PixM pix) {
        double avgIn, avgOut;
        int cptIn, cptOut;
        Point3D vecTan0, vecTan;
        Point3D vecNor0, vecNor;
        PixM pix2 = new PixM(pix.getColumns(),pix.getLines());
        List<Point3D> p = spline.getCoefficients().getData1d();
       
        double sumOut = 0.0;
        double sumIn = 0.0;
        double energy = 0.0;
        for(double t=0.; t<1.; t+=1./pix.getColumns()) {
            pix2.setCompNo(0);
            Point3D p2 = spline.calculerPoint3D(t);
            pix2.set((int)(double)(p2.getX()), (int)(double)(p2.getY()), 1.0);// si get(x,y)>0 ??? separer les courbes
        }       
        for(int i=0; i<pix.getColumns(); i++) {
            boolean pOut = true;
            for(int j=0; j<pix.getLines(); j++) {
                 if(pix2.get(i,j)==1.0) {
                     pOut = !pOut;
                     
                 } 
                 if(pOut) {
                     pix2.set(i,j,0.0);
                     
                     out.add(M.getVector(1, new double[][] {pix.getValues(i,j), pix2.getValues(i,j)}));
                     
                     avgOut += pix.get(i,j);
                     cptOut ++;
                 } else {
                    pix2.set(i,j,1.0);
                    
                    in.add(M.getVector(0, new double [][]{pix.getValues(i,j), pix2.getValues(i,j)}));
                    
                    avgIn += pix.get(i,j);
                    cptIn ++;
                 }

            }
        }
        avgOut /= cptOut;
        avgIn /= cptIn;



        pix3 = new PixM(pix2.getColumns(), pix2.getLines());
 

        in.forEach( v -> {
           
            double e = Math.pow(pix.getIntensity((int)(v[0]),
(int)(v[1]))-avgIn, 2);
            pix3.set((int)(double)(v[0]), (int)(double)(v[1]), e);
            energy += e;
        });
             
        out.forEach(v -> {
              double e = Math.pow(pix.getIntensity((int)(v[0]),
(int)(v[1]))-avgIn, 2);
        
        pix3.set((int)(double)(v[0]), (int)(double)(v[1]), - e);
        energy -= e;
        });
    }
    
    public boolean process(File in, File out) {
        try {
            PixM pix = new PixM(ImageIO.read(in));
          pix3 = new PixM(pix.getColumns(), pix.getLines());
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }

       classification();

        try {
            ImageIO.write(pix3.getImage(),
               "jpg", out);
        } catch(Exception ex) {
            ex.printStackTrace();
          return false;
        }
        return true;
    }
}
