package domonx.zoo.web.messages.requests;

import domonx.zoo.web.messages.NeWebMessageTypeRequest;

public class NeWebMessageCardsSwappedRequest extends NeWebMessageRequest {
	
	public String[] guids;

	public NeWebMessageCardsSwappedRequest() {
		super(NeWebMessageTypeRequest.SWAP);
	}	

}
