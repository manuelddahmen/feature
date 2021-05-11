package one.empty3.feature;

import java.io.File;
import java.util.Objects;

public class Run extends Thread {
    static String [] args = new String[] {"aquarelle", "features"};
    @Override
    public void run() {
        String settingsPropertiesPath = "sets/";
        int i=0;
        for (String directoryName : Objects.requireNonNull(new File(settingsPropertiesPath).list())) {
            FTPProcessFiles.settingsPropertiesPath = settingsPropertiesPath + File.separator + directoryName;
            FTPProcessFiles.loadArgsProps(FTPProcessFiles.settingsPropertiesPath);
            FTPProcessFiles.defaultProcess();
            //       }


        }
    }

    public static void main(String[] args) {
        new Run().start();
    }
}
