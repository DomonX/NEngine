package domonx.zoo.web.client;

import java.io.IOException;

import java.net.Socket;

import domonx.zoo.web.core.*;

public abstract class WebClient implements WebDataListener {

	private WebHandler handler;
	private boolean valid = true;

	private Socket socket;

	public WebClient(String ip, int port) {
		super();
		try {
			socket = new Socket(ip, port);
			System.out.println("Connected");
		} catch (IOException e) {
			valid = false;
			System.out.println(e);
			return;
		}
		handler = new WebHandler(this, socket);
	}

	public boolean isValid() {
		return valid;
	}

	public void write(String message) {
		if (!valid) {
			return;
		}
		handler.write(message);
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Error in closing socket");
			e.printStackTrace();
		}
	}

}
