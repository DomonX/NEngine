package domonx.zoo.game;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.window.NeGraphicsModule;

public abstract class NeStructureElement {
	public String GUID = "";
	public ENeStructureType type;
	public NeGraphicsModule graphics;
	public INeActionListener listener;

	public NeStructureElement(String GUID, ENeStructureType type, NeGraphicsModule graphics,
			INeActionListener listener) {
		this.GUID = GUID;
		this.type = type;
		this.graphics = graphics;
		this.listener = listener;
	}
	
	protected abstract void prepareGUI();
}
