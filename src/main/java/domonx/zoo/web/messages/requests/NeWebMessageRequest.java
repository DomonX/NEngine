package domonx.zoo.web.messages.requests;

import domonx.zoo.web.messages.NeWebMessageTypeRequest;

public class NeWebMessageRequest {
	
	public NeWebMessageRequest(NeWebMessageTypeRequest type) {
		super();
		this.type = type;
	}
	public NeWebMessageTypeRequest type;
	public String playerGuid;
}
