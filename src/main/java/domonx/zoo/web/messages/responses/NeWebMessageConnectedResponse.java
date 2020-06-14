package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageConnectedResponse extends NeWebMessageResponse {
	public NeWebMessageConnectedResponse() {
		super(NeWebMessageTypeResponse.CONNECTED);
	}
	public String guid;
}
