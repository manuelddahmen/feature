package one.empty3;
import java.util.Properties;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.nio.file.*;
import java.io.*;
import java.net.URI;
import java.io.FileInputStream;
import org.apache.commons.net.ftp.*;
import one.empty3.io.ProcessFile;
import java.util.logging.Logger;

/**
 * An example program that demonstrates how to list files and directories
 * on a FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPProcessFiles {
    public static String getDirname(String s) {
        return s.substring(0, s.lastIndexOf("/"));
    }
    static ProcessFile processInstance;
    static String directoryOut;
    static FTPClient ftpClient;
    public static Properties settings() {
         Properties p = new Properties();
         try {
              p.load(
                new FileInputStream("./settings.properties"));
          } catch(Exception ex) {
              ex.printStackTrace();
          }
         return p;
    }
    
    public static Properties defProcess(FileInputStream is) {
        Properties p = new Properties();
        p.load(is);
        try {
              p.load(is);
          } catch(Exception ex) {
              ex.printStackTrace();
          }
         return p;
    }
    public static void main(String[] args) {
        defaultProcess(new String[]{"settings.properties"});
    }
    public static void defaultProcess(String[] args) {
        System.out.println("arg 0 : dir0 or ftp1 dir path");
        System.out.println("arg 1 : one.empty3.io.ProcessFile class");
        System.out.println("arg 2 : dir0 or ftp1 dir output");
        
            
       // if(path==null) {
         //      System.exit(-1);
      //  }
        
       // Properties set = defProcess(args[0]);
        
        Properties settings = settings();
        String server =(String) settings.getProperty("host");
        int port = Integer.parseInt(settings.getProperty("port"));
        String username = (String)settings.getProperty("username");
        String password = (String)settings.getProperty("password");
        String directory = (String)settings.getProperty("directory");
        String classname = (String)settings.getProperty("classname");
        String directoryOut = directory.substring(0, directory.lastIndexOf("/"));
        
        
        ftpClient = new FTPClient();
 
        try {
            Class classs = Class.forName(
                classname
            );
            Object o = classs.newInstance();
            if(o instanceof ProcessFile)
                processInstance = (ProcessFile) o;
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
            printFileDetails(files1,  directory);
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
    public static void process(FTPFile object, String remote){
        if(object.isFile()) {
        try {
                    
        
        File fi = new File("input/"+remote);
        File fo = new File("output/"+remote);
        
            
        new File(getDirname(fi.getAbsolutePath())).mkdirs();
        new File(getDirname(fo.getAbsolutePath())).mkdirs();
        fi.createNewFile();
        fo.createNewFile();
            
        FileOutputStream fos =
            new FileOutputStream(fi.getAbsolutePath());
        
        ftpClient.retrieveFile(remote, fos);
        

        processInstance.process(fi, fo);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
            }
    }
 
 
    private static void printFileDetails(FTPFile[] files, String directory) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            if(file.isFile() && !file.getName().equals(".")
                 && !file.getName().equals("..")
              ) {
                String filePath = directory+"/"+file.getName();
                
               //Logger.getLogger(getClass()).info(file.getName());
                System.out.println(file.getName());
                
                
                process(file, filePath);
            }
        }
    }
 
    private static void printNames(String files[]) {
        if (files != null && files.length > 0) {
            for (String aFile: files) {
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
