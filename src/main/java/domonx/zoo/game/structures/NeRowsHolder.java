package domonx.zoo.game.structures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeHorizontalLayout;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.configuration.NeGameConfiguration;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.window.NeGraphicsModule;

public class NeRowsHolder extends NeStructureElement<NeContainer> {

	public Map<String, NeRow> rows;
	
	private Map<String, NeRow> registry;

	private int x;
	private int y;

	private String searchBuffer;

	NeRowsHolder(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener, int x, int y) {
		super(guid, ENeStructureType.NONE, graphics, listener);
		rows = new HashMap<>();
		this.x = x;
		this.y = y;
		prepareGUI();
	}

	public NeRow addRow() {
		NeRow temp = new NeRow(GUIDGenerator.get(), graphics, listener, 0, 0);
		rows.put(temp.guid, temp);
		guiElement.add(temp.guiElement);
		temp.guiElement.fit();
		registry.put(temp.guid, temp);
		return temp;
	}
	
	public void removeRow(String guid) {
		rows.remove(guid);
		registry.remove(guid);
	}
	
	public void connectRowsRegistry(Map<String, NeRow> registry) {
		this.registry = registry;
	}
	
	public void initialize() {
		for(int i = 0; i < NeGameConfiguration.getBaseRowsPerPlayer(); i++) {
			addRow();
		}
	}

	public String hasItem(String key) {
		searchBuffer = "";
		rows.forEach((String rowKey, NeRow item) -> searchBuffer = item.hasItem(key) ? rowKey : searchBuffer);
		return searchBuffer;
	}
	
	public void removeCard(INeCard card) {	
		NeRow owner = rows.get(card.getRowGUID());
		if(owner == null) {
			return;
		}
		owner.removeCard(card.getGuid());
	}
	
	public void removeCard(String cardGuid) {
		Optional<NeRow> owner = rows.values().stream().filter(i -> i.hasItem(cardGuid)).findFirst();
		if(!owner.isPresent()) {
			return;
		}
		owner.get().removeCard(cardGuid);
	}

	@Override
	protected void prepareGUI() {
		guiElement = new NeContainer(guid);
		NeContainerController ctrl = new NeContainerController(guiElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		guiElement.connectStorage(graphics.getStorage());
		guiElement.connectLayout(new NeHorizontalLayout(guiElement));
		guiElement.setWidth(1300);
		guiElement.setHeight(400);
		guiElement.moveRelatively(0, 0);
		guiElement.setScale(1);
		guiElement.move(x, y);
	}

}