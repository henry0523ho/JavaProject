package Project;
import java.io.File;
import java.util.ArrayList;

public class PhotoFiles {
    private ArrayList<File> photoSource;

    public PhotoFiles(){
        photoSource = new ArrayList<File>();
    }

    public void addPhotoSource(File file) {
        this.photoSource.add(file);
    }
    public ArrayList<File> getPhotoSources() {
        return this.photoSource;
    }
}
