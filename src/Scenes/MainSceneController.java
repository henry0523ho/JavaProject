package Scenes;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Project.PhotoElement;
import Project.PhotoElementType;
import Project.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.scene.input.DragEvent;

public class MainSceneController implements Initializable{
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
    private ScrollPane workingPane;
    @FXML
    private ScrollPane elementPane;
    @FXML
    private ChoiceBox<String> modeChoiceBox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] workModes={"畫筆","去背"};
        modeChoiceBox.getItems().addAll(workModes);
        modeChoiceBox.setOnAction(this::changeWorkMode);
        
    }
    @FXML
    void saveBtnAction(ActionEvent event) {
        workingCanvas=DrawingTools.scaleCanvas(workingCanvas,90,(90/workingCanvas.getWidth())*workingCanvas.getHeight());
        updateWorkCanvas();
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
        if(files!=null){
            for (File file : files) {
                project.getPhotoFiles().addPhotoSource(file);
            }
        }
        updateMediaPane();
    }

    public void changeWorkMode(ActionEvent event){
        String currentMode = modeChoiceBox.getValue();
        if(currentMode=="畫筆"){
            paintMode();
        }else if(currentMode=="去背"){
            removeBackgroundMode();
        }
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
                    PhotoElement pe=DrawingTools.fileToPhotoElement(file);
                    project.getPhotoElementTypes().get(0).addPhotoElement(pe);
                    updateElementTable();
                    toWorkCanvas(pe);
                }
            });
        }
    }

    void toWorkCanvas(PhotoElement pe){
        workingCanvas=pe.getPixels();
        updateWorkCanvas();
    }

    void updateWorkCanvas(){
        workingPane.setContent(workingCanvas);
        
        workingCanvas.setOnMouseReleased((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GraphicsContext gc = workingCanvas.getGraphicsContext2D();
                gc.fillOval(event.getX(), event.getY(), 10, 10);
                updateElementTable();
            }
        });
    }

    void paintMode(){
        workingCanvas.setOnMouseClicked((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GraphicsContext gc = workingCanvas.getGraphicsContext2D();
                gc.fillOval(event.getX(), event.getY(), 10, 10);
                gc.restore();
            }
        });
        workingCanvas.setOnMouseDragged((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GraphicsContext gc = workingCanvas.getGraphicsContext2D();
                gc.fillOval(event.getX(), event.getY(), 10, 10);
                gc.restore();
            }
        });
    }

    void removeBackgroundMode(){
        workingCanvas.setOnMouseClicked((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GraphicsContext gc = workingCanvas.getGraphicsContext2D();
                gc=DrawingTools.removeBackground(gc,(int)event.getX(), (int)event.getY(), 0.1);

            }
        });
        workingCanvas.setOnMouseDragged(null);
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

    void updateElementTable(){
        GridPane elementTable=new GridPane();
        for(int i=0;i<project.getPhotoElementTypes().size();++i){
            PhotoElementType pet = project.getPhotoElementTypes().get(i);
            elementTable.add(new Label(pet.getTypeName()),i,0);
            for(int j=1;j<=pet.getPhotoElements().size();++j){
                PhotoElement pe=pet.getPhotoElements().get(j-1);
                Button btn=pe.generateElementBtn();
                elementTable.add(btn,i,j);
                btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        toWorkCanvas(pe);
                        updateWorkCanvas();
                    }
                });
            }
        }
        elementPane.setContent(elementTable);
    }


}
