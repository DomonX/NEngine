package domonx.zoo.game.structures.gui;

import java.util.HashMap;
import java.util.Map;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeVerticalLayout;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.structures.NeCard;
import domonx.zoo.game.structures.NeRowsHolder;
import domonx.zoo.window.NeGraphicsModule;

public class NeGuiRow {
	
	public NeContainer guiElement = null;
	public NeRowsHolder owner = null;
	public String guid = "";
	public Map<String, NeCard> cards = new HashMap<>();
	public String cardCode = "";

	public NeGuiRow(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener, int x, int y) {
		this.guid = guid;
		prepareGUI(graphics, listener, x, y);
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
		guiElement.load("assets\\row.png");
		guiElement.move(x, y);
	}
	
	
	public void addCard(NeCard card) {
		cards.put(card.getGuid(), card);
		card.setRowGUID(guid);
		cardCode = card.getSrc();
		guiElement.add(card.getGuiElement());
		card.getGuiElement().fit();
		guiElement.layout();
	}
	public INeCard removeCard(String guid) {
		guiElement.destroy(guid);
		INeCard removedCard = cards.remove(guid);
		if(cards.size() == 0) {
			cardCode = "";
		}
		return removedCard;
	}	
	
	public boolean rowCanHoldCard(NeCard card) {
		return cardCode.equals("") || card.getSrc().equals(cardCode);
	}
	
	public boolean hasItem(String key) {
		return cards.containsKey(key);
	}
	
	

}
