
package one.empty3.feature.pio;
import one.empty3.feature;
import java.io.File;
import java.util.*;
import one.empty3.library.Point3D;
/*** 
 * point sur images
 */
class PI {
     Point3D p;
     PixM image;
     File imageFile;
}

public abstract class Pio {
     public abstract void process(File
     in, File out);

     public abstract List<PI> getResults() ;
}
