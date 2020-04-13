package domonx.zoo.actions;

public class NeActionEntityClicked extends NeAction{

	public NeActionEntityClicked(String srcGUIDPath) {
		super(srcGUIDPath, ENeActionTypes.EntityClicked);
	}

	@Override
	public Object getData() {
		return null;
	}
	
}
