package one.empty3.io;

import java.io.File;

public abstract class ProcessFile {
     List<PixM> listImage = new ArrayList<>(); 
     public ProcessFile() {
     
     
     }
     
     public abstract boolean process(File in, File out);
}
