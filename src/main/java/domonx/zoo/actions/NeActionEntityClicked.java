package domonx.zoo.actions;

import domonx.zoo.core.controller.EControllerSignatures;

public class NeActionEntityClicked extends NeAction{

	public NeActionEntityClicked(String srcGUIDPath, EControllerSignatures signature) {
		super(srcGUIDPath, ENeActionTypes.EntityClicked, signature);
	}

	@Override
	public Object getData() {
		return null;
	}
	
}
