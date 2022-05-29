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
    public PhotoElement(BufferedImage image){
        this.pixels=image;
        width = image.getWidth();
        height = image.getHeight();
        posX =0;
        posY=0;
    }
    
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void setPosX(int posX){
        this.posX= posX;
    }
    public void setPosY(int posY){
        this.posY= posY;
    }
}
