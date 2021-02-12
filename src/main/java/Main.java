package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("main/resources/Strings_en");

        //Set root FXML
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/mainView.fxml"),bundle);

        //Define features of main window.
        primaryStage.setTitle(bundle.getString("AppName"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        //Creating the viewController of the application.
        ViewController viewController = new ViewController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
