package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * @author Arafin
 *
 */

public class Main extends Application {
	
    private static Stage primaryStage;
	
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("application/signin/Signin.fxml"));
        stage.setTitle("MakeChat App - Chat with Friends");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResource("icons/makechat_icon.png").toString()));
        
        Scene mainScene = new Scene(root);
        mainScene.setRoot(root);
        mainScene.getStylesheets().setAll(getClass().getResource("application.css").toExternalForm());

        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.centerOnScreen();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
