package one.empty3.feature ;

public class LocalPattern extends FilterPixM {
    public LocalPattern (int n) {
        super(n, n) ;
    } 
    /***
    */
    public PixM searchSet(
           PixM mat, 
           M3 searchReplace, 
           ) {
         return mat.copy();
    } 
} 
