package domonx.zoo.core.actions;

import domonx.zoo.core.controller.EControllerSignatures;

public class NeActionEntityBlured extends NeAction{

	public NeActionEntityBlured(String srcGUIDPath, EControllerSignatures signature) {
		super(srcGUIDPath, ENeActionTypes.EntityBlured, signature);
	}

	@Override
	public Object getData() {
		return null;
	}
}
