package domonx.zoo.game.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import domonx.zoo.core.actions.NeAction;
import domonx.zoo.core.actions.NeActionEntityBlured;
import domonx.zoo.core.actions.NeActionEntityClicked;
import domonx.zoo.core.actions.NeActionEntityHovered;
import domonx.zoo.core.actions.NeActionEntityMoved;
import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.interfaces.INePickListener;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.cards.CardFactory;
import domonx.zoo.game.enums.EGameState;
import domonx.zoo.game.enums.ENePickModes;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.state.NeGameState;
import domonx.zoo.game.structures.NeCard;
import domonx.zoo.game.structures.NePlayer;
import domonx.zoo.game.structures.NeRow;
import domonx.zoo.game.structures.gui.NeGuiRow;
import domonx.zoo.game.util.NeGameUtil;
import domonx.zoo.web.main.WebPlayer;
import domonx.zoo.web.messages.NeWebMessageTypeRequest;
import domonx.zoo.web.messages.requests.NeWebMessageCardPickedRequest;
import domonx.zoo.web.messages.requests.NeWebMessageCardPlayedRequest;
import domonx.zoo.web.messages.requests.NeWebMessageCardsSwappedRequest;
import domonx.zoo.web.messages.requests.NeWebMessageRequest;
import domonx.zoo.web.module.NeWebModule;

public class NeGameStateController implements NeAbstractGameStateController, NeAbstractActionListener, INePickListener {

	protected boolean isPicking = false;

	protected ENePickModes pickMode = ENePickModes.NONE;

	protected int cardsToPick = 0;

	private String[] bannedGUIDs;

	private NeGameState state;

	private INePickListener listener;

	private NeWebModule web;

	private Gson gson;

	private String webConnectionString;

	private boolean stateIsLoaded = false;

	private boolean gameAlreadyStarted = false;
	
	private CardFactory cf;

	public NeGameStateController() {
		super();
		gson = new Gson();
	}

	@Override
	public ENePickModes getPickMode() {
		return pickMode;
	}

	public void connectState(NeGameState state) {
		this.state = state;
		cf = new CardFactory(state.getGraphics(), this, this);
	}

	public void connectWeb(NeWebModule module) {
		this.web = module;
	}

	@Override
	public void handleAction(NeAction payload) {
		if (payload instanceof NeActionEntityMoved) {
			handleEntityMoved((NeActionEntityMoved) (payload));
		}
		if (payload instanceof NeActionEntityHovered) {
			handleEntityHovered((NeActionEntityHovered) payload);
		}
		if (payload instanceof NeActionEntityBlured) {
			handleEntityBlured((NeActionEntityBlured) payload);
		}
		if (payload instanceof NeActionEntityClicked) {
			handleEntityClicked((NeActionEntityClicked) payload);
		}
	}

	@Override
	public void pickFromFriendlyRow(int number, INePickListener listener, String[] bannedGuids) {
		pickMode = ENePickModes.ROW;
		setupPickMode(number, listener, bannedGuids);
	}

	@Override
	public void pickFromEnemyRow(int number, INePickListener listener, String[] bannedGuids) {
		pickMode = ENePickModes.OPPONENT_ROW;
		setupPickMode(number, listener, bannedGuids);
	}

	@Override
	public void pickFromDeck(int number, INePickListener listener, String[] bannedGuids) {
		pickMode = ENePickModes.DECK;
		setupPickMode(number, listener, bannedGuids);
	}

	@Override
	public void pickFromDeckFromN(int number, int cardsShown, INePickListener listener, String[] bannedGuids) {
		pickMode = ENePickModes.DECK_N;
		setupPickMode(number, listener, bannedGuids);
	}

	@Override
	public void pickFromFriendlyHand(int number, INePickListener listener, String[] bannedGuids) {
		pickMode = ENePickModes.HAND;
		setupPickMode(number, listener, bannedGuids);
	}

