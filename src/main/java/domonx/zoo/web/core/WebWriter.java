package domonx.zoo.web.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WebWriter {
	private PrintWriter out;
	private boolean valid = true;

	public WebWriter(Socket socket) {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Error in creating Print Writer");
			valid = false;
		}
	}

	public boolean isValid() {
		return valid;
	}

	public void write(String message) {
		if (!valid) {
			return;
		}
		System.out.println("Me: " + message);
		out.println(message);
	}

	public void close() {
		if (valid) {
			out.close();
		}
	}
}
