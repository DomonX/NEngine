package domonx.zoo.game;

import domonx.zoo.core.controller.NeMouseFollowerController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.window.NeGraphicsModule;

public class NePreview extends NeStructureElement {
	public NePreview(String GUID, NeGraphicsModule graphics, INeActionListener listener) {
		super(GUID, ENeStructureType.preview, graphics, listener);
		prepareGUI();
	}

	private NeContainer GUIElement;

	public void add(String GUID, String src) {
		createPreviewCard(GUID, src);
	}

	public void remove(String GUID) {
		GUIElement.remove(GUID);
	}

	@Override
	protected void prepareGUI() {
		GUIElement = new NeContainer(GUIDGenerator.get());
		@SuppressWarnings("unused")
		NeMouseFollowerController ctrl = new NeMouseFollowerController(GUIElement, graphics.getWindow(),
				(int) (graphics.screen.getWidth()), (int) (graphics.screen.getHeight()));
		GUIElement.setWidth(380);
		GUIElement.setHeight(500);
		graphics.screen.add(GUIElement);
	}

	protected NeImage createPreviewCard(String GUID, String src) {
		NeImage i = new NeImage(GUID);
		i.connectStorage(graphics.getStorage());
		GUIElement.add(i);
		i.setScale(1);
		i.fit();
		i.load(src);
		return i;
	}
}
