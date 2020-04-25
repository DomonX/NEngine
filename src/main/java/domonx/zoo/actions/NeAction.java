package domonx.zoo.actions;

import domonx.zoo.core.controller.EControllerSignatures;

public abstract class NeAction {
	public String srcGUIDPath;
	public ENeActionTypes actionType;
	public abstract Object getData();
	public EControllerSignatures signature;
	
	protected NeAction(String srcGUIDPath, ENeActionTypes actionType, EControllerSignatures signature) {
		super();
		this.srcGUIDPath = srcGUIDPath;
		this.actionType = actionType;
		this.signature = signature;
	}
	
	
}
