
  
import one.empty3.feature.*;


public class TestMatGrad {
  public void testMatGradDotProduct() {
    PixM pixMOriginal = new PixM(500, 500);
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
        WriteFile.writeNext("black image 500x500", pixMOriginal);
}
}
