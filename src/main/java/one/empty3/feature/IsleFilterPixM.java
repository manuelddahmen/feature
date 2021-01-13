package one.empty3.feature;

import java.awt.Color;


public class IsleFilterPixM 
         extends FilterPixM {
     private Color selColor;
     private Color oppositeColor;
     private double threshold:
     private float selComps[];
     private float oppComps[];
     public IsleFilterPixM(PixM pix) {
         super(pix);
     }
     public boolean selectPoint(int x, inty) {
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
