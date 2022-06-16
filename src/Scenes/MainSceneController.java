package Scenes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.imageio.ImageIO;

import Project.PhotoElement;
import Project.PhotoElementType;
import Project.Project;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

public class MainSceneController implements Initializable{
    final Project project;
    int photoElementTypeId;
    int photoElementId;

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
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderLabel;
    @FXML
    private TextField setHeigth;
    @FXML
    private TextField setWidth;
    @FXML
    private TextField setPosX;
    @FXML
    private TextField setPosY;
    @FXML
    private TextField setPossibility;
    @FXML
    private Button newCanvasBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initUI();

        String[] workModes={"畫筆","去背"};
        modeChoiceBox.getItems().addAll(workModes);
        modeChoiceBox.setOnAction(this::changeWorkMode);
        colorPicker.setValue(Color.BLACK);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        setHeigth.setOnKeyPressed(this::setSize);
        setWidth.setOnKeyPressed(this::setSize);
        setPosX.setOnKeyPressed(this::setSize);
        setPosY.setOnKeyPressed(this::setSize);
        setPossibility.setOnKeyPressed(this::setSize);
        
        // workingPane.setBackground(new Background(new BackgroundImage(new Image("shark.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

    @FXML
    void newCanvasBtnAction(ActionEvent event) {
        PhotoElement pe=new PhotoElement(project.getWidth(), project.getHeight());
        pe.getPixels().getGraphicsContext2D().setFill(Color.WHITE);
        pe.getPixels().getGraphicsContext2D().fillRect(0,0,pe.getWidth(),pe.getHeight());
        project.getPhotoElementTypes().get(0).addPhotoElement(pe);
        photoElementId=project.getPhotoElementTypes().get(0).getPhotoElements().size()-1;
        photoElementTypeId=0;
        updateElementTable();
        updateWorkCanvas();
    }
    @FXML
    void saveBtnAction(ActionEvent event) {
        
        ArrayList<PhotoElement> list = new ArrayList<PhotoElement>();
        for(PhotoElement pe:project.getPhotoElementTypes().get(0).getPhotoElements()){
            list.add(pe);
        }
        Canvas c=DrawingTools.generatePreview((double)project.getWidth(),(double)project.getHeight(),project.getBackgroundColorList().getColorList().get(0).getColor(), list);
        Pane p= new Pane();
        p.getChildren().add(c);
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(p, 450, 450));
        stage.show();
    }

    @FXML
    void addMediaFile(DragEvent event) {
        // Dragboard db = event.getDragboard();
        // File file = db.getFiles().get(0);
        // project.addPhotoSource(file);
    }

    @FXML
    void addMediaFileBtnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇匯入圖片");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("圖片", getAllAvailableImageType()),
                new FileChooser.ExtensionFilter("All Images", "*.*"));
        List<File> files = fileChooser.showOpenMultipleDialog(getCurrentStage());
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

    void initUI(){
        @FunctionalInterface
        interface Function6<One, Two, Three, Four, Five, Six> {
            public Six apply(One one, Two two, Three three, Four four, Five five);
        }
    
        Function6<Node,Node,Node,Node,Node,BorderPane> generateBorderPane= (center,top,right,bottom,left)->{
            BorderPane bp=new BorderPane(center,top,right,bottom,left);
            bp.setPrefSize(200,80);
            return bp;
        };
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        gp.setGridLinesVisible(true);
        Label heightLabel=new Label("高:");
        Label widthLabel=new Label("寬:");
        Label bgColorLabel=new Label("背景顏色:");
        TextField heightTf=new TextField("300");
        TextField widthTf=new TextField("400");
        ColorPicker colorPicker=new ColorPicker();
        Button addColor=new Button("+");
        Button submit=new Button("確定");
        FlowPane colorPane=new FlowPane();
        FlowPane colorCtrl=new FlowPane(colorPicker,addColor);



        gp.add(new BorderPane(null,null,heightLabel,null,null),0,1);
        gp.add(new BorderPane(null,null,widthLabel,null,null),0,2);
        gp.add(new BorderPane(null,null,bgColorLabel,null,null),0,3);
        gp.add(new BorderPane(null,null,null,null,heightTf),1,1);
        gp.add(new BorderPane(null,null,null,null,widthTf),1,2);
        gp.add(new BorderPane(colorCtrl,null,null,null,colorPane),1,3);
        gp.add(new BorderPane(null,null,null,null,submit),1,4);
        
        
        
        stage.setScene(new Scene(new BorderPane(gp,null,null,null,null), 600, 400));
        stage.setTitle("初始設定");
        stage.showAndWait();

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
                    photoElementTypeId=0;
                    photoElementId=project.getPhotoElementTypes().get(0).getPhotoElements().size()-1;
                    updateWorkCanvas();
                }
            });
        }
    }

    void updateWorkCanvas(){
        updateSetSize();
        if(photoElementTypeId!=-1&&photoElementId!=-1){
            workingCanvas=project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPixels();
        }else{
            workingCanvas=new Canvas(0,0);
        }
        workingPane.setContent(workingCanvas);
        modeChoiceBox.setValue("畫筆");
        changeWorkMode(null);
        workingCanvas.setOnMouseReleased((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updateElementTable();
            }
        });
    }
    
    void paintMode(){
        colorPicker.setDisable(false);
        sliderLabel.setText("粗細:");
        slider.setMin(0);
        slider.setMax(128);
        slider.setValue(8);
        slider.setMajorTickUnit(8);
        slider.setMinorTickCount(2);
        slider.setBlockIncrement(4);
        workingCanvas.setOnMouseClicked(this::paintHandler);
        workingCanvas.setOnMouseDragged(this::paintHandler);
    }

    public void paintHandler(MouseEvent event){
        GraphicsContext gc = workingCanvas.getGraphicsContext2D();
        gc.setFill(colorPicker.getValue());
        gc.fillOval(event.getX()-slider.getValue()/2, event.getY()-slider.getValue() / 2, slider.getValue(), slider.getValue());
        gc.restore();
    }

    void removeBackgroundMode(){
        colorPicker.setDisable(true);
        sliderLabel.setText("差異度:");
        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(0.1);
        slider.setMajorTickUnit(0.1);
        slider.setMinorTickCount(2);
        slider.setBlockIncrement(0.05);
        workingCanvas.setOnMouseClicked((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GraphicsContext gc = workingCanvas.getGraphicsContext2D();
                gc=DrawingTools.removeBackground(gc,(int)event.getX(), (int)event.getY(), slider.getValue());
                updateElementTable();
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
                final int peID=j-1;
                final int petID=i;
                btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        photoElementId=peID;
                        photoElementTypeId=petID;
                        updateWorkCanvas();
                        
                    }
                });
                btn.setOnMouseClicked((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent e) {
                        if(e.getButton()==MouseButton.SECONDARY){
                            ContextMenu contextMenu = new ContextMenu();
                            MenuItem deleteBtn = new MenuItem("刪除");
                            contextMenu.getItems().add(deleteBtn);
                            deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    project.delPhotoElement(petID,peID);
                                    if(petID==photoElementTypeId&&peID==photoElementId){
                                        photoElementTypeId=-1;
                                        photoElementId=-1;
                                    }else if(petID==photoElementTypeId&&peID<photoElementId){
                                        photoElementId-=1;
                                    }
                                    updateWorkCanvas();
                                    updateElementTable();
                                }
                            });
                            btn.setContextMenu(contextMenu);
                        }
                        
                    }
                    
                });
            }
        }
        elementPane.setContent(elementTable);
    
    }
    void showDialog()
    {
        Dialog<String> dialog=new Dialog<String>();
        dialog.setTitle("Error!!!");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.setContentText("輸入錯誤");
        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.showAndWait();
    }
    
    public void setSize(KeyEvent event) {
        //System.out.printf("%s\n",setHeigth.getText());
        if(event.getCode().equals(KeyCode.ENTER)&&photoElementId!=-1&&photoElementTypeId!=-1){
            try {
                project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).setHeight(Double.parseDouble(setHeigth.getText()));
                project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).setWidth(Double.parseDouble(setWidth.getText()));
                project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).setPosX(Double.parseDouble(setPosX.getText()));
                project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).setPosY(Double.parseDouble(setPosY.getText()));
                project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).setPossibility(Double.parseDouble(setPossibility.getText()));

            }
            catch(NumberFormatException e)
            {
                System.out.println(e);
                showDialog();
            } 
            catch (Exception e) {
                //TODO: handle exception
                System.out.println(e);;
                showDialog();
            }
        }
        
    }
    public void updateSetSize()
    {
        if(photoElementTypeId!=-1&&photoElementId!=-1){
            setHeigth.setText(String.format("%.2f", project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getHeight()));
            setWidth.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getWidth()));
            setPosX.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPosX()));
            setPosY.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPosY()));
            setPossibility.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPossibility()));
        }else{
            setHeigth.setText("");
            setWidth.setText("");
            setPosX.setText("");
            setPosY.setText("");
        }
    }


}
