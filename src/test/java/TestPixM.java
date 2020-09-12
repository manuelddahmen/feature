import org.junit.Test;
import static org.junit.Assert.*;

import one.empty3.feature.*;

public class TestPixM {
     @Test
     public void testPixMblack() {
         PixM p = new PixM(500, 500);
         WriteFile.writeNext(p.getImage(), "black 500x500");
         PixM p2 = PixM.getPixM(new PixM(1000, 1000).getImage(), 500.0);
         WriteFile.writeNext(p.getImage(), "black 500x500 resized from 1000x1000");
         assertTrue(p.equals(p2));
    }
     
     @Test
     public void testPrimaryColors() {
         PixM p = new PixM(500, 500);
         colorsRegion(p, 0, 0, 250, 250, new double[]{1.0, 1.0, 1.0, 1.0});
         WriteFile.writeNext(p.getImage(), "white 500x500");
         PixM p2 = PixM.getPixM(new PixM(1000, 1000).getImage(), 500.0);
         colorsRegion(p, 0, 0, 250, 250, new double[]{1.0, 1.0, 1.0, 1.0});
         WriteFile.writeNext(p.getImage(), "white 500x500 resized from 1000x1000");
         assertTrue(p.equals(p2));
          
    }
     
     public void colorsRegion(PixM p, int x, int y, int w, int h, double[] comps) {
         for(int i=x; i<x-w; i++)
              for(int j=x; j<x-w; j++)
                   for(int c=0; c<p.getCompCount(); c++) {
                        p.setCompNo(c);
                        p.set(i, j, comps[c]);
                   }
     }
}