	@Override
	public void pickFromEnemyHand(int number, INePickListener listener, String[] bannedGuids) {
		pickMode = ENePickModes.OPPONENT_HAND;
		setupPickMode(number, listener, bannedGuids);
	}

	@Override
	public void moveCardFromRowToTemp(String guid, String rowGuid) {
		INeCard temp = state.getMainPlayer().rowsHolder.rows.get(rowGuid).cards.get(guid);
		state.getRows().get(rowGuid).removeCard(guid);
		state.getTemporaryZone().put(guid, temp);
	}

	@Override
	public void moveCardFromHandToTemp(String guid, String playerGuid) {
		INeCard temp = getPlayerByGuid(playerGuid).hand.getCards().get(guid);
		getPlayerByGuid(playerGuid).hand.removeCard(guid);
		state.getTemporaryZone().put(temp.getGuid(), temp);
	}

	@Override
	public void moveCardFromGraveyardToTemp(String guid) {
	}

	@Override
	public void moveCardFromDeckToTemp(int index) {
	}

	@Override
	public void moveCardFromTempToRow(String guid, String rowGuid) {
		INeCard temp = state.getTemporaryZone().get(guid);
		state.getTemporaryZone().remove(guid);
		state.getRows().get(rowGuid).addCard((NeCard)temp);
	}

	@Override
	public void moveCardFromTempToHand(String guid, String playerGuid) {
		INeCard temp = state.getTemporaryZone().get(guid);
		NePlayer player = getPlayerByGuid(playerGuid);
		player.hand.addCard(temp);
		state.getTemporaryZone().remove(guid);
	}

	@Override
	public void moveCardFromTempToGraveyard(String guid) {
	}

	@Override
	public void createCardInTemp(String type, String guid) {
		INeCard temp = cf.get(type, guid);
		state.getTemporaryZone().put(temp.getGuid(), temp);
	}

	@Override
	public List<INeCard> getPickBuffer() {
		return state.getPickBuffer();
	}

	@Override
	public void addToPickBuffer(INeCard newCard) {
		int pickedIndex = -1;
		int i = 0;
		for (String bannedGuid : bannedGUIDs) {
			if (bannedGuid.equals(newCard.getGuid())) {
				return;
			}
		}
		for (INeCard c : state.getPickBuffer()) {
			pickedIndex = newCard.getGuid().equals(c.getGuid()) ? i : pickedIndex;
			i++;
		}
		if (pickedIndex != -1) {
			state.getPickBuffer().remove(pickedIndex);
			newCard.setSelected(false);
			cardsToPick++;
			return;
		}
		if (cardsToPick > -1 && cardsToPick < 1) {
			return;
		}
		state.getPickBuffer().add(newCard);
		cardsToPick--;
		newCard.setSelected(true);
	}

	@Override
	public void submitPicks() {
		pickModeOff();
		for (INeCard temp : state.getPickBuffer()) {
			temp.setSelected(false);
		}
		if (listener != null) {
			listener.afterPick();
		}
	}

	protected void setupPickMode(int cardsToPick, INePickListener listener, String[] bannedGUIDs) {
		state.getPickBuffer().clear();
		setState(EGameState.PICKING);
		this.cardsToPick = cardsToPick;
		if (cardsToPick > 0) {
			pickModeOn();
			this.bannedGUIDs = bannedGUIDs;
			this.listener = listener;
		} else {
			pickModeOff();
		}
	}

	protected void setupRowsPickMode() {
		state.getPickBuffer().clear();
		setState(EGameState.PICKING_ROWS);
		this.cardsToPick = -1;
		pickMode = ENePickModes.ROW;
		listener = this;
		this.bannedGUIDs = findCardGuidsThatCannotBePickedFromRow();
	}

	protected void setupCardsSwap() {
		state.getPickBuffer().clear();
		setState(EGameState.SWAPING_CARDS);
		this.cardsToPick = 3;
		pickMode = ENePickModes.HAND;
		listener = this;
		bannedGUIDs = new String[0];
	}

