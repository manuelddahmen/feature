package one.empty3.feature;
import one.empty3.library.*;
/*

Sum [x+i, y+j, dist<sizeK] (I ij *
    gauss derivate ij) 

sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?


 Somme pondérée Gradient(i, j) * dérivée gauss(x-i, y-j) 
= M3 cols, lines, sigmas
 Map of vectors. 
*/
public class GradientMapFilter extends FilterPixM {
    private M3 map;
    

    public GradientMapFilter(PixM img, int [] sigmas) {
       super() ;
    }

    
    }
    
}
