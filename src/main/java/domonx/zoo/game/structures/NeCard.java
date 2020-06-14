package domonx.zoo.game.structures;

import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.controller.NeDraggableController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.cards.NeCardCodes;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public abstract class NeCard extends NeStructureElement<NeContainer> implements INeCard{
	
	protected int strenght;
	protected int additionalStrenght;
	protected String src;
	private String rowGUID;
	protected String playerGUID;
	protected String enemyGUID;
	protected NeAbstractGameStateController controller;
	protected String code;
	private NeImage selected;
	
	public NeCard(String guid, String src, NeGraphicsModule graphics, NeAbstractActionListener listener, NeAbstractGameStateController state) {
		super(guid, ENeStructureType.NONE, graphics, listener);
		this.src = src;
		prepareGUI();
		this.controller = state;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getGuid() {
		return guid;
	}

	@Override
	public String getRowGUID() {
		return rowGUID;
	}

	@Override
	public String getSrc() {
		return src;
	}

	@Override
	public int getStrength() {
		return strenght;
	}

	@Override
	public int getAdditionalStrength() {
		return additionalStrenght;
	}

	@Override
	public void setAdditionalStrength(int additionalStrength) {
		this.additionalStrenght = additionalStrength;
	}

	@Override
	public void afterPick() {
		
	}

	@Override
	public void setSelected(boolean selected) {
		if(selected) {
			guiElement.add(this.selected);
			this.selected.fit();
		} else {
			guiElement.remove(this.selected.getGUID());
		}		
	}

	@Override
	public String getOwnerGUID() {
		return playerGUID;
	}

	@Override
	public String getEnemyGUID() {
		return enemyGUID;
	}

	@Override
	public void onSelfDraw() {
		
	}

	@Override
	public void onOtherDraw() {
		
	}

	@Override
	public void onDeath() {
		
	}

	@Override
	public void onActivate() {
		
	}

	@Override
	public void onSelfPlay() {
		
	}

	@Override
	public void onOtherPlay() {
		
	}

	@Override
	public void onSelfDiscard() {
		
	}

	@Override
	public void onOtherDiscard() {
		
	}

	@Override
	public void canBePlayed() {
		
	}
	
	@Override
	protected void prepareGUI() {
		selected = new NeImage(guid + "_Selected");
		selected.connectStorage(graphics.getStorage());
		selected.setWidth(200);
		selected.setHeight(300);
		selected.setScale(1);
		selected.load("assets\\Selected.png");
		guiElement = new NeContainer(guid);
		NeDraggableController ctrl = new NeDraggableController(guiElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		ctrl.setThreshold(45);
		ctrl.setExtraSignature(EControllerSignatures.CARD);
		guiElement.connectStorage(graphics.getStorage());
		guiElement.setWidth(200);
		guiElement.setHeight(300);
		guiElement.setScale(1);
		guiElement.load(src);
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}
}
