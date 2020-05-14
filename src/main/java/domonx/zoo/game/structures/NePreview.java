package domonx.zoo.game.structures;

import domonx.zoo.core.controller.NeMouseFollowerController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.window.NeGraphicsModule;

public class NePreview extends NeStructureElement<NeContainer> {
	public NePreview(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid, ENeStructureType.PREVIEW, graphics, listener);
		prepareGUI();
	}

	public void add(String GUID, String src) {
		createPreviewCard(GUID, src);
	}

	public void remove(String GUID) {
		guiElement.remove(GUID);
	}

	@Override
	protected void prepareGUI() {
		guiElement = new NeContainer(GUIDGenerator.get());
		@SuppressWarnings("unused")
		NeMouseFollowerController ctrl = new NeMouseFollowerController(guiElement, graphics.getWindow(),
				(int) (graphics.screen.getWidth()), (int) (graphics.screen.getHeight()));
		guiElement.setWidth(380);
		guiElement.setHeight(500);
	}

	protected NeImage createPreviewCard(String GUID, String src) {
		NeImage i = new NeImage(GUID);
		i.connectStorage(graphics.getStorage());
		guiElement.add(i);
		i.setScale(1);
		i.fit();
		i.load(src);
		return i;
	}
}
