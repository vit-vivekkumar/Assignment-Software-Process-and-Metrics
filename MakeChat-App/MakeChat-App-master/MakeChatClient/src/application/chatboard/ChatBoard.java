package application.chatboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.Message;
import application.StandardClient;
import application.database.DataQuery;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * @author Arafin
 *
 */

public class ChatBoard implements Initializable{

	private String clientMessage;
	
	private DataQuery dataQuery;
	private ArrayList<Message> arrayList;
	private ArrayList<Message> messageSaveList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatPane.getStylesheets().add(getClass().getResource("chatboard.css").toExternalForm());
		
		messageSaveList = new ArrayList<>();
	}
	
	@FXML
	public void saveMessages() {
		
		FileChooser fileChooser = new FileChooser();

		//Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//Show save file dialog
		File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
		
		for(int i = 0; i < messageSaveList.size(); i++) {
			BufferedWriter bw = null;
			
			try {
				bw = new BufferedWriter(new FileWriter(file, true));
				bw.write("------------------------");
				bw.newLine();
				bw.write("User: " + messageSaveList.get(i).getUserName());
				bw.newLine();
				bw.write("Msg: " + messageSaveList.get(i).getMessage());
				bw.newLine();
				bw.write("DateTime: " + messageSaveList.get(i).getMsgProcessTime());
				bw.newLine();
				bw.newLine();
				bw.flush();
			} catch (IOException ioe) {
				ioe.printStackTrace();
		    } finally {
		    	if (bw != null) try {
					bw.close();
				} catch (IOException ioe2) {
			    	ioe2.printStackTrace();
				}
			}
		}
	}
	
	public void initConnectionStatus(String connection, String userName, String url, String port) {
		Task<Void> initConnection = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				userNameLabel.setText(userName);
				notificationLabel.setText("Wait!! Checking Previous Chats...");

				dataQuery = new DataQuery();
				arrayList = dataQuery.retrieveMessages(url, port);
				
				for(int i = 0; i < arrayList.size(); i++) {
					addPreviousChat(arrayList.get(i));
				}
				arrayList.clear();
				
				return null;
			}
		};
		
		initConnection.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent worker) {
				
				if(connection.equals("FAILED")) {
					connectionStatus.setTextFill(Color.RED);
					connectionStatus.setText("Connection Suspended");
					notificationLabel.setText("No connection!!");
				} else {
					connectionStatus.setTextFill(Color.BLACK);
					connectionStatus.setText(connection);
				}
				
				notificationLabel.setText("Updated ChatBox");
			}
		});
		
		new Thread(initConnection).start();
	}
	
	protected void addPreviousChat(Message messageObj) {
		
		messageSaveList.add(messageObj);
		
        if (messageObj.getUserName().equals(userNameLabel.getText().toString())) {

            Label messageLabel = new Label();
            messageLabel.setText(messageObj.getMessage());
            messageLabel.setTextFill(Color.WHITE);
            
            Label userLabel = new Label();
            userLabel.setText(messageObj.getUserName());
            userLabel.setStyle("-fx-font-weight: bold;");
            userLabel.setTextFill(Color.WHITE);
            
            VBox vBox = new VBox(2);
            CornerRadii cornerRadi = new CornerRadii(5f);
            BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(64, 128, 128), cornerRadi, null);
            vBox.setBackground(new Background(backgroundFill));
            vBox.setPadding(new Insets(5f));
            vBox.getChildren().addAll(userLabel, messageLabel);
            
            Label dateLabel = new Label();
            dateLabel.setText(messageObj.getMsgProcessTime());
            dateLabel.setStyle("-fx-font-size: 10;");
            dateLabel.setTextFill(Color.GRAY);
            dateLabel.setAlignment(Pos.CENTER);
            dateLabel.setMaxHeight(Double.MAX_VALUE);

            HBox x = new HBox(2);
            x.setMaxWidth(chatPane.getWidth() - 20);
            x.setAlignment(Pos.TOP_RIGHT);
            x.getChildren().addAll(dateLabel, vBox);
            
            chatPane.getItems().add(x);
        } else {
        	
            Label messageLabel = new Label();
            messageLabel.setText(messageObj.getMessage());
            messageLabel.setTextFill(Color.BLACK);
            
            Label userLabel = new Label();
            userLabel.setText(messageObj.getUserName());
            userLabel.setStyle("-fx-font-weight: bold;");
            userLabel.setTextFill(Color.BLACK);
            
            VBox vBox = new VBox(2);
            CornerRadii cornerRadi = new CornerRadii(5f);
            BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(218, 218, 218), cornerRadi, null);
            vBox.setBackground(new Background(backgroundFill));
            vBox.setPadding(new Insets(5f));
            vBox.getChildren().addAll(userLabel, messageLabel);
            
            Label dateLabel = new Label();
            dateLabel.setText(messageObj.getMsgProcessTime());
            dateLabel.setStyle("-fx-font-size: 10;");
            dateLabel.setTextFill(Color.GRAY);
            dateLabel.setAlignment(Pos.CENTER);
            dateLabel.setMaxHeight(Double.MAX_VALUE);
            
            HBox x = new HBox(2);
            x.setMaxWidth(chatPane.getWidth() - 20);
            x.setAlignment(Pos.TOP_LEFT);
            x.getChildren().addAll(vBox, dateLabel);
            
            chatPane.getItems().add(x);
        }
	}

	@FXML
	public void sendMsg() throws IOException {
		clientMessage = textMessage.getText().toString();
		textMessage.clear();
				
		if(!clientMessage.isEmpty()) {
			StandardClient.send(clientMessage);
		}
	}
	
	public synchronized void addToChat(Message messageObj) {
		
		messageSaveList.add(messageObj);
		
        Task<HBox> othersMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
//                Image image = new Image(getClass().getClassLoader().getResource("images/" + msg.getPicture() + ".png").toString());
//                ImageView profileImage = new ImageView(image);
//                profileImage.setFitHeight(32);
//                profileImage.setFitWidth(32);
                
                Label messageLabel = new Label();
                messageLabel.setText(messageObj.getMessage());
                messageLabel.setTextFill(Color.BLACK);
                
                Label userLabel = new Label();
                userLabel.setText(messageObj.getUserName());
                userLabel.setStyle("-fx-font-weight: bold;");
                userLabel.setTextFill(Color.BLACK);
                
                VBox vBox = new VBox(2);
                CornerRadii cornerRadi = new CornerRadii(5f);
                BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(218, 218, 218), cornerRadi, null);
                vBox.setBackground(new Background(backgroundFill));
                vBox.setPadding(new Insets(5f));
                vBox.getChildren().addAll(userLabel, messageLabel);
                
                
                Label dateLabel = new Label();
                dateLabel.setText(messageObj.getMsgProcessTime());
                dateLabel.setStyle("-fx-font-size: 10;");
                dateLabel.setTextFill(Color.GRAY);
                dateLabel.setAlignment(Pos.CENTER);
                dateLabel.setMaxHeight(Double.MAX_VALUE);
                
                HBox x = new HBox(2);
                x.setMaxWidth(chatPane.getWidth() - 20);
                x.setAlignment(Pos.TOP_LEFT);
                x.getChildren().addAll(vBox, dateLabel);

                return x;
            }
        };

        othersMessages.setOnSucceeded(event -> {
            chatPane.getItems().add(othersMessages.getValue());
        });


        Task<HBox> yourMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
