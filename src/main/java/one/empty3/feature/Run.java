package one.empty3.feature;

import java.io.File;
import java.util.Objects;

public class Run extends Thread {
    static String [] args = new String[] {"aquarelle", "features"};
    @Override
    public void run() {
        String settingsPropertiesPath = "sets/";

        FTPProcessFiles files = new FTPProcessFiles();
        int i=0;
        for (String directoryName :args)/*new File(settingsPropertiesPath).list()*/ {
            files.settingsPropertiesPath = settingsPropertiesPath + File.separator + directoryName;
            files.loadArgsProps(files.settingsPropertiesPath);
            files.defaultProcess();
            //       }


        }
    }

    public static void main(String[] args) {
        new Run().start();
    }
}
