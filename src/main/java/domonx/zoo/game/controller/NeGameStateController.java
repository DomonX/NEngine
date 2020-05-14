package domonx.zoo.game.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.cards.CardFactory;
import domonx.zoo.game.enums.EGameState;
import domonx.zoo.game.enums.ENePickModes;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.state.NeGameState;
import domonx.zoo.game.structures.NeCard;
import domonx.zoo.game.structures.NePlayer;
import domonx.zoo.game.structures.NeRow;
import domonx.zoo.game.util.NeGameUtil;

public class NeGameStateController implements NeAbstractGameStateController, NeAbstractActionListener, INePickListener {

	protected boolean isPicking = false;

	protected ENePickModes pickMode = ENePickModes.NONE;

	protected int cardsToPick = 0;

	private String[] bannedGUIDs;

	private NeGameState state;

	private INePickListener listener;

	public NeGameStateController() {
		super();
	}

	@Override
	public ENePickModes getPickMode() {
		return pickMode;
	}

	public void connectState(NeGameState state) {
		this.state = state;
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
		state.getMainPlayer().rowsHolder.rows.get(rowGuid).removeCard(guid);
		state.getTemporaryZone().put(guid, temp);
	}

	@Override
	public void moveCardFromHandToTemp(String guid, String playerGuid) {
		INeCard temp = state.getMainPlayer().hand.getCards().get(guid);
		state.getMainPlayer().hand.removeCard(guid);
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
		state.getMainPlayer().putCardOnRow(temp, rowGuid);
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
		CardFactory cf = new CardFactory(state.getGraphics(), this, this);
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
		if (state.getCurrentState() == EGameState.JUST_STARTED) {
			setState(EGameState.PLAYING_CARDS);
		}
		NeEntity src = NeGameUtil.getEntityByPath(NeGameUtil.getArrayFromPath(payload.srcGUIDPath),
				state.getGraphics().screen);
		if (state.getCurrentState() != EGameState.PLAYING_CARDS) {
			src.getController().returnOldPosition();
			return;
		}
		int xPos = payload.data.x;
		int yPos = payload.data.y;
		boolean movePerformed = false;
		try {
			Optional<NeRow> target = state.getRows().values().stream()
					.filter(i -> i.guiElement.isPointInside(xPos, yPos)).reduce((a, b) -> {
						throw new IllegalStateException("Multiple rows when searching for one");
					});
			if (target.isPresent()) {
				movePerformed = state.getMainPlayer().cardMovedOnRow(src.getGUID(), target.get().guid, this);
			}
		} catch (IllegalStateException e) {
			return;
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
		if(getPickMode() == ENePickModes.HAND) {
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
		fillPlayerHand(state.getMainPlayer());
		fillPlayerHand(state.getEnemy());
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
		getPickBuffer().forEach(i -> state.getMainPlayer().pickCard(i));
	}
	
	protected void endSwapingCards() {
		getPickBuffer().forEach(i -> {
			String guid = i.getGuid();
			moveCardFromHandToTemp(guid, state.getMainPlayer().getGuid());
			moveCardFromTempToBottomOfDeck(guid);
			drawCard(state.getMainPlayer().getGuid());
		});
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
		((NeCard)(temp)).getGuiElement().destroy();
		state.getDeck().cards.add(temp.getCode());
	}

	@Override
	public void moveCardFromTempToBottomOfDeck(String guid) {
		INeCard temp = state.getTemporaryZone().get(guid);
		((NeCard)(temp)).getGuiElement().destroy();
		state.getDeck().cards.add(state.getDeck().cards.size(), temp.getCode());
	}

	@Override
	public void drawCard(String playerGuid) {
		String newCardGuid = GUIDGenerator.get();
		createCardInTemp(state.getDeck().drawCard(), newCardGuid);
		moveCardFromTempToHand(newCardGuid, playerGuid);		
	}

}
