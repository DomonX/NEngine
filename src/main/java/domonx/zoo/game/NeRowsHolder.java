package domonx.zoo.game;

import java.util.ArrayList;
import java.util.HashMap;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeHorizontalLayout;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.window.NeGraphicsModule;

public class NeRowsHolder extends NeStructureElement{
	
	public String GUID = "";
	public NeContainer GUIElement = null;
	public HashMap<String, NeRow> rows = new HashMap<String, NeRow>();
	
	private int x;
	private int y;
	
	private String searchBuffer = "";

	NeRowsHolder(String GUID, NeGraphicsModule graphics, INeActionListener listener, int x, int y) {
		super(GUID, ENeStructureType.none, graphics, listener);
		this.x = x;
		this.y = y;
		prepareGUI();
	}
	
	public NeRow addRow(ArrayList<NeRow> registry) {
		NeRow temp = new NeRow(GUIDGenerator.get(), graphics, listener, 0, 0);
		rows.put(temp.GUID, temp);
		GUIElement.add(temp.GUIElement);
		temp.GUIElement.fit();
		registry.add(temp);
		return temp;
	}
	
	public String hasItem(String key) {
		searchBuffer = "";
		rows.forEach((String rowKey, NeRow item) -> {
			searchBuffer = item.hasItem(key) ? rowKey : searchBuffer;
		});
		return searchBuffer;
	}

	@Override
	protected void prepareGUI() {
		GUIElement = new NeContainer(GUID);
		@SuppressWarnings("unused")
		NeHorizontalLayout l = new NeHorizontalLayout(GUIElement);
		NeContainerController ctrl = new NeContainerController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		GUIElement.connectStorage(graphics.getStorage());
		GUIElement.setWidth(1300);
		GUIElement.setHeight(400);
		GUIElement.moveRelatively(0, 0);
		GUIElement.setScale(1);
		GUIElement.move(x, y);
		
	}

}
