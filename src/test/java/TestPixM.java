import one.empty3.feature;

public class TestPixM {
     public void testPixM() {
         PixM p = new PixM(500, 500);
         WriteFile.write(p.getImage(), "black 500x500");
     }
}
