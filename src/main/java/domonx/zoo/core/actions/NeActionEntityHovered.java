package domonx.zoo.core.actions;

import domonx.zoo.core.controller.EControllerSignatures;

public class NeActionEntityHovered extends NeAction{
	public NeActionEntityHovered(String srcGUIDPath, EControllerSignatures signature) {
		super(srcGUIDPath, ENeActionTypes.EntityHovered, signature);
	}

	@Override
	public Object getData() {
		return null;
	} 
}
