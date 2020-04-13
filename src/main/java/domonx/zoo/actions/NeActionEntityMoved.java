package domonx.zoo.actions;

import domonx.zoo.actions.data.NeActionEntityMovedData;

public class NeActionEntityMoved extends NeAction{
	
	public NeActionEntityMovedData data = new NeActionEntityMovedData();

	@Override
	public Object getData() {
		return data;
	}

	public NeActionEntityMoved(String srcGUIDPath, int x, int y) {
		super(srcGUIDPath, ENeActionTypes.EntityDragged);
		data.x = x;
		data.y = y;
	} 
	
	public NeActionEntityMoved(String srcGUIDPath, int x, int y, int xOffset, int yOffset) {
		super(srcGUIDPath, ENeActionTypes.EntityDragged);
		data.x = x;
		data.y = y;
		data.xOffset = xOffset;
		data.yOffset = yOffset;
	}
}
