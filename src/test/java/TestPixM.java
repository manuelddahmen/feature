import org.junit.Test;
import static org.junit.Assert.*;

import one.empty3.feature.*;

public class TestPixM {
     @Test
     public void testPixMblack() {
         PixM p = new PixM(500, 500);
         WriteFile.writeNext(p.getImage(), "black 500x500");
         p = PixM.getPixM(new PixM(1000, 1000).getImage(), 500.0);
         WriteFile.writeNext(p.getImage(), "black 500x500 resized from 1000x1000");

    }
}
