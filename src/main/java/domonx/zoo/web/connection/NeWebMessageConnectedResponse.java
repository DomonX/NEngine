package domonx.zoo.web.connection;

public class NeWebMessageConnectedResponse extends NeWebMessageResponse {
	public NeWebMessageConnectedResponse() {
		type = "connected";
	}
	public String guid;
}
