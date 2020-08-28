package one.empty3.feature;

import one.empty3.library.Representable;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.Scene;
import one.empty3.library.core.export.STLExport;
import one.empty3.library.core.script.ExtensionFichierIncorrecteException;
import one.empty3.library.core.script.Loader;
import one.empty3.library.core.script.SceneLoader;
import one.empty3.library.core.script.VersionNonSupporteeException;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class HeadRepresentable extends Representable {
    public HeadRepresentable() {
        StringBuilder sb = new StringBuilder("scene(");
        Properties headRb = new Properties();
        try {
            headRb.load(new BufferedReader(new InputStreamReader(new FileInputStream(new File("resources/stl/head.properties")))));

            headRb.keySet().forEach(sb::append);

            sb.append(")");

            String s = sb.toString();

            Scene scene = new Scene();

            Scene load = new Loader().load(new File("resources/stl/head.stl"), scene);


            File sceneL = new File("resources/stl/head.moo");
            new PrintWriter(sceneL).println(load);
            File sceneXml = new File("resources/stl/head.xml");
            StringBuilder stringBuilder = new StringBuilder();
            scene.xmlRepresentation(null, stringBuilder, load);
            new PrintWriter(sceneXml).println(stringBuilder);
            File sceneStl = new File("resources/stl/head.stl");
            STLExport.save(sceneStl, load, true);
            new PrintWriter(sceneL).println(sb.toString());

            System.out.println(load);
        } catch (VersionNonSupporteeException | ExtensionFichierIncorrecteException | IOException e) {
            e.printStackTrace();
        }
    }
        public static void main(String [] args) {
            HeadRepresentable headRepresentable = new HeadRepresentable();
        }
}
