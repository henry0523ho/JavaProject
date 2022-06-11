package Project;




import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class PhotoElement {
    private int posX;
    private int posY;
    private Canvas pixels;
    private int width;
    private int height;
    private double possibility;

    public PhotoElement(int width,int height){
        this.posX = 0;
        this.posY = 0;
        this.width = width;
        this.height = height;
        this.pixels=new Canvas(width,height);
        this.possibility=0.5;
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
    public void setPixels(Canvas pixels){
        this.pixels= pixels;
    }
    public Canvas getPixels(){
        return this.pixels;
    }

    public double getPossibility(){
        return possibility;
    }
    
}
