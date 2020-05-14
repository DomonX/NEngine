package domonx.zoo.controls;

import domonx.zoo.core.controller.NeMouseController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeButton extends NeImage{

	public NeButton(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid);
		NeMouseController ctrl = new NeMouseController(this, graphics.getWindow());
		ctrl.addActionListener(listener);
		connectStorage(graphics.getStorage());
	}

}
