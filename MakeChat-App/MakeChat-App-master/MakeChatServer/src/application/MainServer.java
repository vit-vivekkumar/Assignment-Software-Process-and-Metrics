package application;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import application.database.DataInsert;
import application.database.ServerRegister;

/**
 * @author Arafin
 *
 */

public class MainServer extends Thread {
	
	private int port;
	static HashSet<ObjectOutputStream> writers = new HashSet<>();
	private ServerSocket serverSocket;
	
	public MainServer(int port) {
		this.port = port;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port) {
				protected void finalize() throws IOException {
					this.close();
				}
			};
			System.out.println("Port is now open.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {
			// accepts a new client
			Socket clientSocket;
			try {
				clientSocket = serverSocket.accept();
				System.out.println("Connection established with client: " + clientSocket.getRemoteSocketAddress());
				
				// create a new thread for client handling
				new Thread(new ClientHandler(this, clientSocket, port)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void broadcastMessages(Message messageObj) throws IOException{
		for (ObjectOutputStream writer : writers) {
			// Add userList later
			writer.writeObject(messageObj);
			writer.reset();
		}
	}
}

class ClientHandler implements Runnable {

	private MainServer server;
	private Socket serverSocket;
	
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	
	private String hostAddress;
	private String portNo;
	
	public ClientHandler(MainServer server, Socket serverSocket, int port) {
		this.server = server;
		this.serverSocket = serverSocket;
		portNo = String.valueOf(port);
		hostAddress = serverSocket.getInetAddress().getHostAddress();
		
		// Register if Server is new
		new ServerRegister(hostAddress, portNo).registerToDb();
		
		inputStream = null;
		objectInputStream = null;
	}

	@Override
	public void run() {
		
		try {
			outputStream = serverSocket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			
			// when there is a new message, broadcast to all
			inputStream = serverSocket.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			
			MainController.connectedDeviceList.add(serverSocket.getInetAddress().getHostName() + " -> " + serverSocket.getInetAddress().getHostAddress());
			// add client message to list
            MainServer.writers.add(objectOutputStream);
			
			while(serverSocket.isConnected()) {
				Message messageObj = (Message) objectInputStream.readObject();

				if(messageObj != null) {
					System.out.println(messageObj.getMessage());
					server.broadcastMessages(messageObj);
					new DataInsert(hostAddress, portNo, messageObj.getUserName(), messageObj.getMessage(), messageObj.getMsgProcessTime()).insert();
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	private synchronized  void closeConnection() {
        if (objectOutputStream != null){
        	MainServer.writers.remove(objectOutputStream);
        }
        
        if (outputStream != null){
            try {
            	outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (inputStream != null){
            try {
            	inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (objectInputStream != null){
            try {
            	objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