	protected String[] findCardGuidsThatCannotBePickedFromRow() {
		NePlayer mainPlayer = state.getMainPlayer();
		List<String> elements = new ArrayList<>();
		mainPlayer.rowsHolder.rows.values().stream().filter(i -> i.cards.values().size() < 3).forEach(i -> {
			i.cards.values().stream().forEach(j -> elements.add(j.getGuid()));
		});
		return elements.toArray(new String[0]);
	}

	protected void handleEntityMoved(NeActionEntityMoved payload) {
		if (payload.signature == EControllerSignatures.CARD) {
			handleCardMoved(payload);
		}
	}

	protected void handleCardMoved(NeActionEntityMoved payload) {
		NeEntity src = NeGameUtil.getEntityByPath(NeGameUtil.getArrayFromPath(payload.srcGUIDPath),
				state.getGraphics().screen);
		if (state.getCurrentState() != EGameState.PLAYING_CARDS && state.getCurrentState() != EGameState.JUST_STARTED) {
			src.getController().returnOldPosition();
			return;
		}
		int xPos = payload.data.x;
		int yPos = payload.data.y;
		boolean movePerformed = false;
		NeGuiRow targetRow = null;
		try {
			Optional<NeGuiRow> target = state.getRows().values().stream()
					.filter(i -> i.guiElement.isPointInside(xPos, yPos)).reduce((a, b) -> {
						throw new IllegalStateException("Multiple rows when searching for one");
					});
			if (target.isPresent()) {
				targetRow = target.get();
				movePerformed = state.getMainPlayer().cardMovedOnRow(src.getGUID(), target.get().guid, this);
			}
		} catch (IllegalStateException e) {
			return;
		}
		if (movePerformed) {
			if (state.getCurrentState() == EGameState.JUST_STARTED) {
				setState(EGameState.PLAYING_CARDS);
			}
			sendRequest(createCardPlayedRequest(targetRow.guid, src.getGUID()));
		}
		if (!movePerformed) {
			src.getController().returnOldPosition();
		}
	}

	protected void handleEntityClicked(NeActionEntityClicked payload) {
		if (payload.signature == EControllerSignatures.END_BUTTON) {
			handleEndButtonClicked(payload);
		}
		if (payload.signature == EControllerSignatures.CARD) {
			handleCardClicked(payload);
		}
		if (payload.signature == EControllerSignatures.SUBMIT_PICKS_BUTTON) {
			handleSubmitPicksClicked(payload);
		}
		if (payload.signature == EControllerSignatures.PICK_ROWS_BUTTON) {
			handlePickRowsClicked(payload);
		}
		if (payload.signature == EControllerSignatures.SWAP_CARDS_BUTTON) {
			handleSwapCardsClicked(payload);
		}
	}

	protected void handleEntityHovered(NeActionEntityHovered payload) {
		if (payload.signature == EControllerSignatures.CARD) {
			handleCardHovered(payload);
		}
	}

	protected void handleEntityBlured(NeActionEntityBlured payload) {
		if (payload.signature == EControllerSignatures.CARD) {
			handleCardBlured(payload);
		}
	}

	protected void handleCardHovered(NeActionEntityHovered payload) {
		addToHoverPreview(payload);
	}

	protected void handleCardBlured(NeActionEntityBlured payload) {
		NeEntity src = NeGameUtil.getEntityByPath(payload.srcGUIDPath, state.getGraphics().screen);
		if(src == null) {
			System.out.println(payload.srcGUIDPath);
			return;
		}
		state.getPreview().remove(src.getGUID());
	}

	protected void handleEndButtonClicked(NeActionEntityClicked payload) {
		endPlayerTurn(state.getMainPlayer().getGuid());
	}

