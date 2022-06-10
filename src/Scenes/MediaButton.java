package Scenes;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
        Button btn = new Button();
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(70);
        Label label= new Label(fileName);
        label.setMaxHeight(16);
        label.setMaxWidth(80);
        BorderPane borderPanel = new BorderPane(imageView,null,null,label,null);
        btn.setGraphic(borderPanel);
        return btn;
    }


}
