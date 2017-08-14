package stockapp.main;

/*
 * author 1449773
 * icon png from: http://www.freeiconspng.com/free-images/smiley-icon-8157
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application{
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("StockAppOverview.fxml"));
        primaryStage.setTitle("Stock App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("smiley.png")));
        primaryStage.show();
    }
    	
    public static void main(String[] args) {
        launch(args);
    }
}
