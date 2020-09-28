import org.junit.Test;
import static org.junit.Assert.*;
import javax.imageio.ImageIO;
import java.io.File;
import one.empty3.feature.*;
import java.util.logging.*; 

public class TestMatGrad {
   static Logger logger;
  static {
    logger 

            = Logger.getLogger(TestMatGrad.class.getName()); 

  }
  @Test
  public void testMatGradAndDotProduct() {
   for(String fileStr :  new File("resources").list()) {
     logger.info("start with " + fileStr);
    if(!fileStr.endsWith(".jpg"))
        continue;
     File file = new File("resources/"+fileStr);
    PixM pixMOriginal = null;
    try {
        pixMOriginal = PixM.getPixM(ImageIO.read(file), 500.0);
    } catch(Exception ex) {
        ex.printStackTrace();
      continue;
       // assertTrue(false);
      
     }
        logger.info("file loaded");
                GradientFilter gradientMask = new GradientFilter(pixMOriginal.getColumns(), pixMOriginal.getLines());
                M3 imgForGrad = new M3( pixMOriginal, 2, 2);
                M3 filter = gradientMask.filter(imgForGrad);
                PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);
logger.info("gradient computed");

//                    image1 = null;

                // Zero. +++Zero orientation variation.
                Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                        new PixM(pixMOriginal.getColumns(), pixMOriginal.getLines()));
                linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                PixM smoothedGrad = linear.getImages()[2];
logger.info("dot ootter product");
      PixM pext = pixMOriginal;
     LocalExtrema le =
    new  LocalExtrema( imagesMatrix[1][0].getColumns(), 
                      imagesMatrix[1][0].getLines(),
                      3, 2);
     le.filter(new M3(smoothedGrad
             .normalize(0.,1.).getImage()
                      , 1, 1));
     logger.info("local maximum");
     
      AfterGradientBeforeExtremum a 
        = new AfterGradientBeforeExtremum(3);
      M3 anglesTangente = a.filter(new M3(
        
        new PixM[][]
         {{
            pext, imagesMatrix[0][0], imagesMatrix[1][0]
         }}
      ));
logger.info("angles tangentes");
     PixM pix = smoothedGrad;
        IntuitiveRadialGradient i 
         = new IntuitiveRadialGradient(pix);
     i.setMax(2., 5., 2, 4);
        PixM rad = i.filter(pix);
     logger.info("radial orientation");
        WriteFile.writeNext(file.getName()+"image reduite", pixMOriginal.normalize(0.,1.).getImage());
            WriteFile.writeNext(file.getName()+"image gradient gx", imagesMatrix[0][0].normalize(0.,1.).getImage());
      WriteFile.writeNext(file.getName()+"image gradient gy", imagesMatrix[1][0].normalize(0.,1.).getImage());
      WriteFile.writeNext(file.getName()+"image gradient phase x", imagesMatrix[0][1].normalize(0.,1.).getImage());
      WriteFile.writeNext(file.getName()+"image gradient phase y", imagesMatrix[1][1].normalize(0.,1.).getImage());
   WriteFile.writeNext(file.getName()+"image gradients dot", smoothedGrad.normalize(0.,1.).getImage());
     WriteFile.writeNext(file.getName()+"image extrema", pext.normalize(0.,1.).getImage());
     WriteFile.writeNext(file.getName()+"image angles", anglesTangente.getImagesMatrix()[0][0].normalize(0.,1.).getImage());
     WriteFile.writeNext(file.getName()+"image radial grad", rad.normalize(0.,1.).getImage());
     
     System.gc();
      }

}
}
