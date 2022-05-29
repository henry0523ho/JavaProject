package Project;

import java.util.ArrayList;

public class PhotoElementType {
    private String typeName;
    private ArrayList<PhotoElement> photoElement;
    
    public PhotoElementType(String name) {
        typeName=name;
        photoElement=new ArrayList<PhotoElement>();
    }
}
