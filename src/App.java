import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Scenes/MainScene.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("NFT圖片自動生成程式");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("ERROR");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("00957130");
        System.out.println("00957104");
    }
}