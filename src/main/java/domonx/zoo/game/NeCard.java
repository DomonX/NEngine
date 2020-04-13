package domonx.zoo.game;

import domonx.zoo.core.controller.NeDraggableController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeCard {
	
	public String GUID = "";
	public NeImage GUIElement;
	public int strenght;
	public int additionalStrenght;
	public String code;
	
	public NeCard(String GUID, String src, NeGraphicsModule graphics, INeActionListener listener) {
		prepareGUI(GUID, src, graphics, listener);
		code = src;
	}
	
	private void prepareGUI(String GUID, String src, NeGraphicsModule graphics, INeActionListener listener) {
		this.GUID = GUID;
		GUIElement = new NeImage(GUID);
		NeDraggableController ctrl = new NeDraggableController(GUIElement, graphics.getWindow());
		ctrl.addActionListener(listener);
		ctrl.setThreshold(45);
		GUIElement.connectStorage(graphics.getStorage());
		GUIElement.setWidth(200);
		GUIElement.setHeight(300);
		GUIElement.setScale(1);
		GUIElement.load(src);
	}
	
	public void onDraw() {
		
	}
	
	public void onThisDraw() {
		
	}
	
	public void onDeath() {
		
	}
	
	public void onActivate() {
		
	}
	
	public void onPlay() {
		
	}
	
	public void onDiscard() {
		
	}
}
