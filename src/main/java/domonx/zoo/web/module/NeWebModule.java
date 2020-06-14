package domonx.zoo.web.module;

import com.google.gson.Gson;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.web.main.SpecializedWebClient;
import domonx.zoo.web.messages.NeWebMessageTypeResponse;
import domonx.zoo.web.messages.responses.NeWebMessageCardPickedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageCardPlayedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageCardsSwappedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageClientDataResponse;
import domonx.zoo.web.messages.responses.NeWebMessageConnectedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageDeckSizeResponse;
import domonx.zoo.web.messages.responses.NeWebMessageDrawResponse;
import domonx.zoo.web.messages.responses.NeWebMessageOpponentHandChangeResponse;
import domonx.zoo.web.messages.responses.NeWebMessageResponse;

public class NeWebModule implements Runnable, NeWebListener {
	public SpecializedWebClient c;
	private NeAbstractGameStateController controller;
	private Gson gson;

	public NeWebModule(NeAbstractGameStateController controller) {
		super();
		this.controller = controller;
		c = new SpecializedWebClient(NeConfiguration.getServerIp(), NeConfiguration.getServerPort(), this);
		gson = new Gson();
	}

	public void connect() {
		c.connect();
	}

	@Override
	public void run() {
		System.out.println("Starting connection");
		while (c.isValid()) {

		}
	}

	@Override
	public void signal(String data) {
		NeWebMessageResponse msgRaw = gson.fromJson(data, NeWebMessageResponse.class);
		System.out.println("Server: " + data);
		if (msgRaw.type.equals(NeWebMessageTypeResponse.DRAW)) {
			NeWebMessageDrawResponse msg = gson.fromJson(data, NeWebMessageDrawResponse.class);
			controller.afterDrawCard(msg.cardGuid, msg.cardType);
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.DECK_SIZE)) {
			NeWebMessageDeckSizeResponse msg = gson.fromJson(data, NeWebMessageDeckSizeResponse.class);
			controller.afterGetDeckSize(msg.size);
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.CONNECTED)) {
			NeWebMessageConnectedResponse msg = gson.fromJson(data, NeWebMessageConnectedResponse.class);
			controller.afterConnected(msg.guid);
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.OPPONENT_HAND_CHANGE)) {
			NeWebMessageOpponentHandChangeResponse msg = gson.fromJson(data,
					NeWebMessageOpponentHandChangeResponse.class);
			controller.afterOpponentHandChange(msg.cardGuid, msg.isRemoved);
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.CLIENT_DATA)) {
			NeWebMessageClientDataResponse msg = gson.fromJson(data, NeWebMessageClientDataResponse.class);
			controller.afterDataConnected(msg.data);
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.CARD_PLAYED)) {
			NeWebMessageCardPlayedResponse msg = gson.fromJson(data, NeWebMessageCardPlayedResponse.class);
			controller.afterOpponentPlayCard(msg.cardGuid, msg.rowGuid, msg.playerGuid, msg.cardType);
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.CARDS_SWAPPED)) {
			NeWebMessageCardsSwappedResponse msg = gson.fromJson(data, NeWebMessageCardsSwappedResponse.class);
			for(String removedGuid : msg.cardsRemoved) {
				controller.afterOpponentHandChange(removedGuid, true);
			}
		}
		if (msgRaw.type.equals(NeWebMessageTypeResponse.CARD_PICKED)) {
			NeWebMessageCardPickedResponse msg = gson.fromJson(data, NeWebMessageCardPickedResponse.class);
			controller.afterOpponentPickCard(msg.cardGuid, msg.playerGuid);
		}
	}

}
