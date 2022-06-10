package Project;




import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class PhotoElement {
    private int posX;
    private int posY;
    private Canvas pixels;
    private int width;
    private int height;

    public PhotoElement(int width,int height){
        this.posX = 0;
        this.posY = 0;
        this.width = width;
        this.height = height;
        this.pixels=new Canvas(width,height);
    }
    // public PhotoElement(BufferedImage image){
    //     this.pixels=image;
    //     width = image.getWidth();
    //     height = image.getHeight();
    //     posX =0;
    //     posY=0;
    // }
    
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
