package domonx.zoo.game;

import java.util.HashMap;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeVerticalLayout;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeRow {
	
	public String GUID = "";
	public NeRowsHolder owner = null;	
	public NeContainer GUIElement = null;
	public HashMap<String, NeCard> cards = new HashMap<String, NeCard>();
	public String cardCode = "";
	
	public NeRow(String GUID, NeGraphicsModule graphics, INeActionListener listener, int x, int y) {
		this.GUID = GUID;
		prepareGUI(graphics, listener, x, y);
	}
	
	public void addCard(NeCard card) {
		GUIElement.add(card.GUIElement);
		cards.put(card.GUID, card);
		card.GUIElement.fit();
		NeVerticalLayout l = (NeVerticalLayout)(this.GUIElement.getLayout());
		GUIElement.layout();
		cardCode = card.code;
	}
	
	public void removeCard(String GUID) {
		GUIElement.remove(GUID);
		cards.remove(GUID);
	}
	
	private void prepareGUI(NeGraphicsModule graphics, INeActionListener listener, int x, int y) {
		GUIElement = new NeContainer(GUID);
		@SuppressWarnings("unused")
		NeVerticalLayout l = new NeVerticalLayout(GUIElement);
		NeContainerController ctrl = new NeContainerController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		GUIElement.connectStorage(graphics.getStorage());
		GUIElement.setWidth(150);
		GUIElement.setHeight(400);
		GUIElement.setScale(1);
		GUIElement.move(x, y);
	}
}
