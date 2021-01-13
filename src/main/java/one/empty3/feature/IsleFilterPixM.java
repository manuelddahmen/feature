package one.empty3.feature;

import java.awt.Color;


public class IsleFilterPixM 
         extends FilterPixM {
     private Color selColor;
     private Color oppositeColor;
     private double threshold:
     private float selComps[] = new float[4];
     private float oppComps[] = new float[4];
     public IsleFilterPixM(PixM pix) {
         super(pix);
     }
     public boolean selectPoint(int x, int y) {
         double [] sel = new double[3];
         getColor(x, y, sel));
         double d = 0;
         for(int i=0; i<3; i++)
             d += (sel[i]-selComps[i])
                  *(sel[i]-selComps[i]);
         return sqrt(d)<threshold?true:false;
     }
     public void setCValues(Color background, 
         Color sel, double threshold) {
       this.oppositeColor = background;
       this.selColor = sel;
       this.threshold = threshold;
       selColor.getColorComponens(this.selComps );
       this.oppColor.getColorComponens(this.oppComps ) ;

     }
     public void filter() {
     }
}
