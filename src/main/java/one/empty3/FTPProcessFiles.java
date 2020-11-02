package one.empty3;
import java.util.*;
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
import java.lang.reflect.Method;
/**
 * An example program that demonstrates how to list files and directories
 * on a FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPProcessFiles {
    public static String classnames, classname;
        
       
        
    public static String currentDirin ="input/0";
    public static String currentDirout ="";
    public static String currentFileName ="";

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
    
    public static Properties defProcess(
                                     InputStream is) {
        Properties p = new Properties();
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
    
    public static void parseAndSet(ProcessFile processInstance, List<Object> argCl){
         if(argCl.size()%3==0)
             {
             for(int i = 0; i<argCl.size(); i+=3) {
         //Class param = Class.forName(argCl.get(i));
                 String param = (String)argCl.get(i);
         String propertyName= (String)argCl.get(i+1);
         String argValue= (String)argCl.get(i+2);
                 try {
         Method m =processInstance.getClass().getMethod("set"+ propertyName, argValue.getClass());
         m.invoke(processInstance, "set"+ propertyName, argValue);
                     } catch(Exception ex)
                     {
                 ex.printStackTrace();
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
             }
        }
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
       /* String*/ classnames = (String)settings.getProperty("classname");
        String class0 = (String)settings.getProperty("class0");
        String directoryOut = directory.substring(0, directory.lastIndexOf("/"));
        
        
        ftpClient = new FTPClient();
 
        
      
        
      try {  
          String  sep="";
          int i=0;
          //currentDirin = "";
          if(class0==null || class0.equals("")){
              sep=""; } else sep=",";
          
           {
                classnames =(classnames !=null?
                             classnames+sep:"")
                           
                             +(class0==null?"":""+class0);
            }
          if(i==0&&(class0==null || class0.rquals("") }
           String [] classnamesArr = classnames.split(",");
           for (String classname2 : classnamesArr) {
            classname = classname2;
            currentDirout = "./output/"+classname+"/";
            Class classs = Class.forName(
                classname
            );
            
            Logger.getLogger(FTPProcessFiles.class.getName()).info("Process Dir" + classname2);
            Object o = classs.newInstance();
            if(o instanceof ProcessFile)
                processInstance = (ProcessFile) o;
            String arg = null;
            List<Object> argCl= new ArrayList();
           if((arg=(String)(settings.getProperty(classname)))!=null) {
               String [] ar = arg.split(":");
               argCl.addAll(Arrays.asList(ar));
           }
           parseAndSet(processInstance, argCl);
         
               
           
                
                
                
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
            } else {
                
                
                System.out.println("I>0 clase de traitement"+classs.toString()+" : "+currentDirin);
                printFileDetails(new File(currentDirin).list(),  currentDirin);
            }
 /*
            // uses simpler methods
            String[] files2 = ftpClient.listNames(directory);
            printNames(files2);
 */
            i++;
            
            
            currentDirin = currentDirout;
            
            }
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
                    
        
        File fi = new File(currentDirin+"/"+object.getName());
        File fo = new File(currentDirout+"/"+object.getName());
        
            
        new File(getDirname(fi.getAbsolutePath())).mkdirs();
        new File(getDirname(fo.getAbsolutePath())).mkdirs();
            
            Logger.getLogger(FTPProcessFiles.class.getName()).info("fi"+fi.getAbsolutePath());
            Logger.getLogger(FTPProcessFiles.class.getName()).info("fo"+fo.getAbsolutePath());                                 
        fi.createNewFile();
        fo.createNewFile();
            
        FileOutputStream fos =
            new FileOutputStream(fi.getAbsolutePath());
        
        ftpClient.retrieveFile(remote, fos);
        
        Logger.getLogger(FTPProcessFiles.class.getName()).info("file  in : "+fi.getAbsolutePath());
        Logger.getLogger(FTPProcessFiles.class.getName()).info("file out : "+fo.getAbsolutePath());
        Logger.getLogger(FTPProcessFiles.class.getName()).info("process ftpfile  : "+processInstance.getClass().getName());
            
            processInstance.process(fi, fo);
            
            
            
       // dirin = (fo.getParent());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
            }
        
        
    }
 public static void process(File object){
        if(object.isFile()) {
        try {
                    
        
        File fi = object;
        File fo = new File(currentDirout+"/"+object.getName());
        
            
        new File(getDirname(fi.getAbsolutePath())).mkdirs();
        new File(getDirname(fo.getAbsolutePath())).mkdirs();
        fi.createNewFile();
        fo.createNewFile();
            Logger.getLogger(FTPProcessFiles.class.getName()).info("file  in : "+fi.getAbsolutePath());
        Logger.getLogger(FTPProcessFiles.class.getName()).info("file out : "+fo.getAbsolutePath());
        Logger.getLogger(FTPProcessFiles.class.getName()).info("process file  : "+processInstance.getClass().getName());
            
        
        processInstance.process(fi, fo);
            
            
     //   dirin = (fo.getParent());
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
                String filePath ="";
                String remoteFile = directory+"/"+file.getName();
               //Logger.getLogger(getClass()).info(file.getName());
                //System.out.println(file.getName() + " "+ remote);
                
                
                process(file, remoteFile);
            }
        }
    }
            
 
    private static void printFileDetails(String[] files, String directory) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String f : files) {
            File file = new File(directory +"/" +f);
            if(file.isFile() && !file.getName().equals(".")
                 && !file.getName().equals("..")
              ) {
                String filePath = directory+"/"+classname+"/"+file.getName();
                
               //Logger.getLogger(getClass()).info(file.getName());
                System.out.println(file.getName());
                
                
                process(file);
            } else {
                System.out.println("error file in not found");
                System.exit(-1);
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
