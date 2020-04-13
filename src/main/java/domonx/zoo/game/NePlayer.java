package domonx.zoo.game;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.window.NeGraphicsModule;

public class NePlayer implements INePlayer{
	public String GUID;
	public NeHand hand;
	public NeRowsHolder rowsHolder;
	public NeContainer GUIElement;
	
	public int points;	
	
	public NePlayer(NeGraphicsModule graphics, INeActionListener listener) {
		prepareGUI(graphics, listener);
		rowsHolder = new NeRowsHolder(GUIDGenerator.get(), graphics, listener, 0, 0);
		hand = new NeHand(GUIDGenerator.get(), graphics, listener, 0, (int)(rowsHolder.GUIElement.getHeight()));
		GUIElement.add(rowsHolder.GUIElement);
		GUIElement.add(hand.GUIElement);
	}
	
	public void prepareGUI(NeGraphicsModule graphics, INeActionListener listener) {
		GUIElement = new NeContainer(GUIDGenerator.get());
		NeContainerController ctrl = new NeContainerController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		GUIElement.setWidth(1300);
		GUIElement.setHeight(540);
		GUIElement.moveRelatively(310, 540);
		GUIElement.connectStorage(graphics.getStorage());
	}
	
	public boolean cardMovedOnRow(String cardGUID, String rowGUID) {
		NeRow tempRow = rowsHolder.rows.get(rowGUID);
		System.out.println(rowGUID);
		NeCard tempCard = hand.cards.get(cardGUID);
		if(tempRow == null) {
			return false;
		}
		if(tempCard == null) {
			return false;
		}
		if(tempCard.code != tempRow.cardCode && tempRow.cardCode != "") {
			return false;
		}
		hand.removeCard(tempCard.GUID);
		tempRow.addCard(tempCard);
		return true;
	}
}