	protected void handleSubmitPicksClicked(NeActionEntityClicked payload) {
		if (state.getCurrentState() == EGameState.PICKING_ROWS || state.getCurrentState() == EGameState.PICKING) {
			submitPicks();
			setState(EGameState.PLAYING_CARDS);
		}
		if (state.getCurrentState() == EGameState.SWAPING_CARDS) {
			submitPicks();
			endPlayerTurn(state.getMainPlayer().getGuid());
		}
	}

	protected void handleSwapCardsClicked(NeActionEntityClicked payload) {
		setupCardsSwap();
	}

	protected void handlePickRowsClicked(NeActionEntityClicked payload) {
		setupRowsPickMode();
	}

	protected void handleCardClicked(NeActionEntityClicked payload) {
		if (isInPickMode()) {
			handleCardClickedInPickMode(payload);
		}
	}

	protected void handleCardClickedInPickMode(NeActionEntityClicked payload) {
		if (getPickMode() == ENePickModes.ROW) {
			handleCardClickedPickModeRow(payload);
		}
		if (getPickMode() == ENePickModes.HAND) {
			handleCardClickedPickModeHand(payload);
		}
	}

	protected NePlayer getPlayerByGuid(String playerGuid) {
		if (state.getMainPlayer().getGuid().equals(playerGuid)) {
			return state.getMainPlayer();
		}
		if (state.getEnemy().getGuid().equals(playerGuid)) {
			return state.getEnemy();
		}
		return null;
	}

	protected void handleCardClickedPickModeRow(NeActionEntityClicked payload) {
		NeEntity src = NeGameUtil.getEntityByPath(payload.srcGUIDPath, state.getGraphics().screen);
		String rowHoldingItemGUID = state.getMainPlayer().rowsHolder.hasItem(src.getGUID());
		if (rowHoldingItemGUID.equals("")) {
			return;
		}
		NeCard temp = state.getMainPlayer().rowsHolder.rows.get(rowHoldingItemGUID).cards.get(src.getGUID());
		if (temp == null) {
			return;
		}
		addToPickBuffer(temp);
	}

	protected void handleCardClickedPickModeHand(NeActionEntityClicked payload) {
		NeEntity src = NeGameUtil.getEntityByPath(payload.srcGUIDPath, state.getGraphics().screen);
		NeCard temp = state.getMainPlayer().hand.getCards().get(src.getGUID());
		if (temp == null) {
			return;
		}
		addToPickBuffer(temp);
	}

	protected void addToHoverPreview(NeActionEntityHovered payload) {
		NeEntity src = NeGameUtil.getEntityByPath(payload.srcGUIDPath, state.getGraphics().screen);
		if (src == null) {
			System.out.println("Ne: Couldn't find entity associated with action ");
			System.out.println("Payload: ");
			System.out.println(payload.srcGUIDPath + " " + payload.actionType + " " + payload.signature + " ");
			return;
		}
		if (src.getController() == null) {
			throw new Error("Ne: Action should be send by Controller, but source entity have no controller");
		}
		NeImage srcAsImage;
		if (src instanceof NeImage) {
			srcAsImage = (NeImage) (src);
		} else {
			return;
		}
		state.getPreview().add(src.getGUID(), srcAsImage.getSrcKey());
	}

	protected void pickModeOn() {
		if (pickMode == ENePickModes.NONE) {
			throw new Error("Pick mode should be assigned before turning on pick mode");
		}
		setState(EGameState.PICKING);
	}

	protected void pickModeOff() {
		pickMode = ENePickModes.NONE;
		state.getPickRowsButton().getGuiElement().setVisible(false);
		state.getEndButton().getGuiElement().setVisible(true);
		state.getPickSignaler().getGuiElement().setVisible(false);
	}

	@Override
	public void endPlayerTurn(String playerGuid) {
		NePlayer player = getPlayerByGuid(playerGuid);
		fillPlayerHand(player);
		if (state.getMainPlayer().getGuid().equals(playerGuid)) {
			setState(EGameState.JUST_STARTED);
		} else {
			setState(EGameState.JUST_STARTED);
		}
	}

