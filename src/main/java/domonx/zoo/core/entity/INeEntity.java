package domonx.zoo.core.entity;

import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.interfaces.INeGameObject;
import domonx.zoo.core.interfaces.INeRenderable;
import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.core.storage.INeImageStorage;

public interface INeEntity extends INeTickListener, INeRenderable, INeGameObject{
	
	public String getGUID();
	public String getGUIDPath();
	
	public double getScale();
	public void setScale(double scale);
	
	public INeEntity getOwner();
	public void setOwner(INeEntity owner);
	
	// Controller Injection
	
	public void connectController(INeController controller);
	public boolean isControllerActive();
	public void setControllerActive(boolean activity);
	public INeController getController();
	
	// Storage Injection
	
	public void connectStorage(INeImageStorage storage);
	
	// Cleanup
	
	public void destroy();
	
}
