package Project;


import Scenes.DrawingTools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PhotoElement {
    private double posX;
    private double posY;
    private Canvas pixels;
    private double width;
    private double height;
    private double possibility;
    private String name;

    public PhotoElement(double width,double height){
        this.width = width;
        this.height = height;
        this.pixels=new Canvas(width,height);
        this.possibility=0.5;
        posX=0;
        posY=0;
        name="name";
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


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public Button generateElementBtn(){
        if(pixels.getHeight()<pixels.getWidth()){
            return DrawingTools.generateButton(DrawingTools.scaleCanvas(pixels,90,(90/pixels.getWidth())*pixels.getHeight()), name);
        }else{
            return DrawingTools.generateButton(DrawingTools.scaleCanvas(pixels,(70/pixels.getHeight())*pixels.getWidth(),70), name);
        }
    }
    
    
}
