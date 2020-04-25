package domonx.zoo.game;

import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.controller.NeDraggableController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class NeCard extends NeStructureElement implements INeCard{
	
	public NeContainer GUIElement;
	public NeImage selected;
	public int strenght;
	public int additionalStrenght;
	public String code;
	public String rowGUID;
	public String playerGUID;
	public String enemyGUID;
	
	public INeGameStateController state;
	
	public NeCard(String GUID, String src, NeGraphicsModule graphics, INeActionListener listener, INeGameStateController state) {
		super(GUID, ENeStructureType.none, graphics, listener);
		code = src;
		prepareGUI();
		this.state = state;
	}
	
	@Override
	protected void prepareGUI() {
		selected = new NeImage(GUID + "_Selected");
		selected.connectStorage(graphics.getStorage());
		selected.setWidth(200);
		selected.setHeight(300);
		selected.setScale(1);
		selected.load("assets\\Selected.png");
		GUIElement = new NeContainer(GUID);
		NeDraggableController ctrl = new NeDraggableController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		ctrl.setThreshold(45);
		ctrl.setExtraSignature(EControllerSignatures.Card);
		GUIElement.connectStorage(graphics.getStorage());
		GUIElement.setWidth(200);
		GUIElement.setHeight(300);
		GUIElement.setScale(1);
		GUIElement.load(code);
	}
	
	public void onSelfDraw() {
		
	}
	
	public void onOtherDraw() {
		
	}
	
	public void onDeath() {
		
	}
	
	public void onActivate() {
		
	}
	
	public void onSelfPlay() {
		
	}
	
	public void onOtherPlay() {
		
	}
	
	public void onSelfDiscard() {
		
	}
	
	public void onOtherDiscard() {
		
	}
	
	public void canBePlayed() {
		
	}

	@Override
	public String getGUID() {
		return GUID;
	}

	@Override
	public String getRowGUID() {
		return rowGUID;
	}

	@Override
	public String getCardCode() {
		return code;
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
			GUIElement.add(this.selected);
			this.selected.fit();
		} else {
			GUIElement.remove(this.selected.getGUID());
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
}
