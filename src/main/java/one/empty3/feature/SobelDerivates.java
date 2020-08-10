package one.empty3.feature;
/***
sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?
*/
public SobelDerivative extends FilterPixM{
     int[][] sobelX = new int[] {-1,-2,-1,0,0,0
                                ,1,2,1};
     int[][] sobelY = new int[] {-1,0,-1,
                                 -2,0,2
                                ,1,0,1};
     public SobelDerivate(int c, int l) {
          
          super(c, l);
     }
     public SobelDerivate(BufferedImage image) {
          super(image);
     }
     public double x(int i, int j) {
           return sobelX[i*lines + j];
     }
   public double y(int i, int j){
        return sobelY[i*lines + j];
   }
   public void theta (int i, int j) {
   
   }
}
