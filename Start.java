// Turing Machine for CS2333
// Made By:    Blake Holt
//          &  Gregory Quinlan

// Start.java
// this file is used to initialize the FXML file GUI.fxml and by extension, its controller, Controller.java.

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;
import javafx.scene.control.Button;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
                  
	 Parent fxml = FXMLLoader.load(getClass().getResource("GUI.fxml"));
      Scene scene = new Scene(fxml);
	   stage.setScene(scene);
	   stage.setTitle("Turing Machine");
       stage.show();
	
    }

    public static void main(String[] args) {
        launch(args);
    }
}
