package domonx.zoo.game.structures;

import java.util.Map;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.configuration.NeGameConfiguration;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.structures.gui.NeGuiRow;
import domonx.zoo.window.NeGraphicsModule;

public class NePlayer extends NeStructureElement<NeContainer> {
	
	public NeHand hand;
	public NeRowsHolder rowsHolder;	
	public int points;	
	
	private String opponentGuid;
	
	private int maxCardsInHand;
	

	public NePlayer(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid, ENeStructureType.NONE, graphics, listener);
		prepareGUI();
		prepareHolderAndHand();
		guiElement.add(rowsHolder.guiElement);
		guiElement.add(hand.guiElement);
		maxCardsInHand = NeGameConfiguration.getBaseCardsInHand();
	}
	
	public void prepareGUI() {
		guiElement = new NeContainer(GUIDGenerator.get());
		NeContainerController ctrl = new NeContainerController(guiElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		setupPositions();
		guiElement.connectStorage(graphics.getStorage());
	}
	
	public int getMaxCardsInHand() {
		return maxCardsInHand;
	}
	
	public boolean cardMovedOnRow(String cardGUID, String rowGUID, NeAbstractGameStateController eventStack) {
		NeGuiRow tempRow = rowsHolder.rows.get(rowGUID);
		NeCard tempCard = hand.getCards().get(cardGUID);
		if(tempRow == null || tempCard == null) {
			return false;
		}
		if(!tempRow.rowCanHoldCard(tempCard)) {
			return false;
		}
		hand.removeCard(tempCard.guid);
		tempRow.addCard(tempCard);
		tempCard.onSelfPlay();
		return true;
	}
	
	public void putCardOnRow(INeCard card, String rowGUID) {
		NeGuiRow tempRow = rowsHolder.rows.get(rowGUID);
		NeCard tempCard = (NeCard)(card);
		if(tempRow == null || tempCard == null) {
			return;
		}
		tempRow.addCard(tempCard);
	}
	
	public void initialize(Map<String, NeGuiRow> registry) {
		rowsHolder.connectRowsRegistry(registry);
		rowsHolder.initialize();
	}

	public String getOpponentGuid() {
		return opponentGuid;
	}

	public void setOpponentGuid(String opponentGuid) {
		this.opponentGuid = opponentGuid;
		hand.setEnemyGUID(opponentGuid);
	}
	
	public int getCardsInHand() {
		return hand.getCards().size();
	}
	
	public void pickCard(INeCard card) {
		points += card.getStrength();
		rowsHolder.removeCard(card);
	}
	
	public void pickCard(String cardGuid) {
		INeCard pickedCard = rowsHolder.removeCard(cardGuid);
		if(pickedCard != null) {
			points += pickedCard.getStrength();
		}
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	protected void prepareHolderAndHand() {
		rowsHolder = new NeRowsHolder(GUIDGenerator.get(), graphics, listener, 0, 0);
		hand = new NeHand(GUIDGenerator.get(), graphics, listener, 0, (int)(rowsHolder.guiElement.getHeight()));
		hand.setMainPlayerGUID(guid);
	}
	
	protected void setupPositions() {
		guiElement.setWidth(1300);
		guiElement.setHeight(540);
		guiElement.moveRelatively(310, 540);
	}
}
