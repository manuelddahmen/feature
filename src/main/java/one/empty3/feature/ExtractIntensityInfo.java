package one.empty3.feature;

import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ExtractIntensityInfo extends
    ProcessFile {
    static String dirout="." ;
    String dirOut = null ;

      
               
        public void            
          process(File in, File out) {
            try {
   BufferedImage img = ImageIO.read(in);
                } catch(Exception rx){}
        PixM pix = PixM.getPixM(img, 500.0);
        
        
               PixM pixMOriginal = pix;

                final BufferedImage[] img3 = new BufferedImage[]{ pix.getImage()};
                 

                GradientFilter gradientMask = new GradientFilter(pixMOriginal.columns, pixMOriginal.lines);
                M3 imgForGrad = new M3( pixMOriginal,
2, 2);
                M3 filter = gradientMask.filter(imgForGrad);
                PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);


//                    image1 = null;

                // Zero. +++Zero orientation variation.
                Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                        new PixM(pixMOriginal.columns, pixMOriginal.lines));
                linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                PixM smoothedGrad = linear.getImages()[2];
        
       
    
     double min = 0.3;
        double rMin = 2.0;
       // for(double rMin = 1.0; rMin<10; rMin*= 2.) {
           //for (min = 0.0; min <= 1.0; min += 0.1) {
               // BufferedImage img3 = new BufferedImage(read.getWidth(), read.getHeight(), BufferedImage.TYPE_INT_RGB);
               img3[0] = pix.getImage();

 PixM pix2 = smoothedGrad.copy();
              pix2 = new PixM(pix2.columns, pix2.lines);//??
                Histogram2 histogram = new Histogram2(15);
                histogram.setM(pix2);
                    List<Histogram2.Circle> pointsOfInterest = histogram.getPointsOfInterest(rMin);

                

                

         


               
double [] iSum = {0.0, 0.0, 1.0};
pointsOfInterest.stream().filter(new Predicate<Histogram2.Circle>() {
                    @Override
                    public boolean test(Histogram2.Circle circle) {
                        iSum [0] += circle.i;
                        iSum[1] = Math.min(circle.i, iSum[1]);
                        iSum[2] = Math.max(circle.i, iSum[2]);
                        return true;
                    }
                }).forEach(circle -> {
                    circle.i = (circle.i - iSum[1] )/(iSum[2]-iSum[1]);
                });
           Color[] colors= new Color[15];
                for (int i = 0; i < colors.length; i++) 
                    colors[i] = Colors.random();
                    
                double finalMin = min;
                pointsOfInterest.stream().filter(new Predicate<Histogram2.Circle>() {
                    @Override
                    public boolean test(Histogram2.Circle circle) {
                        return circle.i > finalMin;
                    }
                }).forEach(circle -> {
                    //System.out.println(circle.toString());
                    pix.setCompNo(0);
                
                    pix.set((int) circle.x, (int) circle.y, circle.i) ;
                    pix.setCompNo(2);
                    pix.set((int) circle.x, (int) circle.y, circle.r) ;
                    Color color = colors[(int) ((circle.i-iSum[1])/(iSum[2]-iSum[1])*15)];
                    Graphics graphics = img3[0].getGraphics();
                    graphics.setColor(color);
                    graphics.drawRect((int) (circle.x-10), (int) (circle.y-10), (int) (10), (int) (10));

                });
               
               
                
            out.normalize(0.0,1.0);
                
                    //
                                               
                                               
                                               
                    ImageIO.write (pix.getImage(),
       "JPEG", out) ;
                    
                   


           
} 

   } 
