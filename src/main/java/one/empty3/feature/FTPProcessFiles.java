package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.TextureMov;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
//import org.json.*;

/**
 * An example program that demonstrates how to list files and directories
 * on a FTP server using Apache Commons Net API.
 *
 * @author www.codejava.net
 * @author empty3.one
 */
public class FTPProcessFiles {
    public static String classnames, classname;
    static String[] classes;


    public static String directory = "images";
    static String settingsPropertiesPath;
    private static PrintWriter pw;
    private static int maxRes;
    private static int maxFilesInDir;
    private static String[] initialDirectories;

    public static String getDirname(String s) {
        if (s.contains("/"))
            return s.substring(0, s.lastIndexOf("/"));
        return s;
    }

    static String currentDirout;
    static String[] currentDirin;


    static ProcessFile processInstance;
    static String directoryOut;


    public static void loadArgsJson(String file) {


    }

    static FTPClient ftpClient;


    public static Properties settings() {
        Properties p = new Properties();
        try {
            p.load(
                    new FileInputStream(settingsPropertiesPath +


                            "/settings.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

    public static void loadArgsProps(String propFile) {
    }

    /*
        try {
            Method m = processInstance.class.getDeclaredMethod(argCl, argValue);  
        Object rv = m.invoke(processInstance);  
        System.out.println(rv);  
            }
        catch (NoSuchMethodException,  
            InvocationTargetException, IllegalAccessException 
            }*/
    public static Properties defProcess(
            InputStream is) {
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

    public static void main(String[] args) {
        settingsPropertiesPath = "sets/";
        if (args.length > 1)
            settingsPropertiesPath = args[1];


        if (args.length > 1) {
            if (args[0].endsWith(".properties")) {
                loadArgsProps(args[1]);
                defaultProcess();
            }
            if (args[0].endsWith(".json")) {
                loadArgsJson(args[1]);
                defaultProcess();
            }
        } else {
            int i = 0;
            String[] sets = new File("sets").list();
            while (i < Objects.requireNonNull(sets).length) {
                settingsPropertiesPath = "sets/" + sets[i];
                defaultProcess();
                i++;
            }

        }
    }

    public String[] split(String array) {
        String[] split = array.split(",");
        return split;
    }

    public static void parseAndSet(ProcessFile processInstance, List<Object> argCl) {
        if (argCl.size() % 3 == 0) {
            for (int i = 0; i < argCl.size(); i += 3) {
                //Class param = Class.forName(argCl.get(i));
                String param = (String) argCl.get(i);
                String propertyName = (String) argCl.get(i + 1);
                String argValue = (String) argCl.get(i + 2);
                try {
                    Method m = processInstance.getClass().getMethod("set" + propertyName, argValue.getClass());
                    m.invoke(processInstance, "set" + propertyName, argValue);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public static void defaultProcess() {
        System.out.println("arg 0 : dir0 or ftp1 dir path");
        System.out.println("arg 1 : one.empty3.io.ProcessFile class");
        System.out.println("arg 2 : dir0 or ftp1 dir output");

        try {
            DiffEnergy.pw = new PrintWriter("." + File.separator + "energies.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // if(path==null) {
        //      System.exit(-1);
        //  }

        // Properties set = defProcess(args[0]);

        Properties settings = settings();


        String server;
        int port;
        String username;
        String password;


        currentDirin = new String[1];
        if ("local".equals(settings.getProperty("in.device"))) {
            currentDirin = (settings.getProperty("in.directory")).split(",");
            server = "file";
            port = 0;
            username = "";
            password = "";
        } else {
            currentDirin = ((String) settings.getProperty("in.directory")).split(",");
            server = (String) settings.getProperty("host");
            port = Integer.parseInt(settings.getProperty("port"));
            username = (String) settings.getProperty("username");
            password = (String) settings.getProperty("password");
        }

        String maxFilesInDir0 = settings.getProperty("maxFilesInDir");
        FTPProcessFiles.maxFilesInDir = Integer.parseInt(maxFilesInDir0 == null ? "10000" : maxFilesInDir0);
        maxRes = Integer.parseInt(settings.getProperty("maxRes"));
        /* String*/
        classnames = (String) settings.getProperty("classname");
        String class0 = (String) settings.getProperty("class0");
        String directoryOut = (String) settings.getProperty("out.directory");


        String sep = "";
        int i = 0;
        //currentDirin = "";
        if (class0 == null || class0.equals("")) {
            sep = "";
        } else sep = ",";
/*
        classnames = (classnames != null ?
                classnames + sep : "")

                + (class0 == null ? "" : "," + classnames);
*/
        String[] classnamesArr = classnames.split(",");

//        for(String inputDir : currentDirin) {
        int index = 0;
        for (String classname2 : classnamesArr) {
            try {
                classname = classname2;
                if (i > 0)
                    currentDirin[index] = currentDirout;


                currentDirout = "" + directoryOut + "-" + i + "-" + classname + "/";
                Logger.getLogger(FTPProcessFiles.class.getName()).info("Process class name read " + classname);
                System.out.println(classname);
                Class classs = Class.forName(
                        classname
                );


                Logger.getLogger(FTPProcessFiles.class.getName()).info("Process Dir" + classname2);

                Object o = classs.newInstance();

                if (o instanceof ProcessFile)

                    processInstance = (ProcessFile) o;

                String arg = null;

                List<Object> argCl = new ArrayList();

                if ((arg = (String) (settings.getProperty(classname))) != null) {

                    String[] ar = arg.split(":");

                    argCl.addAll(Arrays.asList(ar));

                }

                parseAndSet(processInstance, argCl);


                if (i == 0) {


                    if (server.startsWith("ftp")) {


                        ftpClient = new FTPClient();
                        ftpClient.connect(server, port);
                        showServerReply(ftpClient);

                        int replyCode = ftpClient.getReplyCode();
                        if (!FTPReply.isPositiveCompletion(replyCode)) {
                            System.out.println("Connect failed");
                            return;
                        }
                        // reads settings.xml or prompts user/pass
                        boolean success = ftpClient.login(username, password);
                        showServerReply(ftpClient);

                        if (!success) {
                            System.out.println("Could not login to the server");
                            return;
                        }

                        ftpClient.enterLocalPassiveMode();


                        // Lists files and directories
                        ftpClient.changeWorkingDirectory(directory);
                        showServerReply(ftpClient);

                        FTPFile[] files1 = ftpClient.listFiles(directory);
                        showServerReply(ftpClient);

                        printFileDetails(files1, directory);
                    } else if (server.startsWith("http")) {
                        URL oracle = new URL(server);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(oracle.openStream()));

                        String inputLine;
                        while ((inputLine = in.readLine()) != null)
                            ;
                        in.close();


                    } else {
                        // local path
                        initialDirectories = currentDirin;
                        for (int d = 0; d < initialDirectories.length; d++)
                            if (new File(initialDirectories[d]).exists())
                                printFileDetails(Objects.requireNonNull(new File(initialDirectories[d]).list()), initialDirectories[d]);


                    }

                } else {

                    System.out.println("effect" + processInstance.toString());

                    System.out.println("I>0 classes de traitement\nClasse : " + classs.toString() + " : " + currentDirin[index]);


                    if (new File(currentDirin[index]).exists())
                        printFileDetails(Objects.requireNonNull(new File(currentDirin[index]).list()), currentDirin[index]);


                }


                i++;
                //                  index++;
 /*
            // uses simpler methods
            String[] files2 = ftpClient.listNames(directory);
            printNames(files2);
 */


            } catch (Exception ex) {
                System.out.println("Oops! Something wrong happened");
                ex.printStackTrace();
            } finally {
                // logs out and disconnects from server
                if (ftpClient != null) {
                    try {
                        if (ftpClient.isConnected()) {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
//            }
        }
    }

    public static void process(FTPFile object, String remote) {
        if (object.isFile()) {
            try {


                File fi = new File(currentDirout + "/" + object.getName());
                File fo = new File(currentDirout + "/" + object.getName());


                new File(getDirname(fi.getAbsolutePath())).getParentFile().mkdirs();
                new File(getDirname(fo.getAbsolutePath())).getParentFile().mkdirs();

                Logger.getLogger(FTPProcessFiles.class.getName()).info("fi" + fi.getAbsolutePath());
                Logger.getLogger(FTPProcessFiles.class.getName()).info("fo" + fo.getAbsolutePath());
                fi.createNewFile();
                //fo.createNewFile();

                FileOutputStream fos =
                        new FileOutputStream(fi.getAbsolutePath());

                ftpClient.retrieveFile(remote, fos);

                Logger.getLogger(FTPProcessFiles.class.getName()).info("file  in : " + fi.getAbsolutePath());
                Logger.getLogger(FTPProcessFiles.class.getName()).info("file out : " + fo.getAbsolutePath());
                Logger.getLogger(FTPProcessFiles.class.getName()).info("process ftpfile  : " + processInstance.getClass().getName());

                //Thread thread = new Thread(() -> {
                    processInstance.process(fi, fo);
                    processInstance.setImage(fo);
                    energy(fo);
                //});
                //new TimerKillThread(thread);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void energy(File image) {
       /* try {
            DiffEnergy diffEnergy = new DiffEnergy(ImageIO.read(image), pw);
            for(int i=0; i<diffEnergy.columns; i++)
                for(int j=0; j<diffEnergy.lines; j++) {
                    diffEnergy.filter(i, j);
                }
            diffEnergy.end(image.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    public static void process(File object) {

        if (object.isFile()) {
            if (object.getName().endsWith(".mpg")
                    || object.getName().endsWith(".mp4")
                    || object.getName().endsWith(".avi")) {
                printFileDetails(object);
            }


            File fi = object;
            File fo = new File(currentDirout + "/" + object.getName());


            //new File(getDirname(fi.getAbsolutePath())).getParentFile().mkdirs();
            new File(getDirname(fo.getAbsolutePath())).getParentFile().mkdirs();
            //fi.createNewFile();
            //fo.createNewFile();

            Logger.getLogger(FTPProcessFiles.class.getName()).info("file  in : " + fi.getAbsolutePath());
            Logger.getLogger(FTPProcessFiles.class.getName()).info("file out : " + fo.getAbsolutePath());
            processInstance.setMaxRes(maxRes);
            Logger.getLogger(FTPProcessFiles.class.getName()).info("process file  : " + processInstance.getClass().getName());

           // Thread thread = new Thread(() -> {
                processInstance.process(fi, fo);
                processInstance.setImage(fo);
                energy(fo);
      //      });

       //     new TimerKillThread(thread);

        }
    }

    private static void printFileDetails(FTPFile[] files, String directory) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int it = 0;
        for (FTPFile file : files) {
            if (it++ > maxFilesInDir)
                return;

            if (file.isFile() && !file.getName().equals(".")
                    && !file.getName().equals("..")
            ) {
                String filePath = "";
                String remoteFile = directory + "/" + file.getName();
                //Logger.getLogger(getClass()).info(file.getName());
                //System.out.println(file.getName() + " "+ remote);


                process(file, remoteFile);
            }
        }
    }


    private static void printFileDetails(String[] files, String directory) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int it = 0;
        for (String f : files) {
            if (it > maxFilesInDir)
                return;
            File file = new File(directory + File.separator + f);
            if (file.isFile() && !file.getName().equals(".")
                    && !file.getName().equals("..")
            ) {
                String filePath = directory + "/" + classname + "/" + file.getName();

                //Logger.getLogger(getClass()).info(file.getName());
                System.out.println(file.getName());


                process(file);
            }/* else {
                System.out.println("error file in not found");
                System.exit(-1);
            }*/

            it++;
        }

    }


    private static void printFileDetails(File mpeg) {

        TextureMov t = new TextureMov(mpeg.getAbsolutePath());
        List<File> list = new ArrayList<>();
        // extraire les images
        int findex = 0;
        BufferedImage image = null;
        int i = 0;

        t.timeNext();

        do {


            File ftmp = new File(mpeg.getAbsolutePath() + "---" + (findex++) + ".jpg");
            image = t.getImage();
            if (image == null) {
                System.out.println("Movie frame == null");
                continue;
            }
            try {
                ImageIO.write(image, "JPEG", ftmp);
            } catch (Exception ex) {
                System.out.println("error writing movie frame");
                ex.printStackTrace();

            }
            //list.add(ftmp);

            System.out.println("frame no" + (i++));
        } while (t.nextFrame());

        //  File[] files = new File[list.size()];

        for (File file : list) {
            System.out.println("process file "
                    + file.getAbsolutePath());
            process(file);
        }

    }

    private static void printNames(String files[]) {
        if (files != null && files.length > 0) {
            int it = 0;
            for (String aFile : files) {
                if (it++ > maxFilesInDir)
                    return;
                System.out.println(aFile);
            }
        }
    }

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}
