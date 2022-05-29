package Project;

import java.awt.Color;
import java.util.ArrayList;

public class RandomColor {
    private Color color;
    double possibility;
    public RandomColor(Color color){
        this.color = color;
        possibility=1.0f;
    }
    public RandomColor(Color color,double possibility){
        this.color = color;
        this.possibility=possibility;
    }

    public void setColor(Color newColor){
        this.color = newColor;
    }
    public Color getColor(){
        return color;
    }

    public void setPossibility(double possibility){
        this.possibility = possibility;
    }
    public double getPossibility(){
        return possibility;
    }
    
}
