package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageDeckSizeResponse extends NeWebMessageResponse {
	public NeWebMessageDeckSizeResponse() {
		super(NeWebMessageTypeResponse.DECK_SIZE);
	}
	public int size;
}
