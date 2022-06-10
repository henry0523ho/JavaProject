package Scenes;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class MainSceneController {

    @FXML
    private Button btn;

    @FXML
    private Canvas workingCanvas;

    @FXML
    void saveBtnAction(ActionEvent event) {
        WritableImage wim = new WritableImage(300, 250);
        workingCanvas.snapshot(null, wim);
        File file = new File("CanvasImage.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception s) {
        }
    }

    @FXML
    void drawAction(MouseEvent event) {
        GraphicsContext gc = workingCanvas.getGraphicsContext2D();
        gc.fillOval(event.getX(), event.getY(), 10, 10);
    }

}
