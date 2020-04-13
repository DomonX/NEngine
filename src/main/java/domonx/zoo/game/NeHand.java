package domonx.zoo.game;

import java.util.HashMap;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeHorizontalLayout;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeHand {
	
	public String GUID = "";
	public NeRowsHolder owner = null;	
	public NeContainer GUIElement = null;
	public HashMap<String, NeCard> cards = new HashMap<String, NeCard>();
	public String cardType = "";
	
	public NeHand(String GUID, NeGraphicsModule graphics, INeActionListener listener, int x, int y) {
		prepareGUI(GUID, graphics, listener, x, y);
	}
	
	public void addCard(NeCard card) {
		GUIElement.add(card.GUIElement);
		card.GUIElement.fit();
		cards.put(card.GUID, card);
	}
	
	public void removeCard(String GUID) {
		GUIElement.remove(GUID);
		cards.remove(GUID);
	}
	
	private void prepareGUI(String GUID, NeGraphicsModule graphics, INeActionListener listener, int x, int y) {
		GUIElement = new NeContainer(GUID);
		@SuppressWarnings("unused")
		NeHorizontalLayout l = new NeHorizontalLayout(GUIElement);
		NeContainerController ctrl = new NeContainerController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		GUIElement.connectStorage(graphics.getStorage());
		GUIElement.setWidth(1300);
		GUIElement.setHeight(300);
		GUIElement.setScale(1);
		GUIElement.move(x, y);
	}
}
