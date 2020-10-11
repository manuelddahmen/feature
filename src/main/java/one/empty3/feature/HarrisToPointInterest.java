package one.empty3.feature;

public class HarrisToPointInterest extends FilterPixM {
    private M3 m;
    public HarrisToPointInterest(int c, int l) {
        super(c, l);
    }
 // nabla
     // sobel x sobel y
     // ou gxgx gxgy 
     //    gygx gygy
     // p ij
     // mat Ip, Ip~
     // trace/determinant extract
     // m(p) at ij
     // localextrema filter
     // !=0 => points of interest
     public void init(PixM i) {
         //GradientFilter gf  = new GradientFilter(1, 1.2);
         //M3 mat2x2n = i.applyFilter(gf);
         m=i;
       //  GaussFilter.getPixM(5, 5);
         
         
     }
     public double filter(double x, double y) {
         int i=(int)x, j=(int)y;
         //double gxgx, gxgy, gygx, gygy;
         double gpgx = m.get(i+1, j,0,0)-m.get(i,j,0,0);
         double gpgy = m.get(i, j+1,0,0)-m.get(i,j,0,0);
         double gpgx1 = m.get(i, j,0,0)-m.get(i-1,j,0,0);
         double gpgy1 = m.get(i, j,0,0)-m.get(i,j-1,0,0);
         double gxgx = gpgx-gpgx1;
         double gygx = gpgy1-gpgx;
         double gxgy = gpgx1-gpgy;
         double gygy = gpgy1-gpgy;
         
         return (gxgx+gygy)/(gxgx*gygy-gxgy*gygx);
     }
}
