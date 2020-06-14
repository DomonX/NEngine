package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageOpponentHandChangeResponse extends NeWebMessageResponse {
	public NeWebMessageOpponentHandChangeResponse() {
		super(NeWebMessageTypeResponse.OPPONENT_HAND_CHANGE);
	}

	public boolean isRemoved;
	public String cardGuid;
}