	protected void fillPlayerHand(NePlayer player) {
		int cardsToDraw = player.getMaxCardsInHand() - player.getCardsInHand();
		for (int i = 0; i < cardsToDraw; i++) {
			drawCard(player.getGuid());
		}
	}

	@Override
	public boolean isInPickMode() {
		return pickMode != ENePickModes.NONE;
	}

	@Override
	public void startPlayerTurn(String playerGuid) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startGame() {
		if (gameAlreadyStarted) {
			return;
		}
		if (webConnectionString == null) {
			return;
		}
		if (!stateIsLoaded) {
			return;
		}
		gameAlreadyStarted = true;
		fillPlayerHand(state.getMainPlayer());
		state.setCurrentState(EGameState.JUST_STARTED);
	}

	protected void setState(EGameState gameState) {
		state.setCurrentState(gameState);
		if (gameState == EGameState.JUST_STARTED) {
			state.getEndButton().getGuiElement().setVisible(true);
			state.getPickRowsButton().getGuiElement().setVisible(true);
			state.getSubmitPicksButton().getGuiElement().setVisible(false);
			state.getSwapCardsButton().getGuiElement().setVisible(true);
		}
		if (gameState == EGameState.PICKING || gameState == EGameState.PICKING_ROWS) {
			state.getEndButton().getGuiElement().setVisible(false);
			state.getPickRowsButton().getGuiElement().setVisible(false);
			state.getSubmitPicksButton().getGuiElement().setVisible(true);
			state.getSwapCardsButton().getGuiElement().setVisible(false);
		}
		if (gameState == EGameState.PLAYING_CARDS) {
			state.getEndButton().getGuiElement().setVisible(true);
			state.getPickRowsButton().getGuiElement().setVisible(false);
			state.getSubmitPicksButton().getGuiElement().setVisible(false);
			state.getSwapCardsButton().getGuiElement().setVisible(false);
		}
		if (gameState == EGameState.SWAPING_CARDS) {
			state.getEndButton().getGuiElement().setVisible(false);
			state.getPickRowsButton().getGuiElement().setVisible(false);
			state.getSubmitPicksButton().getGuiElement().setVisible(true);
			state.getSwapCardsButton().getGuiElement().setVisible(false);
		}
		if (gameState == EGameState.ENEMY_PLAYING) {
			state.getEndButton().getGuiElement().setVisible(false);
			state.getPickRowsButton().getGuiElement().setVisible(false);
			state.getSubmitPicksButton().getGuiElement().setVisible(false);
			state.getSwapCardsButton().getGuiElement().setVisible(false);
		}
	}

	protected void endPickingRows() {
		getPickBuffer().forEach(i -> {
			NeWebMessageCardPickedRequest req = new NeWebMessageCardPickedRequest();
			req.cardGuid = i.getGuid();
			req.playerGuid = state.getMainPlayer().getGuid();
			sendRequest(gson.toJson(req));
			state.getMainPlayer().pickCard(i);
			System.out.println(Integer.toString(state.getMainPlayer().points));
			state.getPlayerPointsLabel().setValue(Integer.toString(state.getMainPlayer().points));
		});
	}

	protected void endSwapingCards() {
		NeWebMessageCardsSwappedRequest req = new NeWebMessageCardsSwappedRequest();
		ArrayList<String> guids = new ArrayList<>();
		getPickBuffer().forEach(i -> {
			String guid = i.getGuid();
			guids.add(guid);
			moveCardFromHandToTemp(guid, state.getMainPlayer().getGuid());
			moveCardFromTempToBottomOfDeck(guid);
			drawCard(state.getMainPlayer().getGuid());
		});
		req.playerGuid = state.getMainPlayer().getGuid();
		req.guids = guids.toArray(new String[0]);
		sendRequest(gson.toJson(req));
	}

	@Override
	public void afterPick() {
		if (state.getCurrentState() == EGameState.PICKING_ROWS) {
			endPickingRows();
		}
		if (state.getCurrentState() == EGameState.SWAPING_CARDS) {
			endSwapingCards();
		}
	}

