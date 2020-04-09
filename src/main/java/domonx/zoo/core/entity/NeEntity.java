package domonx.zoo.core.entity;

import java.awt.Color;
import java.awt.Graphics;

import domonx.zoo.core.configuration.NeConstantsRegistry;
import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.entity.container.INeContainer;
import domonx.zoo.core.storage.NeImageStorage;
import domonx.zoo.core.util.NeEntityUtils;

public abstract class NeEntity implements INeEntity {

	protected NeImageStorage store = null;

	protected INeController controller = null;

	private double x = 0;
	private double y = 0;

	private double width = 0;
	private double height = 0;

	private double scale = 1;

	private boolean controllerActive = true;

	private INeContainer owner = null;

	private String GUID;

	private String GUIDPath;

	@Override
	public INeContainer getOwner() {
		return owner;
	}

	@Override
	public void setOwner(INeContainer owner) {
		this.owner = owner;
		this.GUIDPath = this.owner.getGUIDPath().concat("/").concat(GUID);
		setScale(owner.getScale());
		move(getX(), getY());
	}

	public NeEntity(NeImageStorage store, String GUID) {
		super();
		this.GUID = GUID;
		this.GUIDPath = GUID;
		this.store = store;
	}

	@Override
	public double getX() {
		return x * scale;
	}

	@Override
	public double getY() {
		return y * scale;
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
		this.x = x / scale;
		this.y = y / scale;
		if (owner == null) {
			return;
		}
		this.x += owner.getX();
		this.y += owner.getY();
	}

	@Override
	public void connectController(INeController controller) {
		this.controller = controller;
	}

	@Override
	public boolean isControllerActive() {
		return controllerActive;
	}

	@Override
	public void setControllerActive(boolean controllerActive) {
		this.controllerActive = controllerActive;
	}

	@Override
	public boolean isPointInside(int x, int y) {
		return NeEntityUtils.isPointInside(this, x, y);
	}

	@Override
	public void renderDev(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) (getX()), (int) (getY()), (int) (getWidth() - 1), (int) (getHeight() - 1));
	}

	protected abstract void reload();

	@Override
	public String getGUID() {
		return GUID;
	}

	@Override
	public String getGUIDPath() {
		return GUIDPath;
	}

	@Override
	public void tick(int hertz) {
		if(controller != null) {
			controller.tick(hertz);
		}
	}

}
