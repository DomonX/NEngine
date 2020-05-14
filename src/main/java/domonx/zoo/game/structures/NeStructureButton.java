package domonx.zoo.game.structures;

import domonx.zoo.controls.NeButton;
import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.window.NeGraphicsModule;

public class NeStructureButton extends NeStructureElement<NeButton> {

	public NeStructureButton(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid, ENeStructureType.BUTTON, graphics, listener);
		prepareGUI();
	}

	@Override
	protected void prepareGUI() {
		guiElement = new NeButton(GUIDGenerator.get(), getGraphics(), getListener());
		guiElement.getController().setExtraSignature(EControllerSignatures.END_BUTTON);
		guiElement.setHeight(100);
		guiElement.setWidth(100);
		guiElement.moveRelatively(0, 0);

	}

}
