package Project;

import java.security.SecureRandom;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class RandomColorList {
    private ArrayList<RandomColor> colorList;
    
    public RandomColorList(){
        colorList = new ArrayList<RandomColor>();
    }

    public ArrayList<RandomColor> getColorList(){
        return colorList;
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

    public void addColor(RandomColor color){
        if(findColor(color)==-1){
            this.colorList.add(color);
        }
    }

    public void removeColor(RandomColor color){
        int idx=findColor(color);
        if(idx!=-1){
            colorList.remove(idx);
        }
    }

    public int findColor(RandomColor color){
        for(int i=0;i<colorList.size();++i){
            if(colorList.get(i).equals(color)){
                return i;
            }
        }
        return -1;
    }

    public Color dealOneBgColor(){
        SecureRandom secureRandom = new SecureRandom();
        double rand=secureRandom.nextDouble();
        double cur=0.0f;
        for(int i=0;i<colorList.size();++i){
            cur+=colorList.get(i).getPossibility();
            if(rand<cur) return colorList.get(i).getColor();
        }
        return Color.TRANSPARENT;
    }
}
