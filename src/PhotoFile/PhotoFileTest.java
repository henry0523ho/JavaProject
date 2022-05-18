package PhotoFile;

import java.io.IOException;
import java.awt.image.BufferedImage;
public class PhotoFileTest {
    public static void main(String[] args){
        String dir="shark.png";
        try {
            PhotoFile photoFile = new PhotoFile(dir);
            BufferedImage image =photoFile.getBufferedImage();
            PhotoFile newPhotoFile = new PhotoFile(image.getWidth(),image.getHeight());
            BufferedImage newImage=newPhotoFile.getBufferedImage();
            for(int i=0;i<newImage.getHeight();++i){
                for(int j=0;j<newImage.getWidth();++j){
                    // int a=(photoFile.getRGB(j, i)&0xFF000000)>>24;
                    int r= (photoFile.getRGB(j, i) & 0x00FF0000) >> 16;
                    int g= (photoFile.getRGB(j, i) & 0x0000FF00) >> 8;
                    int b= (photoFile.getRGB(j, i) & 0x000000FF);
                    int sum=(r+g+b)/3;
                    newImage.setRGB(j,i,(0xFF000000)|(sum<<16)|(sum<<8)|sum);
                }
            }
            newPhotoFile.setBufferedImage(newImage);
            newPhotoFile.writePhotoToFile("png","newShark.png");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
