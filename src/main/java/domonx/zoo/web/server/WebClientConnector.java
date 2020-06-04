package domonx.zoo.web.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebClientConnector implements Runnable{
	private WebClientWaiter observer;
	private ServerSocket socket;
	private boolean isRunning = true;
	public WebClientConnector(WebClientWaiter observer, ServerSocket socket) {
		this.observer = observer;
		this.socket = socket;
	}
	@Override
	public void run() {
		while(isRunning) {
			try {
				System.out.println("Waiting for client");
				Socket clientSocket = socket.accept();
				observer.addClient(clientSocket);
				System.out.println("Client Connected");
			} catch (IOException e) {
				System.out.println("Error in connecting client");
				break;
			}
		}
	}
	public void stop() {
		isRunning = false;
	}
	
}
