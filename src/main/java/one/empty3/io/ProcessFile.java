package one.empty3.io;

import java.io.File;
import java.util.*;

public abstract class ProcessFile {
     List<PixM> listImage = new ArrayList<>(); 
     public ProcessFile() {
     
     
     }
     
     public abstract boolean process(File in, File out);
}
