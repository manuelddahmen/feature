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

public class ExtractIntensityInfo {
    static String dirout="." ;
    String dirOut = null ;
    private 
       
File dir;
    private File file;
/*
    public ExtractIntensityInfo(
) {
        if (file != null)
            if (file.exists() && file.isDirectory())
                this.dir = file;
            else if (file.isFile())
                this.file = file;
    }*/
public String dir() {
    if(dirOut!=null) 
        return dirOut;
               
                   
                  dirOut = dirout
+"outputFiles/Extracts/" 
+System.currentTimeMillis()
               ;
new File(dirOut) 
   . mkdirs() ;
    return dirOut ;
    
   } 
    public void stream(File f) throws IOException {
        BufferedImage read = ImageIO.read(f);

        PixM pix = PixM.getPixM(read, 500.0);
        
        
        
//                    image2 = getImageFromDir(filename2);
             //   GradientFilter gradientMask = new GradientFilter(image1.getWidth(), image1.getHeight());
                PixM pixMOriginal = pix;

                BufferedImage img3 = pix.getImage();
                 

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
        
       // String s = "outputFiles/Extracts/";
     //   new File(s).mkdirs();
     double min = 0.3;
       // for(double rMin = 1.0; rMin<10; rMin*= 2.) {
           //for (min = 0.0; min <= 1.0; min += 0.1) {
               // BufferedImage img3 = new BufferedImage(read.getWidth(), read.getHeight(), BufferedImage.TYPE_INT_RGB);
               img3 = pix.getImage();

 PixM pix2 = smoothedGrad.copy();
                PixM out = new PixM(pix2.columns, pix2.lines);//??
                Histogram2 histogram = new Histogram2(pix2);
                
                    List<Histogram2.Circle> pointsOfInterest = histogram.getPointsOfInterest(rMin);

                

                

           String dirOut = dir() ;


               
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
           Color[] colors= new Color[histogram.numLevels];
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
                    out.setCompNo(0);
                    out.set((int) circle.x, (int) circle.y, circle.i) ;
                    out.setCompNo(2);
                    out.set((int) circle.x, (int) circle.y, circle.r) ;
                    Color color = colors[(int) ((circle.i-iSum[1])/(iSum[2]-iSum[1])*histogram.numLevels)];
                    Graphics graphics = img3.getGraphics();
                    graphics.setColor(color);
                    graphics.drawRect((int) (circle.x-10), (int) (circle.y-10), (int) (10), (int) (10));
                    //img3.setRGB((int) (circle.x), (int) (circle.y), color.getRGB());

                });
               
               
                
            out.normalize(0.0,1.0);
                
                    //File outputFile = new File(dirOut
                                               
                                               
                                               
                    //   + "/0test" + f.getName() + "min" + min + "+rMin"+rMin+".jpg");
                    File outputFile2 = new File(dirOut + "/1test" + f.getName() + "min" + min + "rMin2_"+rMin+".jpg");
                    WriteFile.writeNext(out.normalize(0.,1.).getImage(), "out");
                    WriteFile.writeNext(read, "original");
                    WriteFile.writeNext(img3, "result2");
                    
                   


                System.gc();
           // }
        }
    }

    public static void main(String[] args) {
        
         /*
        if (args.length > 1 && args[0] != null) {
            dir = new File( args[0]) ;
        
            file = new File( args[1]) ;
             }*/
        try {
            ExtractIntensityInfo extractIntensityInfo
      = new ExtractIntensityInfo() ;
            extractIntensityInfo.exec();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void exec() throws IOException {
        if (dir == null)
            dir = new File("resources/") ;
        if (file == null) {
            Arrays.stream(Objects.requireNonNull(dir.listFiles())).sequential().forEach(f -> {
                try {
                    if(f.getName().toLowerCase().endsWith(".jpg"))
                         stream(f);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
        else
            stream(file);
    }

}
