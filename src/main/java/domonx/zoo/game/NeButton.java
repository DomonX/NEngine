package domonx.zoo.game;

import domonx.zoo.core.controller.NeMouseController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.window.NeGraphicsModule;

public class NeButton extends NeImage{

	public NeButton(String GUID, NeGraphicsModule graphics) {
		super(GUID);
		NeMouseController ctrl = new NeMouseController(this, graphics.getWindow());
		connectStorage(graphics.getStorage());
	}

}
