package domonx.zoo.game.structures;

import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.window.NeGraphicsModule;

public class NeDiode extends NeStructureElement<NeImage> {

	public NeDiode(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid, ENeStructureType.DIODE, graphics, listener);
		prepareGUI();
	}

	public void setup(int width, int height, int posX, int posY) {
		guiElement.setHeight(height);
		guiElement.setWidth(width);
		guiElement.moveRelatively(posX, posY);
	}
	
	@Override
	protected void prepareGUI() {
		guiElement = new NeImage(GUIDGenerator.get());
		guiElement.connectStorage(getGraphics().getStorage());
		guiElement.setHeight(64);
		guiElement.setWidth(64);
		guiElement.load("assets\\signaler.png");
		guiElement.moveRelatively(0, 0);
	}

}
