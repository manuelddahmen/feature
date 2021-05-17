package one.empty3.feature;

import java.io.File;
import java.util.Objects;

import static org.bouncycastle.asn1.x509.GeneralName.directoryName;

public class Run extends Thread {
    static String [] args = new String[] {"aquarelle1"};
    @Override
    public void run() {
        String settingsPropertiesPath = "sets/";
        for (int i=0; i<args.length; i++) {

            File file = new File(settingsPropertiesPath + File.separator + (args[i]));
            if(file.exists()) {
                FTPProcessFiles.settingsPropertiesPath = file.getAbsolutePath();
                FTPProcessFiles.loadArgsProps(FTPProcessFiles.settingsPropertiesPath);
                FTPProcessFiles.defaultProcess();
            }
        }
    }

    public static void main(String[] args0) {
        if(args0.length>0)
            args = args0;
        new Run().start();
    }
}
