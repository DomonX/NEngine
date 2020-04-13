package domonx.zoo.actions;

public class NeActionEntityBlured extends NeAction{

	public NeActionEntityBlured(String srcGUIDPath) {
		super(srcGUIDPath, ENeActionTypes.EntityBlured);
	}

	@Override
	public Object getData() {
		return null;
	}
}
