package one.empty3.feature;

import one.empty3.library.Point3D;


public class HarrisToPointInterest extends FilterPixM {
    private PixM m;
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
     
     public double filter(double x, double y) {
         int i=(int)x, j=(int)y;
         //double gxgx, gxgy, gygx, gygy;
         double gpgx = m.get(i+1, j)-m.get(i,j);
         double gpgy = m.get(i, j+1)-m.get(i,j);
         double gpgx1 = m.get(i, j)-m.get(i-1,j);
         double gpgy1 = m.get(i, j)-m.get(i,j-1);
         double gxgx = gpgx-gpgx1;
         double gygx = gpgy1-gpgx;
         double gxgy = gpgx1-gpgy;
         double gygy = gpgy1-gpgy;
         
         return (gxgx+gygy)/(gxgx*gygy-gxgy*gygx);
     }
    
    public List<Point3D> getPoi() {
        List<Point3D> poi = new ArrayList<>();
        
        return poi;
    }
}
