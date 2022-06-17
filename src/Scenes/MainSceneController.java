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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
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
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MainSceneController implements Initializable{
    final Project project;
    int photoElementTypeId;
    int photoElementId;
    // ObservableList<String> tag = FXCollections.observableArrayList();
    public MainSceneController() {
        project = new Project(400, 300);
        project.addBackgroundColor(new Color(1,0,0,1));
        project.addBackgroundColor(new Color(0,1,0,1));
        project.addBackgroundColor(new Color(0,0,1,1));
        project.getBackgroundColorList().setDefaultColorPossibility();
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
    @FXML
    private ComboBox<String> tagComboBox;
    @FXML
    private TextField setName;
    @FXML
    private TextField addNewTag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initUI();
    
        String[] workModes={"畫筆","去背"};
        modeChoiceBox.getItems().addAll(workModes);
        modeChoiceBox.setOnAction(this::changeWorkMode);
        colorPicker.setValue(Color.BLACK);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        setHeigth.setOnKeyPressed(this::baseSetting);
        setWidth.setOnKeyPressed(this::baseSetting);
        setPosX.setOnKeyPressed(this::baseSetting);
        setPosY.setOnKeyPressed(this::baseSetting);
        setPossibility.setOnKeyPressed(this::baseSetting);
        setName.setOnKeyPressed(this::setName);
        addNewTag.setOnKeyPressed(this::setNewTag);
        tagComboBox.setItems(FXCollections.observableArrayList(project.getAllTypeNames()));
        tagComboBox.setOnAction(this::selectTag);
        tagComboBox.setVisibleRowCount(10);
    }
 
    @FXML
    void newCanvasBtnAction(ActionEvent event) {
        PhotoElement pe=new PhotoElement(project.getWidth(), project.getHeight());
        pe.getPixels().getGraphicsContext2D().setFill(Color.WHITE);
        pe.getPixels().getGraphicsContext2D().fillRect(0,0,pe.getWidth(),pe.getHeight());
        photoElementTypeId=0;
        photoElementId= project.defaultAddPhotoElement(pe);
        updateElementTable();
        updateWorkCanvas();
    }
    @FXML
    void saveBtnAction(ActionEvent event) {
        
        
        Canvas c=project.previewPhoto(photoElementTypeId, photoElementId);
        Pane p= new Pane();
        p.getChildren().add(c);
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(p, project.getWidth(), project.getHeight()));
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
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        // gp.setGridLinesVisible(true);
        Label heightLabel=DrawingTools.generateInitUILabel("高:");
        Label widthLabel=DrawingTools.generateInitUILabel("寬:");
        Label bgColorLabel=DrawingTools.generateInitUILabel("背景顏色:");
        TextField heightTf=new TextField("300");
        heightTf.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent e) {
                double input;
                try {
                    input = Double.parseDouble(heightTf.getText());
                } catch (Exception ex) {
                    showDialog();
                }
            }
        });
        TextField widthTf=new TextField("400");
        ColorPicker colorPicker=new ColorPicker();
        Button addColor=new Button("+");
        Button submit=new Button("確定");
        FlowPane colorPane=new FlowPane();
        colorPane.setAlignment(Pos.CENTER);
        FlowPane colorCtrl=new FlowPane(colorPicker,addColor);
        colorCtrl.setAlignment(Pos.CENTER);
        colorCtrl.setMinSize(125,42);
        colorCtrl.setMaxSize(125,42);
        colorCtrl.setPrefSize(125,42);
        BorderPane.setAlignment(colorCtrl,Pos.CENTER);
        ColumnConstraints col0=new ColumnConstraints();
        ColumnConstraints col1=new ColumnConstraints();
        col0.setPercentWidth(25);
        col1.setPercentWidth(75);
        gp.getColumnConstraints().addAll(col0,col1);
        RowConstraints row0=new RowConstraints();
        RowConstraints row1=new RowConstraints();
        row0.setPercentHeight(20);
        row1.setPercentHeight(10);
        gp.getRowConstraints().addAll(row1,row0,row0,row0,row0,row1);
        gp.add(DrawingTools.generateInitUIBorderPane(null,null,heightLabel,null,null),0,1);
        gp.add(DrawingTools.generateInitUIBorderPane(null,null,widthLabel,null,null),0,2);
        gp.add(DrawingTools.generateInitUIBorderPane(null,null,bgColorLabel,null,null),0,3);
        gp.add(DrawingTools.generateInitUIBorderPane(null,null,null,null,heightTf),1,1);
        gp.add(DrawingTools.generateInitUIBorderPane(null,null,null,null,widthTf),1,2);
        gp.add(DrawingTools.generateInitUIBorderPane(colorPane,null,null,null,colorCtrl),1,3);
        gp.add(DrawingTools.generateInitUIBorderPane(null,null,null,null,submit),1,4);
        ArrayList<Color> bgColors=new ArrayList<Color>();
        addColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(!DrawingTools.colorInList(colorPicker.getValue(),bgColors)){
                    colorPane.getChildren().clear();
                    bgColors.add(colorPicker.getValue());
                    for(Color c:bgColors){
                        Button btn=DrawingTools.generateColorButton(c);
                        colorPane.getChildren().add(btn);
                    }
                }
            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(Integer.parseInt(heightTf.getText())<=0){
                    showDialog();
                    return;
                }
                if(Integer.parseInt(widthTf.getText())<=0){
                    showDialog();
                    return;
                }
                project.setHeight(Integer.parseInt(heightTf.getText()));
                project.setWidth(Integer.parseInt(widthTf.getText()));
                for(Color c:bgColors){
                    project.addBackgroundColor(c);
                }
                project.getBackgroundColorList().setDefaultColorPossibility();
                stage.close();
            }
        });
        

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
                    // project.getPhotoElementTypes().get(0).addPhotoElement(pe);
                    photoElementId=project.defaultAddPhotoElement(pe);
                    photoElementTypeId=0;
                    updateElementTable();
                    updateWorkCanvas();
                }
            });
        }
    }

    void updateWorkCanvas(){
        updateBaseSetting();
        updateTag();
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
    public void setName(KeyEvent event) {
        //System.out.printf("%s\n",setHeigth.getText());
        if(event.getCode().equals(KeyCode.ENTER)&&photoElementId!=-1&&photoElementTypeId!=-1){
            try {
                project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).setName(setName.getText());
                updateElementTable();

            }
            catch (Exception e) {
                System.out.println(e);;
                showDialog();
            }
        }
        
    }
    public void baseSetting(KeyEvent event) {
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
                System.out.println(e);;
                showDialog();
            }
        }
        
    }
    public void updateBaseSetting()
    {
        if(photoElementTypeId!=-1&&photoElementId!=-1){
            setHeigth.setText(String.format("%.2f", project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getHeight()));
            setWidth.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getWidth()));
            setPosX.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPosX()));
            setPosY.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPosY()));
            setPossibility.setText(String.format("%.2f",project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getPossibility()));
            setName.setText(String.format("%s", project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId).getName()));
        }else{
            setHeigth.setText("");
            setWidth.setText("");
            setPosX.setText("");
            setPosY.setText("");
            setPossibility.setText("");
            setName.setText("");
        }
    }
    public void setNewTag(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)&&photoElementId!=-1&&photoElementTypeId!=-1){
            try {
                if(addNewTag.getText()!=""){
                    project.addPhotoElementType(addNewTag.getText());
                    updateElementTable();
                    addNewTag.setText("");
                    updateTag();
                }
            }
            catch (Exception e) {
                System.out.println(e);
                showDialog();
            }
        }
    }
    public void selectTag(ActionEvent event){
        System.out.println("selectTag call");
        String tagName = tagComboBox.getSelectionModel().getSelectedItem();
        // System.out.println(tagName);
        if(tagName!=""){
            setTag(tagName);
            updateElementTable();
        }
    }
    public void setTag(String tagName){
        PhotoElement pe= project.getPhotoElementTypes().get(photoElementTypeId).getPhotoElements().get(photoElementId);
        int tagIndex=project.findPhotoElementType(tagName);
        System.out.printf("%s %d%n", tagName, tagIndex);
        if(tagIndex==-1){
            project.addPhotoElementType(tagName);
            project.getPhotoElementTypes().get(project.getPhotoElementTypes().size()-1).addPhotoElement(pe);
            project.delPhotoElement(photoElementTypeId, photoElementId);
        }else{
            project.getPhotoElementTypes().get(tagIndex).addPhotoElement(pe);
            project.delPhotoElement(photoElementTypeId, photoElementId);
            photoElementTypeId=tagIndex;
            photoElementId=project.getPhotoElementTypes().get(tagIndex).getPhotoElements().size()-1;
            updateElementTable();
            updateWorkCanvas();
            // System.out.printf("%s\n",project.getPhotoElementTypes().get(tagIndex).getTypeName());
        }
        updateTag();
    }
    public void updateTag(){
        tagComboBox.setOnAction(null);
        tagComboBox.setItems(FXCollections.observableArrayList(project.getAllTypeNames()));
        tagComboBox.setValue(project.getPhotoElementTypes().get(photoElementTypeId).getTypeName());
        tagComboBox.setOnAction(this::selectTag);
        // tagComboBox.setOnAction(this::selectTag);
        //tagComboBox.getSelectionModel().select(photoElementTypeId);
    }

}
