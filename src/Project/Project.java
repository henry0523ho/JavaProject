package Project;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Project {
    private ArrayList<PhotoElementType> photoElementTypes;
    private int width;
    private int height;
    private PhotoFiles photoFiles;
    private File saveDir;
    private RandomColorList backgroundColorList;

    public Project(int width,int height){
        photoElementTypes = new ArrayList<PhotoElementType>();
        this.addPhotoElementType(new PhotoElementType("預設"));
        this.width = width;
        this.height = height;
        photoFiles=new PhotoFiles();
        saveDir=null;
        backgroundColorList=new RandomColorList();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public void setSaveDir(File saveDir) {
        this.saveDir = saveDir;
    }
    public File getSaveDir(){
        return saveDir;
    }

    public PhotoFiles getPhotoFiles(){
        return this.photoFiles;
    }
    public void setPhotoFiles(PhotoFiles photoFiles){
        this.photoFiles= photoFiles;
    }

    public void addBackgroundColor(Color color){
        backgroundColorList.addColor(new RandomColor(color));
    }

    public RandomColorList getBackgroundColorList(){
        return backgroundColorList;
    }

    public ArrayList<PhotoElementType> getPhotoElementTypes(){
        return photoElementTypes;
    }
    public void setPhotoElementTypes(ArrayList<PhotoElementType> photoElementTypes){
        this.photoElementTypes = photoElementTypes;
    }
    public void addPhotoElementType(PhotoElementType photoElementType){
        this.photoElementTypes.add(photoElementType);
    }
    public void addPhotoElementType(String typeName){
        this.photoElementTypes.add(new PhotoElementType(typeName));
    }
    public void generateResult(int n){
        Canvas canvas=new Canvas(width, height);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        
    }
    public void delPhotoElement(int petId,int peId){
        photoElementTypes.get(petId).getPhotoElements().remove(peId);
    }
    public int findPhotoElementType(String tagName)
    {
        for(int i=0;i<photoElementTypes.size();i++)
        {
            if(photoElementTypes.get(i).equals(tagName))
            {
                return i;
            }
        }
        return -1;
    }

}
