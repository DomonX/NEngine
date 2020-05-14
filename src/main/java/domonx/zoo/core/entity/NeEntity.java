package domonx.zoo.core.entity;

import java.awt.Color;
import java.awt.Graphics;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.configuration.NeConstantsRegistry;
import domonx.zoo.core.util.NeEntityUtils;

public abstract class NeEntity extends NeBaseEntity {

	private double onScreenX = 0;
	private double onScreenY = 0;
	
	private double realX = 0;
	private double realY = 0;

	private double width = 0;
	private double height = 0;

	private double scale = 1;

	public NeEntity(String guid) {
		super(guid);
	}

	@Override
	public double getX() {
		return onScreenX;
	}

	@Override
	public double getY() {
		return onScreenY;
	}

	@Override
	public double getWidth() {
		return width * scale;
	}

	@Override
	public double getHeight() {
		return height * scale;
	}

	@Override
	public void setWidth(double width) {
		this.width = width * scale;
		reload();
	}

	@Override
	public void setHeight(double height) {
		this.height = height * scale;
		reload();
	}
	
	@Override
	public void fit() {
		double ownerRealHeight = getOwner().getHeight() / getOwner().getScale();
		double ownerRealWidth = getOwner().getWidth() / getOwner().getScale();
		double entityHeight = getHeight();
		double entityWidth = getWidth();
		if(entityHeight == 0 || entityWidth == 0) {
			setHeight(ownerRealHeight);
			setWidth(ownerRealWidth);
			return;
		}
		double heightScale = ownerRealHeight / entityHeight;
		double widthScale = ownerRealWidth / entityWidth;
		double realScale = heightScale < widthScale ? heightScale : widthScale;
		setHeight(entityHeight * realScale);
		setWidth(entityWidth * realScale);
	}

	@Override
	public void setScale(double scale) {
		if (scale < NeConstantsRegistry.MIN_SCALE) {
			return;
		}
		this.scale = scale;
		reload();
	}

	@Override
	public double getScale() {
		return this.scale;
	}

	@Override
	public void move(double x, double y) {
		this.onScreenX = x;
		this.onScreenY = y;
		recalculateRealPosition();
	}
	
	public void moveRelatively(double x, double y) {
		this.realX = x;
		this.realY = y;
		recalculateOnScreenPosition();
	}
	
	@Override
	public void recalculateOnScreenPosition() {
		double shiftX = 0;
		double shiftY = 0;
		if(getOwner() != null) {
			shiftX = getOwner().getX();
			shiftY = getOwner().getY(); 
		}
		onScreenX = (this.realX * scale) + shiftX;
		onScreenY = (this.realY * scale) + shiftY;
	}
	
	@Override
	public void recalculateRealPosition() {
		double shiftX = 0;
		double shiftY = 0;
		if(getOwner() != null) {
			shiftX = getOwner().getX();
			shiftY = getOwner().getY();
		}
		this.realX = (this.onScreenX - shiftX) / scale;
		this.realY = (this.onScreenY - shiftY) / scale;
	}

	@Override
	public boolean isPointInside(int x, int y) {
		return NeEntityUtils.isPointInside(this, x, y);
	}

	@Override
	public void renderDev(Graphics g) {
		if(!NeConfiguration.isDeveloperMode()) {
			return;
		}
		g.setColor(Color.WHITE);
		g.drawRect((int) (getX()), (int) (getY()), (int) (getWidth() - 1), (int) (getHeight() - 1));
		g.drawString(getGUID(), (int)(getX()), (int)(getY() + NeConstantsRegistry.baseFont.getSize()));
	}
	
	protected abstract void reload();

}