//                Image image = userImageView.getImage();
//                ImageView profileImage = new ImageView(image);
//                profileImage.setFitHeight(32);
//                profileImage.setFitWidth(32);

                Label messageLabel = new Label();
                messageLabel.setText(messageObj.getMessage());
                messageLabel.setTextFill(Color.WHITE);
                
                Label userLabel = new Label();
                userLabel.setText(messageObj.getUserName());
                userLabel.setStyle("-fx-font-weight: bold;");
                userLabel.setTextFill(Color.WHITE);
                
                VBox vBox = new VBox(2);
                CornerRadii cornerRadi = new CornerRadii(5f);
                BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(64, 128, 128), cornerRadi, null);
                vBox.setBackground(new Background(backgroundFill));
                vBox.setPadding(new Insets(5f));
                vBox.getChildren().addAll(userLabel, messageLabel);
                
                Label dateLabel = new Label();
                dateLabel.setText(messageObj.getMsgProcessTime());
                dateLabel.setStyle("-fx-font-size: 10;");
                dateLabel.setTextFill(Color.GRAY);
                dateLabel.setAlignment(Pos.CENTER);
                dateLabel.setMaxHeight(Double.MAX_VALUE);

                HBox x = new HBox(2);
                x.setMaxWidth(chatPane.getWidth() - 20);
                x.setAlignment(Pos.TOP_RIGHT);
                x.getChildren().addAll(dateLabel, vBox);

                return x;
            }
        };
        
        yourMessages.setOnSucceeded(event -> {
        	chatPane.getItems().add(yourMessages.getValue());
        });

        
        if (messageObj.getUserName().equals(userNameLabel.getText().toString())) {
            Thread t2 = new Thread(yourMessages);
            t2.setDaemon(true);
            t2.start();
        } else {
            Thread t = new Thread(othersMessages);
            t.setDaemon(true);
            t.start();
        }
	}
	
	@FXML
	public void exitConnection() {
		Platform.exit();
		System.exit(0);
	}
	
	@FXML private TextArea textMessage;
	
	@FXML private ListView<HBox> chatPane;
	@FXML private Label connectionStatus;
	@FXML private Label userNameLabel;
	@FXML private Label notificationLabel;
}
