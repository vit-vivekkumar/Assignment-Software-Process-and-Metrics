package application.signin;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import application.StandardClient;
import application.chatboard.ChatBoard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Arafin
 *
 */

public class SignIn implements Initializable {
	
	private String userName;
	private String serverAddress;
	private String portNo;
	
	private boolean connectRequirementCheck;
	private boolean connectionCheck;
	
	private Scene scene;
	
	@FXML
	public void handleConnectButton() throws UnknownHostException, IOException {
		connectRequirementCheck = true;
		
		userName = "@" + userNameEntry.getText().toString();
		serverAddress = serverAddressEntry.getText().toString();
		portNo = portNumberEntry.getText().toString();
		
		if(userNameEntry.getText().toString().equals("")) {
			connectRequirementCheck = false;
			userNameError.setText("Username is required");
		} else {
			userNameError.setText("");
		}
		
		if(serverAddress.equals("")) {
			connectRequirementCheck = false;
			serverAddressError.setText("Server Address is required");
		}
		
		if(portNo.equals("")) {
			connectRequirementCheck = false;
			serverAddressError.setText("Port number is required");
		}
		
		if(serverAddress.equals("") && portNo.equals("")) {
			connectRequirementCheck = false;
			serverAddressError.setText("Server Address and Port number is required");
		}
		
		if(!serverAddress.equals("") && !portNo.equals("")) {
			serverAddressError.setText("");
		}
		
//		serverAddress = "192.168.0.63"; // This should be removed
//		portNo = "3000"; // This should be removed
//		connectRequirementCheck = true; // This should be removed
		if(connectRequirementCheck) {
			connectStatusResult.setTextFill(Color.rgb(0, 0, 210));
			connectStatusResult.setText("Connecting to Server...");

			connectionCheck = true;
			if(connectionCheck) {
		        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/application/chatboard/ChatBoard.fxml"));
		        Parent window = (Pane) fmxlLoader.load();
		        ChatBoard controller = fmxlLoader.<ChatBoard>getController();
		        
				new StandardClient(serverAddress, Integer.valueOf(portNo), userName, controller).start(); // Address and Port
				
		        this.scene = new Scene(window);
		        showScene();
			} else {
				connectStatusResult.setTextFill(Color.rgb(232,0,5));
				connectStatusResult.setText("No Server found!!");
			}
		} else {
			connectStatusResult.setTextFill(Color.rgb(232,0,5));
			connectStatusResult.setText("Connection not possible!! Please fillup all the fields.");
		}
	}
	
    public void showScene() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) userNameError.getScene().getWindow();
            stage.setResizable(false);

            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            stage.setScene(this.scene);
        });
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameEntry.setFocusTraversable(false);
		emailEntry.setFocusTraversable(false);
		serverAddressEntry.setFocusTraversable(false);
		portNumberEntry.setFocusTraversable(false);
		
        Image image = new Image(getClass().getClassLoader().getResource("icons/makechat_icon.png").toString());
        imageView.setImage(image);
	}
	
	@FXML private Label userNameError;
	@FXML private Label emailError;
	@FXML private Label serverAddressError;
	@FXML private Label connectStatusResult;
	
	@FXML private TextField userNameEntry;
	@FXML private TextField emailEntry;
	@FXML private TextField serverAddressEntry;
	@FXML private TextField portNumberEntry;
	
	@FXML private ImageView imageView;
		
	@FXML private Button connectButton;
}