	@Override
	public void moveCardFromTempToTopOfDeck(String guid) {
		INeCard temp = state.getTemporaryZone().get(guid);
		((NeCard) (temp)).getGuiElement().destroy();
		state.getDeck().cards.add(temp.getCode());
	}

	@Override
	public void moveCardFromTempToBottomOfDeck(String guid) {
		INeCard temp = state.getTemporaryZone().get(guid);
		((NeCard) (temp)).getGuiElement().destroy();
		state.getDeck().cards.add(state.getDeck().cards.size(), temp.getCode());
	}

	@Override
	public void drawCard(String playerGuid) {
		sendRequest(createBaseRequest(NeWebMessageTypeRequest.DRAW));
	}

	@Override
	public void afterDrawCard(String cardGuid, String cardType) {
		createCardInTemp(cardType, cardGuid);
		moveCardFromTempToHand(cardGuid, state.getMainPlayer().getGuid());
	}

	@Override
	public void getDeckSize(String playerGuid) {
		sendRequest(createBaseRequest(NeWebMessageTypeRequest.DECK_SIZE));
	}

	@Override
	public void afterGetDeckSize(int size) {
		try {
			state.getDeck().fillUnknown(size);
		} catch (Exception e) {

		}
	}

	private void sendRequest(String message) {
		web.c.write(message);
	}

	private String createBaseRequest(NeWebMessageTypeRequest type) {
		NeWebMessageRequest msg = new NeWebMessageRequest(type);
		msg.playerGuid = webConnectionString;
		return gson.toJson(msg);
	}

	private String createCardPlayedRequest(String rowGuid, String cardGuid) {
		NeWebMessageCardPlayedRequest msg = new NeWebMessageCardPlayedRequest(cardGuid, rowGuid);
		msg.playerGuid = webConnectionString;
		return gson.toJson(msg);
	}

	@Override
	public void afterConnected(String playerGuid) {
		this.webConnectionString = playerGuid;
		state.getMainPlayer().setGuid(playerGuid);
		startGame();
	}

	@Override
	public void afterOpponentHandChange(String cardGuid, boolean isRemoved) {
		if (isRemoved) {
			state.getEnemy().hand.removeCard(cardGuid);
			return;
		}
		createCardInTemp("NE_33_REWERS", cardGuid);
		moveCardFromTempToHand(cardGuid, state.getEnemy().getGuid());
	}

	@Override
	public void informStateIsLoaded() {
		stateIsLoaded = true;
		startGame();
	}

	@Override
	public void afterDataConnected(WebPlayer player) {
		NePlayer playrFound = this.getPlayerByGuid(player.guid);
		NePlayer playr;
		if (playrFound == null) {
			playr = state.getEnemy();
			state.getEnemy().setGuid(player.guid);
		} else {
			playr = playrFound;
		}
		if(player.guid.equals(webConnectionString)) {
			System.out.println(player.rows.size());
		}
		player.rows.forEach((String guid, NeRow row) -> {
			playr.rowsHolder.addRow(row.guid);
			row.cards.forEach((String guidC, String card) -> {
				INeCard temp = cf.get(card, guidC);
				playr.putCardOnRow(temp, row.guid);
			});
			player.addRow(row.guid);
		});
		player.hand.forEach((String key, String code) -> playr.hand.addCard(cf.get(code, key)));
	}

	@Override
	public void afterOpponentPlayCard(String cardGuid, String rowGuid, String playerGuid, String cardCode) {
		getPlayerByGuid(playerGuid).hand.removeCard(cardGuid);
		state.getRows().get(rowGuid).addCard(this.cf.get(cardCode, cardGuid));
	}

	@Override
	public void afterOpponentPickCard(String cardGuid, String playerGuid) {
		getPlayerByGuid(playerGuid).pickCard(cardGuid);
	}

}
