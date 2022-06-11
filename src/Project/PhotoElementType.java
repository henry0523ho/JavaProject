package Project;

import java.util.ArrayList;

public class PhotoElementType {
    private String typeName;
    private ArrayList<PhotoElement> photoElements;
    
    public PhotoElementType(String name) {
        typeName=name;
        photoElements=new ArrayList<PhotoElement>();
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }

    public ArrayList<PhotoElement> getPhotoElements() {
        return photoElements;
    }
    public void setPhotoElements(ArrayList<PhotoElement> photoElements) {
        this.photoElements=photoElements;
    }
    public void addPhotoElement(PhotoElement photoElement) {
        this.photoElements.add(photoElement);
    }
}
