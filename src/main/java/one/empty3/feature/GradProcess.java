   package one.empty3.feature ;
   
   
   public class GradProcess {
   
   public boolean process(File in, File out)
    {
   
    if(!in.getName().endsWith(".jpg"))
        return false;
    File file = in;
    PixM pixMOriginal = null;
    try {
        pixMOriginal = PixM.getPixM(ImageIO.read(file), 500.0);
    } catch(Exception ex) {
        ex.printStackTrace();
      return false;
       // assertTrue(false);
      
     }
      
     GradientFilter gf = new GradientFilter (2, 2);
      
      PixM r = gf.filter(new M3(PixMOriginal, 1, 1)).getImagesMatrix()[0][0] ;
           try {
       ImageIO.write( r.normalize(0.0, 1.0). getImage(), "jpg", out);
     } catch (Exception ex){
         return false;
     }
     return true;
    } 
