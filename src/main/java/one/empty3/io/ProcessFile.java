package one.empty3.io;

import one.empty3.feature.PixM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ProcessFile {
    List<PixM> listImage = new ArrayList<>();
    protected int maxRes = -1;

    public ProcessFile() {


    }

    public abstract boolean process(File in, File out);

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public BufferedImage getStack(int i) {
        return null;
    }

    public void setImage(File fo) {
        try {
            listImage.add(new PixM(ImageIO.read(fo)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
