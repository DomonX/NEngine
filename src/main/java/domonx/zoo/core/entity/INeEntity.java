package domonx.zoo.core.entity;

import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.entity.container.INeContainer;
import domonx.zoo.core.interfaces.INeRenderable;
import domonx.zoo.core.interfaces.INeTickListener;

public interface INeEntity extends INeTickListener, INeRenderable{
	public String getGUID();
	public String getGUIDPath();
	
	public double getX();
	public double getY();
	public double getWidth();
	public double getHeight();
	public void setWidth(double width);
	public void setHeight(double height);
	public boolean isPointInside(int x, int y);
	public void move(double x, double y);
	public double getScale();
	public void setScale(double scale);
	
	public INeContainer getOwner();
	public void setOwner(INeContainer owner);
	
	public double getImageScale();
	
	public void load(String key);
	
	public void connectController(INeController controller);
	public boolean isControllerActive();
	public void setControllerActive(boolean activity);
	
}
