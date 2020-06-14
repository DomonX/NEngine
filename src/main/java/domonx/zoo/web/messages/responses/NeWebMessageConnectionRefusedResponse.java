package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageConnectionRefusedResponse extends NeWebMessageResponse {
	public NeWebMessageConnectionRefusedResponse() {
		super(NeWebMessageTypeResponse.CONNECTION_REFUSED);
	}

}
