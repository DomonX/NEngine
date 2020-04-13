package domonx.zoo.actions;

public abstract class NeAction {
	public String srcGUIDPath;
	public ENeActionTypes actionType;
	public abstract Object getData();
	
	protected NeAction(String srcGUIDPath, ENeActionTypes actionType) {
		super();
		this.srcGUIDPath = srcGUIDPath;
		this.actionType = actionType;
	}
	
	
}
