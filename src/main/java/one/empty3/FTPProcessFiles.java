package one.empty3;
import java.util.Properties;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.nio.file.*;
import java.net.URI;
import java.io.FileInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
 
/**
 * An example program that demonstrates how to list files and directories
 * on a FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPProcessFiles {
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
    public static void main(String[] args) {
        defaultProcess(new String[]{"1", "testMatGrad", "outputFtp"});
    }
    public static void defaultProcess(String[] args) {
        System.out.println("arg 0 : dir0 or ftp1 dir path");
        System.out.println("arg 1 : one.empty3.io.ProcessFile class");
        System.out.println("arg 2 : dir0 or ftp1 dir output");
        
            
       // if(path==null) {
         //      System.exit(-1);
      //  }
        Properties settings = settings();
        String server =(String) settings.getProperty("host");
        int port = Integer.parseInt(settings.getProperty("port"));
        String username = (String)settings.getProperty("username");
        String password = (String)settings.getProperty("password");
        String directory = (String)settings.getProperty("directory");
        FTPClient ftpClient = new FTPClient();
 
        if(args.length<3)
             System.exit(-1);
         Path path = null;
         if(args[0].equals("0")) {
             //path =  Path.of(args[0]);
         } else if(args[0].equals("1")) {
          try {
              //Path.of(new URI(server+"/"+directory));
           } catch(Exception ex) {
           ex.printStackTrace();
           }
         }
        try {
 
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
 
            // Lists files and directories
            ftpClient.changeWorkingDirectory(directory);
            showServerReply(ftpClient);
         
            FTPFile[] files1 = ftpClient.mListDir(directory);
            showServerReply(ftpClient);
            printFileDetails(files1);
 /*
            // uses simpler methods
            String[] files2 = ftpClient.listNames(directory);
            printNames(files2);
 */
 
        } catch (IOException ex) {
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
    public static void process(FTPFile object){
    
    }
 
 
    private static void printFileDetails(FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName();
            if (file.isDirectory()) {
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
 
            System.out.println(details);
         
            process(file);
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
