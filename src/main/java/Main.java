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
        ResourceBundle bundle = ResourceBundle.getBundle("Strings_en");

        //Set root FXML
        ClassLoader classLoader = Main.class.getClassLoader();

        Parent root = FXMLLoader.load(classLoader.getResource("view/mainView.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("../resources/view/mainView.fxml"),bundle);

        //Define features of main window.
        primaryStage.setTitle(bundle.getString("AppName"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        //Creating the viewController of the application.
        ViewController viewController = new ViewController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
