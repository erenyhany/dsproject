package ds.datastructprjc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class Mainforjar extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Mainforjar.class.getResource("GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ourProject");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();


    }
}
