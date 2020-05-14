package domonx.zoo.game.structures;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.window.NeGraphicsModule;

public abstract class NeStructureElement<G extends NeEntity> {

	protected String guid = "";
	protected G guiElement;
	protected ENeStructureType type;
	protected NeGraphicsModule graphics;
	protected NeAbstractActionListener listener;

	public NeStructureElement(String guid, ENeStructureType type, NeGraphicsModule graphics,
			NeAbstractActionListener listener) {
		this.guid = guid;
		this.type = type;
		this.graphics = graphics;
		this.listener = listener;
	}

	public String getGuid() {
		return guid;
	}

	public ENeStructureType getType() {
		return type;
	}

	public void setType(ENeStructureType type) {
		this.type = type;
	}

	public NeGraphicsModule getGraphics() {
		return graphics;
	}

	public NeAbstractActionListener getListener() {
		return listener;
	}

	public G getGuiElement() {
		return guiElement;
	}

	public void setGuiElement(G guiElement) {
		this.guiElement = guiElement;
	}

	public void setListener(NeAbstractActionListener listener) {
		this.listener = listener;
	}

	public void connect() {
		if(getGuiElement() == null) {
			throw new Error("Ne: couldn't connect gui of id: " + getGuid() + " it's null");
		}
		getGraphics().screen.add(getGuiElement());
	}

	public void connect(NeContainer owner) {
		owner.add(getGuiElement());
	}

	protected abstract void prepareGUI();
}
