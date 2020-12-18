package one.empty3.feature ;

public class TrueHarris extends FilterPixM {

public double filter(double x, double y) {
    int i=(int)(float)x, j=(int)(float)y;
double gx=get(i+1,j)-get(i,j), gy=get(i,j+1)+get(i,j);
double Sx2 = (  (get(i+1, j)-get(i,j)) -(get(i,j)-get(i-1,j)) )  *get(i,j) ;
double Sy2 = (  (get(i, j+1)-get(i,j)) -(get(i,j)-get(i,j-1)) )  *get(i,j) ; 
double Ix = gx*get(i, j);
double Iy = gy*get(i, j);
double Ix2 = Ix*Ix;
double Ixy = Ix*Iy;
double sSx2 = gx*Ix;
double sSy2 = gy*Iy;
double sSxy = Math.sqrt((gx+gy)/2*get(i, j)) ;// Robert Collins 

double r = (sSx2*sSy2-sSxy*sSxy) /(sSx2+sSy2);
             
   return r;
  } 
}
