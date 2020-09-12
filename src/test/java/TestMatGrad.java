import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ImageIO;
  
import one.empty3.feature.*;


public class TestMatGrad {
  @Test
  public void testMatGradAndDotProduct() {
    try {
        PixM pixMOriginal = new PixM(ImageIO.read("resources/vg1.jpg"));
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
        WriteFile.writeNext("vg1.jpg image reduite 500x500", pixMOriginal.getImage());
}
}
