package Scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DrawingTools {
    public static GraphicsContext removeBackground(GraphicsContext gc,int x,int y,double offset){
        WritableImage snap = gc.getCanvas().snapshot(null, null);
        PixelReader pixelReader =snap.getPixelReader();
        Color target= pixelReader.getColor(x,y);
        double height=gc.getCanvas().getHeight();
        double width=gc.getCanvas().getWidth();
        for(int i=0;i<height;++i){
            for(int j=0;j<width;++j){
                if(colorDistance(target,pixelReader.getColor(j,i))<=offset){
                    gc.getPixelWriter().setColor(j,i,Color.TRANSPARENT);
                }
            }
        }
        return gc;
    }
    public static double colorDistance(Color a,Color b){
        double dist=0;
        dist+=Math.sqrt((a.getOpacity()-b.getOpacity())*(a.getOpacity()-b.getOpacity()));
        dist+=Math.sqrt((a.getRed()-b.getRed())*(a.getRed() - b.getRed()));
        dist+=Math.sqrt((a.getGreen()-b.getGreen())*(a.getGreen() - b.getGreen()));
        dist+=Math.sqrt((a.getBlue()-b.getBlue())*(a.getBlue() - b.getBlue()));
        return dist;
    }
}
