package domonx.zoo.game;

import java.util.HashMap;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeHorizontalLayout;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeHand extends NeStructureElement{
		
	public NeContainer GUIElement = null;
	public HashMap<String, NeCard> cards = new HashMap<String, NeCard>();
	public String cardType = "";
	public String mainPlayerGUID = "";
	public String enemyGUID = "";
	
	private int x;
	private int y;
	
	public NeHand(String GUID, NeGraphicsModule graphics, INeActionListener listener, int x, int y) {
		super(GUID, ENeStructureType.none, graphics, listener);
		this.x = x;
		this.y = y;
		prepareGUI();
	}
	
	public void addCard(INeCard card) {
		NeCard cardToAdd = (NeCard)(card);
		if(cardToAdd == null) {
			return;
		}
		GUIElement.add(cardToAdd.GUIElement);
		cardToAdd.GUIElement.fit();
		cardToAdd.playerGUID = mainPlayerGUID;
		cardToAdd.enemyGUID = enemyGUID;
		cards.put(cardToAdd.GUID, cardToAdd);
	}
	
	public void removeCard(String GUID) {
		GUIElement.remove(GUID);
		cards.remove(GUID);
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
		GUIElement.setHeight(300);
		GUIElement.setScale(1);
		GUIElement.move(x, y);
	}
}
