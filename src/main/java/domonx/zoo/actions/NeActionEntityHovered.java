package domonx.zoo.actions;

public class NeActionEntityHovered extends NeAction{
	public NeActionEntityHovered(String srcGUIDPath) {
		super(srcGUIDPath, ENeActionTypes.EntityHovered);
	}

	@Override
	public Object getData() {
		return null;
	} 
}
