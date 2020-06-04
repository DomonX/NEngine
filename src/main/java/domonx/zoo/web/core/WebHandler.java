package domonx.zoo.web.core;
import java.net.Socket;

public class WebHandler {
	private WebWriter writer;
	private WebReader reader;
	private Thread readerThread;
	public WebHandler(WebDataListener observer, Socket socket) {
		writer = new WebWriter(socket);
		reader = new WebReader(observer, socket);
		readerThread = new Thread(reader);
		readerThread.start();
	}
	public void write(String message) {
		writer.write(message);
	}
	public void close() {
		reader.close();
	}
}
