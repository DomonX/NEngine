package domonx.zoo.web.connection;

public class NeWebMessageDeckSizeResponse extends NeWebMessageResponse {
	public NeWebMessageDeckSizeResponse() {
		type = "deckSize";
	}
	public int size;
}
