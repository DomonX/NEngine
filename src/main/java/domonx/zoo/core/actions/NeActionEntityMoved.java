package domonx.zoo.core.actions;

import domonx.zoo.core.actions.data.NeActionEntityMovedData;
import domonx.zoo.core.controller.EControllerSignatures;

public class NeActionEntityMoved extends NeAction{
	
	public NeActionEntityMovedData data = new NeActionEntityMovedData();

	@Override
	public Object getData() {
		return data;
	}

	public NeActionEntityMoved(String srcGUIDPath, int x, int y, EControllerSignatures signature) {
		super(srcGUIDPath, ENeActionTypes.EntityDragged, signature);
		data.x = x;
		data.y = y;
	} 
	
	public NeActionEntityMoved(String srcGUIDPath, int x, int y, int xOffset, int yOffset, EControllerSignatures signature) {
		super(srcGUIDPath, ENeActionTypes.EntityDragged, signature);
		data.x = x;
		data.y = y;
		data.xOffset = xOffset;
		data.yOffset = yOffset;
	}
}
