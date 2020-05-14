package domonx.zoo.game.structures;

import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.window.NeGraphicsModule;

public class NeSpecializedButtonBuilder {

	private NeGraphicsModule graphics;
	private NeAbstractActionListener listener;

	private NeStructureButton createdElement;

	public NeSpecializedButtonBuilder(NeGraphicsModule graphics, NeAbstractActionListener listener) {
		this.graphics = graphics;
		this.listener = listener;
	}

	public NeSpecializedButtonBuilder create(String guid) {
		createdElement = new NeStructureButton(guid, graphics, listener);
		return this;
	}

	public NeSpecializedButtonBuilder pos(double x, double y) {
		createdElement.guiElement.moveRelatively(x, y);
		return this;
	}

	public NeSpecializedButtonBuilder size(double width, double height) {
		createdElement.getGuiElement().setHeight(height);
		createdElement.getGuiElement().setWidth(width);
		return this;
	}

	public NeSpecializedButtonBuilder sign(EControllerSignatures signature) {
		createdElement.getGuiElement().getController().setExtraSignature(signature);
		return this;
	}

	public NeSpecializedButtonBuilder load(String key) {
		if (createdElement.getGuiElement().getWidth() < 0) {
			throw new Error(
					"Ne: Specialized button builder has not set proper width for item, enter width before loading image");
		}
		if (createdElement.getGuiElement().getHeight() < 0) {
			throw new Error(
					"Ne: Specialized button builder has not set proper height for item, enter height before loading image");
		}
		createdElement.getGuiElement().load(key);
		return this;
	}
	
	public NeSpecializedButtonBuilder connect() {
		createdElement.connect();
		return this;
	}

	public NeStructureButton get() {
		if (createdElement == null) {
			return new NeStructureButton(GUIDGenerator.get(), graphics, listener);
		}
		return createdElement;
	}

}
