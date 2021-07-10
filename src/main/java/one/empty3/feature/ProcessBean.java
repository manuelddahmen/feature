package one.empty3.feature;

import org.apache.commons.net.ftp.FTPFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessBean {
    private List<File> files = new ArrayList<>();
    protected FTPFile ftpFile;
    private File file;
    boolean ftp = false;

    public ProcessBean(File file) {
        this.ftp = false;
        this.file = file;

    }
    public ProcessBean(FTPFile file) {
        this.ftp = true;
        this.ftpFile = file;

    }

    public static List<ProcessBean> processBeanList(FTPFile[] files1) {
        List<ProcessBean> beans = new ArrayList<>();
        for (int i = 0; i < files1.length; i++) {
            ProcessBean processBean = new ProcessBean(files1[i]);
            beans.add(processBean);
        }
        return beans;
    }

    public static List<ProcessBean> processBeanList(File[] list) {
        List<ProcessBean> beans = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            ProcessBean processBean = new ProcessBean(list[i]);
            beans.add(processBean);
        }
        return beans;
    }


    public PixM getStack(int i) {
        return listImage.get(i);
    }

    public void setImage(File fo) {
        try {
            listImage.add(new PixM(ImageIO.read(fo)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    List<PixM> listImage = new ArrayList<>();


    public List<File> files() {
        return files;
    }
 }
