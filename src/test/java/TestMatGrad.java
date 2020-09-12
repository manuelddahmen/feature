import org.junit.Test;
import static org.junit.Assert.*;
import javax.imageio.ImageIO;
import java.io.File;
import one.empty3.feature.*;


public class TestMatGrad {
  @Test
  public void testMatGradAndDotProduct() {
   for(String fileStr :  new File("resources").list()) {
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
        
                GradientFilter gradientMask = new GradientFilter(pixMOriginal.getColumns(), pixMOriginal.getLines());
                M3 imgForGrad = new M3( pixMOriginal,
2, 2);
                M3 filter = gradientMask.filter(imgForGrad);
                PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);


//                    image1 = null;

                // Zero. +++Zero orientation variation.
                Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                        new PixM(pixMOriginal.getColumns(), pixMOriginal.getLines()));
                linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                PixM smoothedGrad = linear.getImages()[2];

     LocalExtrema le =
    new  LocalExtrema( imagesMatrix[1][0].getColumns(), 
                      imagesMatrix[1][0].getLines(),
                      3, 2);
  M3 pext = le.filter(new M3(imagesMatrix[0][0], 0, 0));
     
     
        WriteFile.writeNext(file.getName()+"/image reduite", pixMOriginal.normalize(0.,1.).getImage());
            WriteFile.writeNext(file.getName()+"/image gradient gx", imagesMatrix[0][0].normalize(0.,1.).getImage());
      WriteFile.writeNext(file.getName()+"/image gradient gy", imagesMatrix[1][0].normalize(0.,1.).getImage());
      WriteFile.writeNext(file.getName()+"/image gradient phase x", imagesMatrix[0][1].normalize(0.,1.).getImage());
      WriteFile.writeNext(file.getName()+"/image gradient phase y", imagesMatrix[1][1].normalize(0.,1.).getImage());
   WriteFile.writeNext(file.getName()+"/image gradients dot", smoothedGrad.normalize(0.,1.).getImage());
     WriteFile.writeNext(file.getName()+"/image extrema", pext.normalize(0.,1.).getImage());
     
     System.gc();
      }

}
}
