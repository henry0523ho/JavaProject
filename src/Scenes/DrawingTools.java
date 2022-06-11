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

    public static GraphicsContext addLayer(GraphicsContext origin,GraphicsContext addition){
        WritableImage originSnap = origin.getCanvas().snapshot(null, null);
        PixelReader originPixels =originSnap.getPixelReader();
        WritableImage additionSnap = addition.getCanvas().snapshot(null, null);
        PixelReader additionPixels = additionSnap.getPixelReader();
        double height=origin.getCanvas().getHeight();
        double width=origin.getCanvas().getWidth();
        for(int i=0;i<height;++i){
            for(int j=0;j<width;++j){
                origin.getPixelWriter().setColor(j,i,addColor(originPixels.getColor(j,i),additionPixels.getColor(j,i)));
            }
        }

        return origin;
    }
    public static Color addColor(Color a,Color b){
        double red=Math.min(1.0,(a.getRed()*a.getOpacity()+b.getRed()*b.getOpacity()));
        double green=Math.min(1.0,(a.getGreen()*a.getOpacity()+b.getGreen()*b.getOpacity()));
        double blue=Math.min(1.0,(a.getBlue()*a.getOpacity()+b.getBlue()*b.getOpacity()));
        double opacity=Math.min(1.0,(a.getOpacity()+b.getOpacity()));
        return new Color(red,green,blue,opacity);
    }

    public static GraphicsContext drawBackground(GraphicsContext gc,Color color){
        double height=gc.getCanvas().getHeight();
        double width=gc.getCanvas().getWidth();
        // gc.setColor(color);
        gc.fillRect(0,0,width,height);
        return gc;
    }
}
