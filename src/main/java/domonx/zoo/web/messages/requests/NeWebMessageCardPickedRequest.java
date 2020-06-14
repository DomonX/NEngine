package domonx.zoo.web.messages.requests;

import domonx.zoo.web.messages.NeWebMessageTypeRequest;

public class NeWebMessageCardPickedRequest extends NeWebMessageRequest {
	
	public String cardGuid = "";

	public NeWebMessageCardPickedRequest() {
		super(NeWebMessageTypeRequest.PICK);
	}

}
