package one.empty3.io;

import one.empty3.feature.PixM;
import one.empty3.feature.ProcessBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ProcessFile {
    public ProcessBean bean;
    protected int maxRes = -1;

    public ProcessFile() {


    }

    public abstract boolean process(File in, File out);

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

}
