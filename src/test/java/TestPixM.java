import org.junit.Test;
import static org.junit.Assert.*;

import one.empty3.feature;

public class TestPixM {
     @Test
     public void testPixMblack() {
         PixM p = new PixM(500, 500);
         WriteFile.write(p.getImage(), "black 500x500");
     }
}
