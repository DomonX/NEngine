package domonx.zoo.web.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import domonx.zoo.web.core.*;
public abstract class WebServer implements WebClientWaiter, WebDataListener{
	
	private WebClientConnector connector;
	private ServerSocket socket;
	private boolean valid = true;
	private Thread connectorThread;
	protected HashMap<String, WebHandler> clients;
	
	protected WebServer(int port) {
		clients = new HashMap<>();
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			valid = false;
			System.out.println("Error in Creating socket");
			return;
		}
		connector = new WebClientConnector(this, socket);
		connectorThread = new Thread(connector);
		connectorThread.start();
	}
	
	public void write(String message, String clientId) {
		if(!valid) {
			return;
		}
		System.out.println(clientId);
		clients.get(clientId).write(message);
	}
	
	public void broadcast(String message, String clientId) {
		clients.forEach((String key, WebHandler client) -> {
			if(key.equals(clientId)) {
				return;
			}
			client.write(message);
		});
	}

	@Override
	public void addClient(Socket client) {
		WebHandler clientHndl = new WebHandler(this, client);
		if(!canNextClientConnect()) {
			clientHndl.write(getRefusedConnectionMessage());
			clientHndl.close();
			return;
		}
		String id = getClientID();
		clientHndl.write(getAfterConnectedMessage(id));
		clients.put(id, clientHndl);
		sendAfterConnectionMessages(id);
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Error in closing socket");
			e.printStackTrace();
		}
	}
	
	public abstract String getClientID();
	
	public abstract String getAfterConnectedMessage(String id);
	
	public abstract boolean canNextClientConnect();
	
	public abstract String getRefusedConnectionMessage();
	
	public abstract void sendAfterConnectionMessages(String id);

}
