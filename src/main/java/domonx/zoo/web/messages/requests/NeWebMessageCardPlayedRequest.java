package domonx.zoo.web.messages.requests;

import domonx.zoo.web.messages.NeWebMessageTypeRequest;

public class NeWebMessageCardPlayedRequest extends NeWebMessageRequest {
	
	public String cardGuid;
	public String rowGuid;

	public NeWebMessageCardPlayedRequest(String cardGuid, String rowGuid) {
		super(NeWebMessageTypeRequest.CARD_PLAYED);
		this.cardGuid = cardGuid;
		this.rowGuid = rowGuid;
	}

}
