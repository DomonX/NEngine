package domonx.zoo.game;

import domonx.zoo.core.controller.NeMouseController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeButton extends NeImage{

	public NeButton(String GUID, NeGraphicsModule graphics, INeActionListener listener) {
		super(GUID);
		NeMouseController ctrl = new NeMouseController(this, graphics.getWindow());
		ctrl.addActionListener(listener);
		connectStorage(graphics.getStorage());
	}

}
