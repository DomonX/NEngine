package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageResponse {
	
	public NeWebMessageTypeResponse type;
	
	NeWebMessageResponse(NeWebMessageTypeResponse type) {
		this.type = type;
	}
}
