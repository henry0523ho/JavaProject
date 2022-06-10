package Project;

import java.io.File;
import java.util.ArrayList;

public class PhotoFiles {
    private ArrayList<File> photoSource;

    public PhotoFiles() {
        photoSource = new ArrayList<File>();
    }

    public void addPhotoSource(File file) {
        if (findPhotoSource(file) == -1) {
            this.photoSource.add(file);
        }
    }

    public ArrayList<File> getPhotoSources() {
        return this.photoSource;
    }

    public void deletePhotoSource(File file) {
        int idx = findPhotoSource(file);
        if (idx != -1) {
            photoSource.remove(idx);
        }
    }

    public int findPhotoSource(File file) {
        for (int i = 0; i < photoSource.size(); ++i) {
            if (photoSource.get(i).equals(file)) {
                return i;
            }
        }
        return -1;
    }
}
