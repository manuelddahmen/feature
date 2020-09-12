import org.junit.Test;
import static org.junit.Assert.*;
import javax.imageio.ImageIO;
import java.io.File;
import one.empty3.feature.*;


public class TestMatGrad {
  @Test
  public void testMatGradAndDotProduct() {
    PixM pixMOriginal = null;
    try {
        pixMOriginal = new PixM(ImageIO.read(
          new File("resources/vg1.jpg")));
    } catch(Exception ex) {
        ex.printStackTrace();
        assertTrue(false);
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
        WriteFile.writeNext("vg1.jpg image reduite", pixMOriginal.getImage());
            WriteFile.writeNext("vg1.jpg image gradient gx", imageMatrix[0][0].getImage());
      WriteFile.writeNext("vg1.jpg image gradient gy", imagesMatrix[1][0].getImage());
      WriteFile.writeNext("vg1.jpg image gradient phase x", imagesMatrix[0][1].getImage());
      WriteFile.writeNext("vg1.jpg image gradient phase y", imagesMatrix[1][1].getImage());
      

}
}
