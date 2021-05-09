package one.empty3.feature;

import java.io.File;
import java.util.Objects;

public class Run extends Thread {
    @Override
    public void run() {
        String settingsPropertiesPath = "sets";

        FTPProcessFiles files = new FTPProcessFiles();/**
        for (String directoryName : new File(settingsPropertiesPath).list()) {
            files.settingsPropertiesPath = "" + directoryName;
*/
            files.settingsPropertiesPath = settingsPropertiesPath + File.separator + "aquarelle";


            files.loadArgsProps(files.settingsPropertiesPath);
            files.defaultProcess();
 //       }

    }

    public static void main(String[] args) {
        new Run().start();
    }
}
