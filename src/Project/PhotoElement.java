package Project;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PhotoElement {
    private int posX;
    private int posY;
    private BufferedImage pixels;
    private ArrayList< ArrayList<RandomColor> > randomColorPixels;
    private int width;
    private int height;

    public PhotoElement(int width,int height){
        this.posX = 0;
        this.posY = 0;
        this.width = width;
        this.height = height;
        this.pixels=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<height;++i){
            ArrayList<RandomColor> tmp = new ArrayList<RandomColor>(width);
            randomColorPixels.add(tmp);
        }
    }
}
