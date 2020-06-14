package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageCardPlayedResponse extends NeWebMessageResponse {
	
	public NeWebMessageCardPlayedResponse() {
		super(NeWebMessageTypeResponse.CARD_PLAYED);
	}
	public String cardGuid = "";
	public String cardType = "";
	public String rowGuid = "";
	public String playerGuid = "";
}
