package Project;

import java.util.ArrayList;

import javafx.scene.paint.Color;


public class Project {
    private ArrayList<PhotoElementType> photoElementTypes;
    private int width;
    private int height;
    private PhotoFiles photoFiles;
    private String saveDir;
    private RandomColorList backgroundColorList;
    private PhotoElementType mainElementType;

    public Project(int width,int height){
        photoElementTypes = new ArrayList<PhotoElementType>();
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

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }
    public String getSaveDir(){
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
    // public void addPhotoSource(File file){
    //     System.out.println("addFile");
    //     this.photoSource.add(new PhotoFile(file));
    // }
    public void generateResult(int n){
        
    }

}
