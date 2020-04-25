package domonx.zoo.state;

import java.util.ArrayList;

import domonx.zoo.actions.NeActionEntityBlured;
import domonx.zoo.actions.NeActionEntityClicked;
import domonx.zoo.actions.NeActionEntityHovered;
import domonx.zoo.actions.NeActionEntityMoved;
import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.controller.NeMouseController;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.NeButton;
import domonx.zoo.game.NeCard;
import domonx.zoo.game.NeDeck;
import domonx.zoo.game.NeEnemy;
import domonx.zoo.game.NePlayer;
import domonx.zoo.game.NePreview;
import domonx.zoo.game.NeRow;
import domonx.zoo.game.cards.CardAnthelope;
import domonx.zoo.game.cards.CardFactory;
import domonx.zoo.game.cards.NeCardCodes;
import domonx.zoo.window.NeGraphicsModule;

public class NeCustomGameState extends NeBaseGameState{
	
	public NePlayer mainPlayer;
	
	public NePlayer enemy;

	public ArrayList<NeRow> rows = new ArrayList<NeRow>();
	
	public NePreview preview;
	
	public NeButton endButton;
	
	public INeGameStateController stateController;
	
	public NeDeck deck;
	
	// Temporary
	public NeImage pickSignaler;

	public NeCustomGameState(NeGraphicsModule graphics, NeGameStateController controller) {
		super(graphics);
		this.stateController = controller;
		prepare();
	}
	
	
	private void prepare() {
		preview = new NePreview(GUIDGenerator.get(), graphics, this);
		prepareEndButton();
		pickSignaler = new NeImage(GUIDGenerator.get());
		pickSignaler.connectStorage(graphics.getStorage());
		pickSignaler.setHeight(64);
		pickSignaler.setWidth(64);
		pickSignaler.load("assets\\signaler.png");
		pickSignaler.moveRelatively(1820, 390);
		deck = new NeDeck(GUIDGenerator.get(), graphics, this, stateController);
		graphics.screen.add(pickSignaler);
		endButton.setHeight(100);
		endButton.setWidth(100);
		endButton.load("assets\\end.png");
		endButton.moveRelatively(1820, 490);
		graphics.screen.load("assets\\background.jpg");
		graphics.screen.setScale(1);
		mainPlayer = new NePlayer(GUIDGenerator.get(), graphics, this);
		enemy = new NeEnemy(GUIDGenerator.get(), graphics, this);
		mainPlayer.hand.enemyGUID = enemy.GUID;
		enemy.hand.enemyGUID = mainPlayer.GUID;
		graphics.screen.add(mainPlayer.GUIElement);
		graphics.screen.add(enemy.GUIElement);		
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		CardFactory cf = new CardFactory(graphics, this, stateController);
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Anthelope, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Bear, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Meerkat, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Horse, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.BlackPanther, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Tiger, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Scorpio, GUIDGenerator.get()));
		mainPlayer.hand.addCard(cf.get(NeCardCodes.Chimpanzee, GUIDGenerator.get()));
		enemy.hand.addCard(cf.get(NeCardCodes.Rewers, GUIDGenerator.get()));
		
	}
	
	public void prepareEndButton() {
		endButton = new NeButton(GUIDGenerator.get(), graphics, this);
		endButton.getController().setExtraSignature(EControllerSignatures.EndButton);
		graphics.screen.add(endButton);
	}
	
	@Override
	public void tick(int hertz) {
		pickSignaler.visible = stateController.isInPickMode();
	}
	
	protected void handleEntityMoved(NeActionEntityMoved payload) {
		if(payload.signature == EControllerSignatures.Card) {
			handleCardMoved(payload);
		}
	}
	
	protected void handleEntityClicked(NeActionEntityClicked payload) {
		if(payload.signature == EControllerSignatures.EndButton) {
			handleEndButtonClicked(payload);
		}
		if(payload.signature == EControllerSignatures.Card) {
			handleCardClicked(payload);
		}
	}
	
	protected void handleEntityHovered(NeActionEntityHovered payload) {
		if(payload.signature == EControllerSignatures.Card) {
			handleCardHovered(payload);
		}
	}
	
	protected void handleEntityBlured(NeActionEntityBlured payload) {
		if(payload.signature == EControllerSignatures.Card) {
			handleCardBlured(payload);
		}
	}
	
	protected void handleCardMoved(NeActionEntityMoved payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		src.getController().getExtraSignature();
		int xPos = payload.data.x;
		int yPos = payload.data.y;
		boolean movePerformed = false;
		for(NeRow row : rows) {
			if(!row.GUIElement.isPointInside(xPos, yPos)) {
				continue;
			}
			movePerformed = mainPlayer.cardMovedOnRow(src.getGUID(), row.GUID, stateController);
			break;			
		}
		if(!movePerformed) {
			src.getController().returnOldPosition();
		}		
	}
	
	protected void handleCardHovered(NeActionEntityHovered payload) {
		addToHoverPreview(payload);
	}
	
	protected void handleCardBlured(NeActionEntityBlured payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		preview.remove(src.getGUID());		
	}
	
	protected void handleEndButtonClicked(NeActionEntityClicked payload) {
		if(stateController.isInPickMode()) {
			stateController.submitPicks();
		}
	}
	
	protected void handleCardClicked(NeActionEntityClicked payload) {
		if(stateController.isInPickMode()) {
			handlePickModeClick(payload);
		}
	}
	
	protected void handlePickModeClick(NeActionEntityClicked payload) {
		if(stateController.getPickMode() == ENePickModes.Row) {
			handlePickModeRow(payload);
		}
	}
	
	protected void handlePickModeRow(NeActionEntityClicked payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		String rowHoldingItemGUID = mainPlayer.rowsHolder.hasItem(src.getGUID());
		if(rowHoldingItemGUID == "") {
			return;
		}
		NeCard temp = mainPlayer.rowsHolder.rows.get(rowHoldingItemGUID).cards.get(src.getGUID());
		if(temp == null) {
			return;
		}
		stateController.addToPickBuffer(temp);
	}
	
	protected void addToHoverPreview(NeActionEntityHovered payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		if(src == null) {
			throw new Error("Ne: Couldn't find entity associated with action");
		}
		if(src.getController() == null) {
			throw new Error("Ne: Action should be send by Controller, but source entity have no controller");
		}
		NeImage srcAsImage;
		if(src instanceof NeImage) {
			srcAsImage = (NeImage)(src);
		} else {
			return;
		}
		preview.add(src.getGUID(), srcAsImage.getSrcKey());
	}

}
