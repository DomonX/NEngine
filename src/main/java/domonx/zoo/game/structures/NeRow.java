package domonx.zoo.game.structures;

import java.util.HashMap;
import java.util.Map;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeVerticalLayout;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeRow {
	
	public String guid = "";
	public NeRowsHolder owner = null;	
	public NeContainer guiElement = null;
	public Map<String, NeCard> cards = new HashMap<>();
	public String cardCode = "";
	
	public NeRow(String GUID, NeGraphicsModule graphics, NeAbstractActionListener listener, int x, int y) {
		this.guid = GUID;
		prepareGUI(graphics, listener, x, y);
	}
	
	public void addCard(NeCard card) {
		guiElement.add(card.guiElement);
		cards.put(card.guid, card);
		card.guiElement.fit();
		card.rowGUID = guid;
		guiElement.layout();
		cardCode = card.src;
	}
	
	public void removeCard(String guid) {
		guiElement.destroy(guid);
		cards.remove(guid);
		if(cards.size() == 0) {
			cardCode = "";
		}
	}
	
	public boolean rowCanHoldCard(NeCard card) {
		return cardCode.equals("") || card.src.equals(cardCode);
	}
	
	public boolean hasItem(String key) {
		return cards.containsKey(key);
	}
	
	private void prepareGUI(NeGraphicsModule graphics, NeAbstractActionListener listener, int x, int y) {
		guiElement = new NeContainer(guid);
		NeContainerController ctrl = new NeContainerController(guiElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		guiElement.connectStorage(graphics.getStorage());
		guiElement.connectLayout(new NeVerticalLayout(guiElement));
		guiElement.setWidth(150);
		guiElement.setHeight(400);
		guiElement.setScale(1);
		guiElement.move(x, y);
	}
}
