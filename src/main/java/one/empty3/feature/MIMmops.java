package one.empty3.feature;

/***
 * Multi-Image Matching using Multi-Scale Oriented Patches
 */
public class MIMmops {
    public PixM harris(PixM image, double sigma, int level ) {
        PixM res = null;
        for(int comp=0; comp<image.getCompCount(); comp++) {
            // Hl(x, y) = ∇σd Pl(x, y)∇σd Pl(x, y)T∗ gσi(x, y)
            // g      -> Gauss filter
            //  Sigma -> filter size
            //  level -> filter iterations
            // i      -> Iteration value of sigma (end condition?)
            // ∇σd Pl     -> Picture "derivative" (iteration x (gradient(image)))
            //               at level l and at sigma
            // ^T ??? -> Transpose smoothed matrix ?

            // La dérivée et le filtre ne sont pas les mêmes. sommeMatrice(e-..)   et
            // (get(x+1)-2*get(x)+get(x-1) + get(y+1)+2*get(y)-get(y))/4/4 ou /1/1 ??
            image.setCompNo(comp);

            image.filter(new GaussFilterPixM(3 /*** sigma */, sigma));
        }



        return res;
    }

    /***
     * fHM(x, y) = det Hl(x, y)
     * tr Hl(x, y)
     * =
     * λ1λ2
     * λ1 + λ2
     */
    public void cornerStrength(M harris)
    {

    }
}
