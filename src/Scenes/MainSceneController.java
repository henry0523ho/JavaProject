package Scenes;

import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import Project.Project;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.scene.input.DragEvent;

public class MainSceneController {
    final Project project;
    public MainSceneController() {
        int projectWidth;
        int projectHeight;
        projectWidth=400;
        projectHeight=300;
        project = new Project(projectWidth, projectHeight);
        project.addBackgroundColor(new Color(1,0,0,1));
        project.addBackgroundColor(new Color(0,1,0,1));
        project.addBackgroundColor(new Color(0,0,1,1));
        project.getBackgroundColorList().setDefaultColorPossibility();

        // workingCanvas.setWidth(project.getWidth());
        // workingCanvas.setHeight(project.getHeight());
    }

    @FXML
    private Button btn;

    @FXML
    private Canvas workingCanvas;

    @FXML
    private FlowPane mediaPane;

    @FXML
    private Button addMediaFileBtn;

    @FXML
    void saveBtnAction(ActionEvent event) {
        // WritableImage wim = new WritableImage(300, 250);
        // workingCanvas.snapshot(null, wim);
        // File file = new File("CanvasImage.png");
        // try {
        //     ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        // } catch (Exception s) {
        // }

        GraphicsContext gc= workingCanvas.getGraphicsContext2D();
        gc=DrawingTools.removeBackground(gc, 1, 1,0.5);
    }

    @FXML
    void drawAction(MouseEvent event) {
        GraphicsContext gc = workingCanvas.getGraphicsContext2D();
        gc.fillOval(event.getX(), event.getY(), 10, 10);
        
    }

    @FXML
    void addMediaFile(DragEvent event) {
        // Dragboard db = event.getDragboard();
        // File file = db.getFiles().get(0);
        // project.addPhotoSource(file);
    }

    @FXML
    void addMediaFileBtnAction(ActionEvent event) {
        // System.out.println("clicked");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇匯入圖片");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("圖片", getAllAvailableImageType()),
                new FileChooser.ExtensionFilter("All Images", "*.*"));
        List<File> files = fileChooser.showOpenMultipleDialog(getCurrentStage());
        // System.out.println(files);
        for (File file : files) {
            project.getPhotoFiles().addPhotoSource(file);
        }
        updateMediaPane();
    }

    void updateMediaPane() {
        mediaPane.getChildren().clear();
        for (File file : project.getPhotoFiles().getPhotoSources()) {
            MediaButton mediaButton = new MediaButton(file.getName(), file);
            Button newBtn=mediaButton.generateButton();
            mediaPane.getChildren().add(newBtn);
            newBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    toWorkCanvas(file);
                }
            });
        }
    }

    void toWorkCanvas(File file) {
        GraphicsContext gc=workingCanvas.getGraphicsContext2D();
        Image image= new Image(file.getAbsolutePath());
        gc.drawImage(image,0,0);
        gc.restore();
    }

    Window getCurrentStage() {
        return addMediaFileBtn.getScene().getWindow();
    }

    String[] getAllAvailableImageType() {
        String[] ret = new String[ImageIO.getReaderFileSuffixes().length];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = "*." + ImageIO.getReaderFileSuffixes()[i];
        }
        return ret;
    }

}
