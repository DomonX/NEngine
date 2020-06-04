package domonx.zoo.web.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.cards.NeCardCodes;
import domonx.zoo.web.connection.NeWebMessageConnectedResponse;
import domonx.zoo.web.connection.NeWebMessageConnectionRefused;
import domonx.zoo.web.connection.NeWebMessageDeckSizeResponse;
import domonx.zoo.web.connection.NeWebMessageDrawResponse;
import domonx.zoo.web.connection.NeWebMessageOpponentHandChange;
import domonx.zoo.web.connection.NeWebMessageRequest;
import domonx.zoo.web.core.WebHandler;
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
		NeWebMessageRequest msg = null;
		try {
			msg = gson.fromJson(data, NeWebMessageRequest.class);
		} catch (IllegalStateException e) {
			System.out.println("Unknown Message");
			return;
		}
		if(msg.type.equals("draw")) {
			String type = drawCard();
			String guid = GUIDGenerator.get();
			NeWebMessageDrawResponse res = new NeWebMessageDrawResponse();
			res.cardType = type;
			res.cardGuid = guid;
			String resMsg = gson.toJson(res);
			WebPlayer player = getPlayerByGUID(msg.playerGuid);
			player.hand.add(guid);
			write(resMsg, msg.playerGuid);
			String res2Msg = getAfterDrawMessage(guid);
			broadcast(res2Msg, msg.playerGuid);
		}	
		if(msg.type.equals("deckSize")) {
			NeWebMessageDeckSizeResponse res = new NeWebMessageDeckSizeResponse();
			res.size = cards.size();
			res.type = msg.type;
			String resMsg = gson.toJson(res);
			write(resMsg, msg.playerGuid);
		}
	}
	
	private String drawCard() {
		if(cards.isEmpty()) {
			loadAndShuffle();
		}
		return cards.remove(0);
	}
	
	private void loadAndShuffle() {
		addCards(NeCardCodes.Chamaeleon, 20);
		addCards(NeCardCodes.Spider, 20);
		int cardsNumber = NeCardCodes.getCardNumber();
		for(int i = 0; i < 20; i++) {
			int index = gen.nextInt(cardsNumber);
			addCards(NeCardCodes.getCard(index), 6);			
		}
		Collections.shuffle(cards);
	}
	
	protected void addCards(String code, int number) {
		for(int i = 0; i < number; i++) {
			cards.add(code);
		}
	}

	@Override
	public String getClientID() {
		return GUIDGenerator.get();
	}

	@Override
	public String getAfterConnectedMessage(String id) {
		if(player1.guid.equals("")) {
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
		NeWebMessageConnectionRefused res = new NeWebMessageConnectionRefused();		
		return gson.toJson(res);
	}

	@Override
	public void sendAfterConnectionMessages(String id) {
		WebPlayer opp = getOpponent(id);
		opp.hand.forEach(i -> write(getAfterDrawMessage(i), id));
	}
	
	private WebPlayer getOpponent(String id) {
		if(player1.guid.equals(id)) {
			return player2;
		}
		return player1;
	}
	
	private WebPlayer getPlayerByGUID(String id) {
		if(player1.guid.equals(id)) {
			return player1;
		}
		return player2;
	}
	
	private String getAfterDrawMessage(String cardGuid) {
		NeWebMessageOpponentHandChange res2 = new NeWebMessageOpponentHandChange();
		res2.cardGuid = cardGuid;
		res2.isRemoved = false;
		return gson.toJson(res2);
	}

}
