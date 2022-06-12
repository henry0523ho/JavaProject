package Scenes;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;

public class MediaButton {
    String fileName;
    File file;
    Image image;
    public MediaButton(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
        this.image=initImage();
    }

    private Image initImage(){
        return new Image(file.getAbsolutePath());
    }

    public Button generateButton() {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        System.out.println(fileName);
        if(image.getHeight()<image.getWidth()){
            imageView.setFitWidth(90);
            imageView.setFitHeight((90/image.getWidth())*image.getHeight());
        }else{
            imageView.setFitHeight(70);
            imageView.setFitWidth((70/image.getHeight())*image.getWidth());
        }
        return DrawingTools.generateButton(imageView,fileName);
    }


}
