package one.empty3.feature;

public class HarrisToPointInterest extends FilterPixM {
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
     }
     public double filter(double x, double y) {
         //GradientFilter gf = new GradientFilter();
         //PixM gx = 
         return 0.0;
     }
}
