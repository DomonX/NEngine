package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageCardPickedResponse extends NeWebMessageResponse {
	
	public String playerGuid = "";
	public String cardGuid = "";

	public NeWebMessageCardPickedResponse() {
		super(NeWebMessageTypeResponse.CARD_PICKED);	
	}
	
}
