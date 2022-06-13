package Project;


import Scenes.DrawingTools;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

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
    
    
    public void setHeight(double height){
        this.height=height;
    }
    public double getHeight()
    {
        return this.height;
    }
    public void setWidth(double width){
        this.width=width;
    }
    public double getWidth()
    {
        return this.width;
    }
    public void setPosX(double posX)
    {
        this.posX=posX;
    }
    public double getPosX()
    {
        return this.posX;
    }
    public void setPosY(double posY)
    {
        this.posY=posY;
    }
    public double getPosY()
    {
        return this.posY;
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
    public void setPossibility(double possibility){
        this.possibility=possibility;
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
