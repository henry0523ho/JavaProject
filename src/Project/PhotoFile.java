package Project;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class PhotoFile {
    public static final int ORIGIN_PHOTO=1;
    public static final int WORKING_PHOTO=2;
    private int photoType;
    private String filePath;
    private BufferedImage bufferedImage;

    public PhotoFile(String filePath) throws IOException{
        File file=new File(filePath);
        bufferedImage=ImageIO.read(file);
        photoType=ORIGIN_PHOTO;
    }

    public PhotoFile(BufferedImage bufferedImage){
        this.bufferedImage=bufferedImage;
        photoType=WORKING_PHOTO;
    }

    public PhotoFile(int width,int height){
        this.bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        photoType=WORKING_PHOTO;
    }

    public int getPhotoType(){
        return photoType;
    }

    public String getFilePath(){
        if(photoType==ORIGIN_PHOTO){
            return this.filePath;
        }
        return "";
    }
    public void setFilePath(String filePath) throws IOException{
        this.filePath = filePath;
        updatePhotoData();
        photoType=ORIGIN_PHOTO;
    }

    public void setBufferedImage(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
        photoType=WORKING_PHOTO;
    }
    public BufferedImage getBufferedImage(){
        return this.bufferedImage;
    }
    
    public void updatePhotoData() throws IOException{
        File file = new File(filePath);
        bufferedImage = ImageIO.read(file);
    }

    public void writePhotoToFile(String fileType,File file) throws IOException{
        ImageIO.write(bufferedImage,fileType,file);
    }
    public void writePhotoToFile(String fileType,String path) throws IOException{
        File newFile = new File(path);
        ImageIO.write(bufferedImage, fileType, newFile);
    }

    public int fileExists(){
        if(photoType==ORIGIN_PHOTO){
            File file = new File(getFilePath());
            if(file.exists()){
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public int getRGB(int x,int y){
        return bufferedImage.getRGB(x, y);
    }
}
