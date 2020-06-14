package domonx.zoo.web.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.cards.NeCardCodes;
import domonx.zoo.web.messages.NeWebMessageTypeRequest;
import domonx.zoo.web.messages.NeWebMessageTypeResponse;
import domonx.zoo.web.messages.requests.NeWebMessageCardPickedRequest;
import domonx.zoo.web.messages.requests.NeWebMessageCardPlayedRequest;
import domonx.zoo.web.messages.requests.NeWebMessageCardsSwappedRequest;
import domonx.zoo.web.messages.requests.NeWebMessageRequest;
import domonx.zoo.web.messages.responses.NeWebMessageCardPickedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageCardPlayedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageCardsSwappedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageClientDataResponse;
import domonx.zoo.web.messages.responses.NeWebMessageConnectedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageConnectionRefusedResponse;
import domonx.zoo.web.messages.responses.NeWebMessageDeckSizeResponse;
import domonx.zoo.web.messages.responses.NeWebMessageDrawResponse;
import domonx.zoo.web.messages.responses.NeWebMessageOpponentHandChangeResponse;
import domonx.zoo.web.server.*;

public class SpecializedWebServer extends WebServer {

	private Gson gson;

	private List<String> cards = new ArrayList<>();

	private Random gen;

	private WebPlayer player1 = new WebPlayer();
	private WebPlayer player2 = new WebPlayer();

	public SpecializedWebServer(int port) {
		super(port);
		gson = new Gson();
		gen = new Random();
	}

	@Override
	public void read(String data) {
		NeWebMessageRequest msgRaw = null;
		System.out.println("Client: " + data);
		try {
			msgRaw = gson.fromJson(data, NeWebMessageRequest.class);
		} catch (IllegalStateException e) {
			System.out.println("Unknown Message");
			return;
		}
		if (msgRaw.type.equals(NeWebMessageTypeRequest.DRAW)) {
			String type = drawCard();
			String guid = GUIDGenerator.get();
			NeWebMessageDrawResponse res = new NeWebMessageDrawResponse();
			res.cardType = type;
			res.cardGuid = guid;
			String resMsg = gson.toJson(res);
			WebPlayer player = getPlayerByGUID(msgRaw.playerGuid);
			player.hand.put(guid, type);
			write(resMsg, msgRaw.playerGuid);
			String res2Msg = getAfterDrawMessage(guid);
			broadcast(res2Msg, msgRaw.playerGuid);
		}
		if (msgRaw.type.equals(NeWebMessageTypeRequest.DECK_SIZE)) {
			NeWebMessageDeckSizeResponse res = new NeWebMessageDeckSizeResponse();
			res.size = cards.size();
			res.type = NeWebMessageTypeResponse.DRAW;
			String resMsg = gson.toJson(res);
			write(resMsg, msgRaw.playerGuid);
		}
		if (msgRaw.type.equals(NeWebMessageTypeRequest.CARD_PLAYED)) {
			NeWebMessageCardPlayedRequest msg = gson.fromJson(data, NeWebMessageCardPlayedRequest.class);
			handleCardPlayedRequest(msg);
		}
		if(msgRaw.type.equals(NeWebMessageTypeRequest.SWAP)) {
			NeWebMessageCardsSwappedRequest msg = gson.fromJson(data, NeWebMessageCardsSwappedRequest.class);
			handleCardsSwapRequest(msg);
		}
		if(msgRaw.type.equals(NeWebMessageTypeRequest.PICK)) {
			NeWebMessageCardPickedRequest msg = gson.fromJson(data, NeWebMessageCardPickedRequest.class);
			NeWebMessageCardPickedResponse res = new NeWebMessageCardPickedResponse();
			res.cardGuid = msg.cardGuid;
			res.playerGuid = msg.playerGuid;
			broadcast(gson.toJson(res), res.playerGuid);
		}
	}
	
