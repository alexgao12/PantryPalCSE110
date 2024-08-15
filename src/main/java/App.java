package main.java;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppFrame root = new AppFrame();
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
        String response = AppFrame.getRequest().performCheck();
        if(!response.equals("true")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Server Down");
            alert.setContentText("Server is currently down. Please try again later");
            alert.showAndWait();
            primaryStage.close();
        }
    }
    @Override
    public void stop() {
        
    }
    public static void main(String[] args) {
        launch(args);
    } 
}