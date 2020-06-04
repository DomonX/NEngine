package domonx.zoo.web.main;

import domonx.zoo.web.client.*;
import domonx.zoo.web.module.NeWebListener;

public class SpecializedWebClient extends WebClient {
	
	private NeWebListener listener;

	public SpecializedWebClient(String ip, int port, NeWebListener listener) {
		super(ip, port);
		this.listener = listener;
	}

	@Override
	public void read(String data) {
		listener.signal(data);
	}

}
