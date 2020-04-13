package domonx.zoo.core.interfaces;

public interface INeGameObject {
	public double getX();
	public double getY();
	public double getWidth();
	public double getHeight();
	public void setWidth(double width);
	public void setHeight(double height);
	public boolean isPointInside(int x, int y);
	public void move(double x, double y);
	public void moveRelatively(double x, double y);
	public void recalculateOnScreenPosition();
	public void recalculateRealPosition(); 
	public void fit();
}
