package Scenes;


import java.io.File;

import javax.imageio.ImageIO;

import Project.PhotoElement;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;

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
        gc.fillRect(0,0,width,height);
        return gc;
    }

    public static PhotoElement fileToPhotoElement(File file){
        Image image=new Image(file.getAbsolutePath());
        PhotoElement ret=new PhotoElement(image.getWidth(),image.getHeight());
        ret.getPixels().getGraphicsContext2D().drawImage(image,0,0);
        return ret;
    }

    public static Canvas deepCopyCanvas(Canvas canvas){
        Canvas ret=new Canvas(canvas.getWidth(),canvas.getHeight());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);         
        WritableImage image = canvas.snapshot(params, null);
        ret.getGraphicsContext2D().drawImage(image, 0, 0);
        return ret;
    }

    public static GraphicsContext scaleGraphicsContext(GraphicsContext gc,double width,double height){
        double w=gc.getCanvas().getWidth();
        double h=gc.getCanvas().getHeight();
        Scale scale = new Scale(width/w, height/h);
        gc.setTransform(new Affine(scale));
        gc.stroke();
        return gc;
    }

    public static Canvas scaleCanvas(Canvas canvas,double width,double height){
        double w=canvas.getWidth();
        double h=canvas.getHeight();
        double scaleX=width/w;
        double scaleY=height/h;
        Canvas newCanvas=deepCopyCanvas(canvas);
        newCanvas.setScaleX(scaleX);
        newCanvas.setScaleY(scaleY);
        return newCanvas; 
    }

    public static void saveCanvas(Canvas canvas,String fileName){
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);         
        WritableImage image = canvas.snapshot(params, null);
        File file = new File(fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (Exception s) {
        }
    }

    public static Button generateButton(Node arg0, String name){
        Button btn = new Button();
        GridPane gp= new GridPane();
        BorderPane p1=new BorderPane(arg0,null,null,null,null);
        Label label = new Label(name);
        label.setTextAlignment(TextAlignment.CENTER);
        BorderPane p2=new BorderPane(label,null,null,null,null);
        p1.setMaxSize(100, 80);
        p1.setPrefSize(100,80);
        p1.setMinSize(100,80);
        p2.setMaxSize(100,20);
        p2.setMinSize(100,20);
        p2.setPrefSize(100,20);
        gp.add(p1,0,0);
        gp.add(p2,0,1);
        btn.setGraphic(gp);
        btn.setMaxSize(100,100);
        btn.setPrefSize(100,100);
        btn.setMinSize(100,100);
        return btn;
    }

}
