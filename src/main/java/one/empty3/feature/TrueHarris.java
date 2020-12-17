package one.empty3.feature ;

public class TrueHarris extends FilterPixM {
public TrueHarris(PixM pix){
     super(pix);
}
public double filter(double x, double y) {
    int i=(double)(float)x, j=(double)(float)y;
double gx=get(i+1)-get(i,j), gy=get(i,j+1)+get(i,j);
double Sx2 = (  (get(i+1, j)-get(i,j)) -(get(i,j)-get(i-1,j)) )  *get(i,j) ;
double Sy2 = (  (get(i, j+1)-get(i,j)) -(get(i,j)-get(i,j-1)) )  *get(i,j) ; 
double Ix = gx*get(i, j);
double Iy = gy*get(i, j);
Ix2 = Ix*Ix;
Ixy = Ix*Iy;
double Sx2 = gx*Ix;
double Sy2 = gy*Iy;
double Sxy = Math.sqrt((gx+gy)/2*get(i, j)) ;// Robert Collins 

double r = (Sx2*Sy2-Sxy*Sxy) /(Sx2+Sy2);
             
   return r;
  } 
}
