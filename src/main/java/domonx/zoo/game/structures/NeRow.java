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
	public Map<String, String> cards = new HashMap<>();
	
	public NeRow(String guid) {
		this.guid = guid;
	}
	
	public void addCard(String guid, String card) {
		cards.put(guid, card);
	}
	
	public void removeCard(String guid) {
		cards.remove(guid);
	}
	
	public boolean hasItem(String key) {
		return cards.containsKey(key);
	}
	
}
