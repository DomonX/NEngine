package domonx.zoo.web.module;

import com.google.gson.Gson;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.web.connection.NeWebMessageConnectedResponse;
import domonx.zoo.web.connection.NeWebMessageDeckSizeResponse;
import domonx.zoo.web.connection.NeWebMessageDrawResponse;
import domonx.zoo.web.connection.NeWebMessageOpponentHandChange;
import domonx.zoo.web.connection.NeWebMessageResponse;
import domonx.zoo.web.main.SpecializedWebClient;

public class NeWebModule implements Runnable, NeWebListener{
	public SpecializedWebClient c;
	private NeAbstractGameStateController controller;
	private Gson gson;
	
	public NeWebModule(NeAbstractGameStateController controller) {
		super();
		this.controller = controller;
		c = new SpecializedWebClient(NeConfiguration.getServerIp(), NeConfiguration.getServerPort(), this);
		gson = new Gson();
	}
	@Override
	public void run() {
		System.out.println("Starting connection");
		while(c.isValid()) {
			
		}
	}
	@Override
	public void signal(String data) {
		NeWebMessageResponse msgRaw = gson.fromJson(data, NeWebMessageResponse.class);
		if(msgRaw.type.equals("draw")) {
			NeWebMessageDrawResponse msg = gson.fromJson(data, NeWebMessageDrawResponse.class);
			controller.afterDrawCard(msg.cardGuid, msg.cardType);
		}
		if(msgRaw.type.equals("deckSize")) {
			NeWebMessageDeckSizeResponse msg = gson.fromJson(data, NeWebMessageDeckSizeResponse.class);
			controller.afterGetDeckSize(msg.size);		
		}
		if(msgRaw.type.equals("connected")) {
			NeWebMessageConnectedResponse msg = gson.fromJson(data, NeWebMessageConnectedResponse.class);
			controller.afterConnected(msg.guid);		
		}
		if(msgRaw.type.equals("oppHandChange")) {
			System.out.println("oppHandChange");
			NeWebMessageOpponentHandChange msg = gson.fromJson(data, NeWebMessageOpponentHandChange.class);
			controller.afterOpponentHandChange(msg.cardGuid, msg.isRemoved);
		}
	}
	
	
}
