package one.empty3.io.ProcessFile;

import java.io.File;

public abstract class ProcessFile {
     public ProcessFile() {
     
     
     }
     
     public abstract boolean process(File in, File out);
}