	private void handleCardPlayedRequest(NeWebMessageCardPlayedRequest msg) {
		NeWebMessageCardPlayedResponse res = new NeWebMessageCardPlayedResponse();
		res.cardGuid = msg.cardGuid;
		res.rowGuid = msg.rowGuid;
		if (player1.hand.containsKey(msg.cardGuid) && player1.rows.containsKey(msg.rowGuid)) {
			String cardCode = player1.hand.remove(msg.cardGuid);
			player1.rows.get(msg.rowGuid).addCard(msg.cardGuid, cardCode);
			res.playerGuid = player1.guid;
			res.cardType = cardCode;
			broadcast(gson.toJson(res), player1.guid);
		} else if (player2.hand.containsKey(msg.cardGuid) && player2.rows.containsKey(msg.rowGuid)) {
			String cardCode = player2.hand.remove(msg.cardGuid);
			player2.rows.get(msg.rowGuid).addCard(msg.cardGuid, cardCode);
			res.playerGuid = player2.guid;
			res.cardType = cardCode;
			broadcast(gson.toJson(res), player2.guid);
		} else {
			System.out.println("Error: Card " + msg.cardGuid + " doesnt exists in either player hands");
		}
	}
	
	private void handleCardsSwapRequest(NeWebMessageCardsSwappedRequest msg) {
		WebPlayer pl = getPlayerByGUID(msg.playerGuid);
		NeWebMessageCardsSwappedResponse res = new NeWebMessageCardsSwappedResponse();
		res.cardsRemoved = msg.guids;
		// "NE_33_REWERS"
		for(String guid : msg.guids) {
			String code = pl.hand.remove(guid);
			this.cards.add(this.cards.size(), code);
		}
		res.playerGuid = msg.playerGuid;
		broadcast(gson.toJson(res), res.playerGuid);
	}

	private String drawCard() {
		if (cards.isEmpty()) {
			loadAndShuffle();
		}
		return cards.remove(0);
	}

	private void loadAndShuffle() {
		addCards(NeCardCodes.Chamaeleon, 20);
		addCards(NeCardCodes.Spider, 20);
		int cardsNumber = NeCardCodes.getCardNumber();
		for (int i = 0; i < 20; i++) {
			int index = gen.nextInt(cardsNumber);
			addCards(NeCardCodes.getCard(index), 6);
		}
		Collections.shuffle(cards);
	}

	protected void addCards(String code, int number) {
		for (int i = 0; i < number; i++) {
			cards.add(code);
		}
	}

	@Override
	public String getClientID() {
		return GUIDGenerator.get();
	}

	@Override
	public String getAfterConnectedMessage(String id) {
		if (player1.guid.equals("")) {
			player1.guid = id;
		} else {
			player2.guid = id;
		}
		NeWebMessageConnectedResponse res = new NeWebMessageConnectedResponse();
		res.guid = id;
		return gson.toJson(res);
	}

	@Override
	public boolean canNextClientConnect() {
		return player1.guid.equals("") || player2.guid.equals("");
	}

	@Override
	public String getRefusedConnectionMessage() {
		NeWebMessageConnectionRefusedResponse res = new NeWebMessageConnectionRefusedResponse();
		return gson.toJson(res);
	}

	@Override
	public void sendAfterConnectionMessages(String id) {
		WebPlayer me = getPlayerByGUID(id);
		toAllPlayerData(me);
		sendOpponentDataToPlayer(me);
	}

	private void toAllPlayerData(WebPlayer player) {
		NeWebMessageClientDataResponse msg = new NeWebMessageClientDataResponse();
		msg.data = player;
		toAll(gson.toJson(msg));
	}

	private void sendOpponentDataToPlayer(WebPlayer player) {
		WebPlayer opp = getOpponent(player.guid);
		NeWebMessageClientDataResponse msg = new NeWebMessageClientDataResponse();
		msg.data = getHiddenPlayer(opp);
		write(gson.toJson(msg), player.guid);
	}

	private WebPlayer getHiddenPlayer(WebPlayer source) {
		WebPlayer result = new WebPlayer();
		result.guid = source.guid;
		result.rows = source.rows;
		source.hand.forEach((String guid, String code) -> result.hand.put(guid, "NE_33_REWERS"));
		return result;
	}

	private WebPlayer getOpponent(String id) {
		if (player1.guid.equals(id)) {
			return player2;
		}
		return player1;
	}

	private WebPlayer getPlayerByGUID(String id) {
		if (player1.guid.equals(id)) {
			return player1;
		}
		return player2;
	}

	private String getAfterDrawMessage(String cardGuid) {
		NeWebMessageOpponentHandChangeResponse res2 = new NeWebMessageOpponentHandChangeResponse();
		res2.cardGuid = cardGuid;
		res2.isRemoved = false;
		return gson.toJson(res2);
	}

}
