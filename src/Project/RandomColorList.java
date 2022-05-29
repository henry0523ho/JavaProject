package Project;

import java.awt.Color;
import java.util.ArrayList;

public class RandomColorList {
    private ArrayList<RandomColor> colorList;
    private RandomColor currentColor;

    public RandomColorList(){
        colorList = new ArrayList<RandomColor>();
        currentColor=null;
    }

    public void setDefaultColorPossibility(){
        double averagePossibility=1.0f/colorList.size();
        for(RandomColor color : colorList){
            color.setPossibility(averagePossibility);
        }
    }
    public boolean checkColorPossibility(){
        double sumPossibility=0.0f;
        for(RandomColor color : colorList){
            sumPossibility+=color.getPossibility();
        }
        if(Math.abs(sumPossibility-1.0f)>0.001f){
            return true;
        }
        return false;
    }
}
