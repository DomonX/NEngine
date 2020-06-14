package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageDrawResponse extends NeWebMessageResponse {
	public NeWebMessageDrawResponse() {
		super(NeWebMessageTypeResponse.DRAW);
	}
	public String cardGuid;
	public String cardType;
}
