package domonx.zoo.web.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebReader implements Runnable {
	private WebDataListener observer;
	private boolean isRunning = true;
	private BufferedReader in;
	private boolean valid = true;

	public WebReader(WebDataListener observer, Socket socket) {
		this.observer = observer;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error in creating BufferedReader");
			valid = false;
			isRunning = false;
		}
	}

	public boolean isValid() {
		return valid;
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				String message = in.readLine();
				observer.read(message);
			} catch (IOException e) {
				System.out.println("Error in reading");
				close();
			}
		}
	}

	public void close() {
		isRunning = false;
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Error in closing buffered reader");
			e.printStackTrace();
		}
	}

}
