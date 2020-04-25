package domonx.zoo.game;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class NePlayer extends NeStructureElement implements INePlayer{
	public NeHand hand;
	public NeRowsHolder rowsHolder;
	public NeContainer GUIElement;
	
	public int points;	
	
	public NePlayer(String GUID, NeGraphicsModule graphics, INeActionListener listener) {
		super(GUID, ENeStructureType.none, graphics, listener);
		prepareGUI();
		prepareHolderAndHand();
		GUIElement.add(rowsHolder.GUIElement);
		GUIElement.add(hand.GUIElement);
	}
	
	public void prepareGUI() {
		GUIElement = new NeContainer(GUIDGenerator.get());
		NeContainerController ctrl = new NeContainerController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		setupPositions();
		GUIElement.connectStorage(graphics.getStorage());
	}
	
	public boolean cardMovedOnRow(String cardGUID, String rowGUID, INeGameStateController eventStack) {
		NeRow tempRow = rowsHolder.rows.get(rowGUID);
		NeCard tempCard = hand.cards.get(cardGUID);
		if(tempRow == null || tempCard == null) {
			return false;
		}
		if(!tempRow.rowCanHoldCard(tempCard)) {
			return false;
		}
		hand.removeCard(tempCard.GUID);
		tempRow.addCard(tempCard);
		tempCard.onSelfPlay();
		return true;
	}
	
	public void putCardOnRow(INeCard card, String rowGUID) {
		NeRow tempRow = rowsHolder.rows.get(rowGUID);
		NeCard tempCard = (NeCard)(card);
		if(tempRow == null || tempCard == null) {
			return;
		}
		tempRow.addCard(tempCard);
	}
	
	protected void prepareHolderAndHand() {
		rowsHolder = new NeRowsHolder(GUIDGenerator.get(), graphics, listener, 0, 0);
		hand = new NeHand(GUIDGenerator.get(), graphics, listener, 0, (int)(rowsHolder.GUIElement.getHeight()));
		hand.mainPlayerGUID = GUID;
	}
	
	protected void setupPositions() {
		GUIElement.setWidth(1300);
		GUIElement.setHeight(540);
		GUIElement.moveRelatively(310, 540);
	}
}
