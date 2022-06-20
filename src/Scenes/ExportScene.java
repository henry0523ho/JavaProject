package Scenes;

import Project.Project;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.event.*;


public class ExportScene{
    
    @FXML
    private Button chooseButton=new Button();
    @FXML
    private TextField amountTextField=new TextField();
    @FXML
    private TextField addressTextField=new TextField();
    @FXML
    private Label amountLabel=new Label();
    @FXML
    private Label addressLabel=new Label();
    @FXML
    private AnchorPane anchorPane=new AnchorPane();
    @FXML
    private Button exportButton=new Button();
    @FXML
    private Button confirmButton=new Button();

    Group root=new Group();
    Scene scene=new Scene(root,600,400);
    DirectoryChooser directoryChooser=new DirectoryChooser();
    int amount;
    String addressPath;
    public Scene generateScene(Project project,Stage stage)
    {
        anchorPane.setMaxHeight(Double.MAX_VALUE);
        anchorPane.setMaxWidth(Double.MAX_VALUE);
        anchorPane.setMinHeight(Double.MAX_VALUE);
        anchorPane.setMinWidth(Double.MAX_VALUE);
        anchorPane.setPrefHeight(400);
        anchorPane.setPrefWidth(600);

        amountTextField.setLayoutX(233);
        amountTextField.setLayoutY(114);
        amountTextField.setPrefHeight(26);
        amountTextField.setPrefWidth(159);
        amountTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent arg0) {
                if(arg0.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        amount=Integer.parseInt(amountTextField.getText()); 
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                        showDialog();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                        showDialog();
                    }
                }
            }
        });

        amountLabel.setLayoutX(77);
        amountLabel.setLayoutY(111);
        amountLabel.setText("輸入匯出數量");
        amountLabel.setTextAlignment(TextAlignment.CENTER);
        amountLabel.setFont(new Font("System Bold", 24));

        addressLabel.setLayoutX(77);
        addressLabel.setLayoutY(192);
        addressLabel.setText("輸入匯出位址");
        addressLabel.setTextAlignment(TextAlignment.CENTER);
        addressLabel.setFont(new Font("System Bold", 24));

        addressTextField.setLayoutX(64);
        addressTextField.setLayoutY(237);
        addressTextField.setPrefHeight(32);
        addressTextField.setPrefWidth(473);

        chooseButton.setLayoutX(233);
        chooseButton.setLayoutY(194);
        chooseButton.setMnemonicParsing(false);
        chooseButton.setPrefHeight(32);
        chooseButton.setPrefWidth(48);
        chooseButton.setText("選擇");
        chooseButton.setFont(new Font(14));
        chooseButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                File file=directoryChooser.showDialog(null);
                if(file!=null)
                {
                    addressTextField.setText(file.getAbsolutePath());
                    addressPath=addressTextField.getText();
                }
            }
            
        });

        exportButton.setLayoutX(474);
        exportButton.setLayoutY(311);
        exportButton.setMnemonicParsing(false);
        exportButton.setPrefHeight(32);
        exportButton.setPrefWidth(48);
        exportButton.setText("匯出");
        exportButton.setFont(new Font(14));
        exportButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                project.output(amount, addressPath);
                stage.close();
            }
            
        });

        confirmButton.setLayoutX(398);
        confirmButton.setLayoutY(111);
        confirmButton.setMnemonicParsing(false);
        confirmButton.setPrefHeight(26);
        confirmButton.setPrefWidth(48);
        confirmButton.setText("確定");
        confirmButton.setFont(new Font(14));
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    amount=Integer.parseInt(amountTextField.getText()); 
                } catch (NumberFormatException e) {
                    System.out.println(e);
                    showDialog();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    showDialog();
                }
            }
        });
        root.getChildren().add(anchorPane);
        root.getChildren().add(amountTextField);
        root.getChildren().add(amountLabel);
        root.getChildren().add(addressLabel);
        root.getChildren().add(addressTextField);
        root.getChildren().add(chooseButton);
        root.getChildren().add(exportButton);
        root.getChildren().add(confirmButton);
        return scene;
    }
    void showDialog()
    {
        Dialog<String> dialog=new Dialog<String>();
        dialog.setTitle("Error!!!");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.setContentText("輸入錯誤");
        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.showAndWait();
    }
    
    
    
}
