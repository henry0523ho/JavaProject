package Project;

import java.io.File;
import java.util.ArrayList;


public class Project {
    private ArrayList<PhotoElementType> photoElementTypes;
    private int width;
    private int height;
    private PhotoFiles photoFiles;
    private String saveDir;
    private RandomColorList backgroundColorList;

    public Project(int width,int height){
        photoElementTypes = new ArrayList<PhotoElementType>();
        this.width = width;
        this.height = height;
        // photoSource = new ArrayList<PhotoFile>();
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

    // public void addPhotoSource(File file){
    //     System.out.println("addFile");
    //     this.photoSource.add(new PhotoFile(file));
    // }
    

}
