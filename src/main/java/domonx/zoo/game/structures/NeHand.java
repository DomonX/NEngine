package domonx.zoo.game.structures;

import java.util.HashMap;
import java.util.Map;

import domonx.zoo.core.controller.NeContainerController;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.entity.container.NeHorizontalLayout;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.window.NeGraphicsModule;

public class NeHand extends NeStructureElement<NeContainer>{
	private Map<String, NeCard> cards = new HashMap<>();
	private String cardType = "";
	private String mainPlayerGUID = "";
	private String enemyGUID = "";	

	private int x;
	private int y;
	
	public String getMainPlayerGUID() {
		return mainPlayerGUID;
	}
	
	public void setMainPlayerGUID(String mainPlayerGUID) {
		this.mainPlayerGUID = mainPlayerGUID;
	}
	
	public String getEnemyGUID() {
		return enemyGUID;
	}
	
	public void setEnemyGUID(String enemyGUID) {
		this.enemyGUID = enemyGUID;
	}
	
	public Map<String, NeCard> getCards() {
		return cards;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public NeHand(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener, int x, int y) {
		super(guid, ENeStructureType.NONE, graphics, listener);
		this.x = x;
		this.y = y;
		prepareGUI();
	}
	
	public void addCard(INeCard card) {
		NeCard cardToAdd = (NeCard)(card);
		if(cardToAdd == null) {
			return;
		}
		guiElement.add(cardToAdd.guiElement);
		cardToAdd.guiElement.fit();
		cardToAdd.playerGUID = mainPlayerGUID;
		cardToAdd.enemyGUID = enemyGUID;
		cards.put(cardToAdd.guid, cardToAdd);
	}
	
	public void removeCard(String guid) {
		guiElement.remove(guid);
		cards.remove(guid);
	}
	
	@Override
	protected void prepareGUI() {
		guiElement = new NeContainer(guid);
		NeContainerController ctrl = new NeContainerController(guiElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		guiElement.connectStorage(graphics.getStorage());
		guiElement.connectLayout(new NeHorizontalLayout(guiElement));
		guiElement.setWidth(1300);
		guiElement.setHeight(300);
		guiElement.setScale(1);
		guiElement.move(x, y);
	}
}
