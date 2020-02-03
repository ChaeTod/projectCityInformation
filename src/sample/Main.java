package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //GetAndPost.MyGETRequest(); // call the connector to the API

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Get some info about a City");
        primaryStage.setScene(new Scene(root, 520, 350));

        // Attempt to change the scene size by some action
        /*
        Controller con = new Controller();
        if (con.showAllLabels() != null)
         */

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
