package domonx.zoo.web.messages.responses;

import domonx.zoo.web.main.WebPlayer;
import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageClientDataResponse extends NeWebMessageResponse{
	public NeWebMessageClientDataResponse() {
		super(NeWebMessageTypeResponse.CLIENT_DATA);
	}
	public WebPlayer data;
}
