package domonx.zoo.web.server;
import java.net.Socket;

public interface WebClientWaiter {
	public void addClient(Socket client);
}
