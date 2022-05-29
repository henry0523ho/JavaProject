package Project;

import java.util.ArrayList;

public class PhotoElementType {
    private String typeName;
    private ArrayList<PhotoElement> photoElement;
    
    public PhotoElementType(String name) {
        typeName=name;
        photoElement=new ArrayList<PhotoElement>();
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }
}
