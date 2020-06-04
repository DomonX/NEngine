package domonx.zoo.web.module;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.web.main.SpecializedWebServer;

public class NeServer {
	SpecializedWebServer server;
	public NeServer() {
		server = new SpecializedWebServer(NeConfiguration.getServerPort());
	}
	
	public void run() {
		while(server.isValid()) {
			
		}
	}
}
