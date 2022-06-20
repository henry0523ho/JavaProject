package Project;

import java.util.ArrayList;

import Scenes.DrawingTools;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public class Project {
    private ArrayList<PhotoElementType> photoElementTypes;
    private int width;
    private int height;
    private PhotoFiles photoFiles;
    private RandomColorList backgroundColorList;

    public Project(int width,int height){
        photoElementTypes = new ArrayList<PhotoElementType>();
        this.width = width;
        this.height = height;
        photoFiles=new PhotoFiles();
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

    public void delPhotoElement(int petId,int peId){
        photoElementTypes.get(petId).getPhotoElements().remove(peId);
    }
    public int findPhotoElementType(String tagName)
    {
        for(int i=0;i<photoElementTypes.size();i++)
        {
            if(photoElementTypes.get(i).getTypeName().equals(tagName))
            {
                return i;
            }
        }
        return -1;
    }

    public int defaultAddPhotoElement(PhotoElement pe){
        if(photoElementTypes.size()==0){
            addPhotoElementType("預設");
        }
        photoElementTypes.get(0).addPhotoElement(pe);
        return photoElementTypes.get(0).getPhotoElements().size()-1;
    }

    public String[] getAllTypeNames(){
        String[] ret=new String[photoElementTypes.size()];
        for(int i=0;i<photoElementTypes.size();++i){
            ret[i]=photoElementTypes.get(i).getTypeName();
        }
        return ret;
    }

    public ArrayList<PhotoElementType> checkOutputPossibility(){
        ArrayList<PhotoElementType> ret=new ArrayList<PhotoElementType>();
        for(PhotoElementType pe : photoElementTypes){
            if(pe.checkPossibility()){
                ret.add(pe);
            }
        }
        return ret;
    }

    public void output(int N,String dirPath){
        for(int i=1;i<=N;++i){
            String destination=String.format("%s\\%d%s",dirPath,i,".png");
            System.out.println(destination);
            Canvas c=makeOnePhoto();
            DrawingTools.saveCanvas(c,destination);
        }
    }

    public Canvas makeOnePhoto(){
        ArrayList<PhotoElement> list = new ArrayList<PhotoElement>();
        for(PhotoElementType pet:photoElementTypes){
            PhotoElement pe=pet.dealOnePhotoElement();
            if(pe!=null){
                list.add(pe);
            }
        }
        Color bgColor=backgroundColorList.dealOneBgColor();
        return DrawingTools.generatePreview(width, height, bgColor, list);
    }
    public Canvas previewPhoto(int petId,int peId){
        ArrayList<PhotoElement> list = new ArrayList<PhotoElement>();
        for(int i=0;i<photoElementTypes.size();++i){
            if(i==petId){
                list.add(photoElementTypes.get(i).getPhotoElements().get(peId));
            }else{
                PhotoElementType pet=photoElementTypes.get(i);
                if(pet.getPhotoElements().size()>0){
                    list.add(pet.getPhotoElements().get(0));
                }
            }
        }
        Color bgColor = backgroundColorList.dealOneBgColor();
        return DrawingTools.generatePreview(width, height, bgColor, list);
    }
}
