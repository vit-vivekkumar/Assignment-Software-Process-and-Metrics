package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Arafin
 *
 */

public class MainController implements Initializable {
		
	public static ArrayList<String> connectedDeviceList = new ArrayList<>();
	private ObservableList<String> dataObservableList;
	private Thread t;
	
	private int portNumber = 3000; // Default
	
	@FXML
	public void openServer() {
		portNumber = Integer.parseInt((portNumberEntry.getText().toString()));
		t = new MainServer(portNumber);
		t.start();
		
		connectionStatus.setText("Server is running!!");
	}
	
	@FXML
	public void stopServer() throws InterruptedException {
		Platform.exit();
		System.exit(0);
		
		if(t.isAlive()) {
			t.join();
		}
	}
	
	@FXML
	public void devices() {
        final Stage deviceListStage = new Stage();
        deviceListStage.initModality(Modality.APPLICATION_MODAL);
        deviceListStage.initOwner(Main.getStage());
        
        dataObservableList = FXCollections.observableArrayList();
        
        for(int j = 0; j < connectedDeviceList.size(); j++) {
        	dataObservableList.add(connectedDeviceList.get(j));
        }
        
        ListView<String> list = new ListView<String>();
        list.setItems(dataObservableList);
        
        StackPane stack = new StackPane();
        stack.getChildren().add(list);
        
        Scene dialogScene = new Scene(stack, 450, 200);
        deviceListStage.setScene(dialogScene);
        deviceListStage.setTitle("List of Connected Devices");
        deviceListStage.show();
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void setStatus(String status) {
		System.out.println(status);
	}

	@FXML private Label connectionStatus;
	@FXML private TextField portNumberEntry;
}
