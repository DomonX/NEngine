package domonx.zoo.core.entity;

import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.storage.INeImageStorage;

public abstract class NeBaseEntity implements INeEntity {	
	
	protected INeImageStorage storage = null;

	protected INeController controller = null;
	
	private INeEntity owner = null;
	
	private String GUID;
	
	private String GUIDPath;
	
	private boolean controllerActive = true;
	
	
	public NeBaseEntity(String gUID) {
		super();
		GUID = gUID;
		GUIDPath = gUID;
	}

	
	public INeController getController() {
		return controller;
	}

	@Override
	public INeEntity getOwner() {
		return owner;
	}

	@Override
	public void setOwner(INeEntity owner) {
		this.owner = owner;
		setScale(owner.getScale());
		recalculateOnScreenPosition();
	}
	
	@Override
	public String getGUID() {
		return GUID;
	}

	@Override
	public String getGUIDPath() {
		return GUIDPath;
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
	public void connectStorage(INeImageStorage storage) {
		this.storage = storage;
	}
	
	@Override
	public void tick(int hertz) {
		if(controller != null) {
			controller.tick(hertz);
		}
	}
	
	@Override
	public void connectController(INeController controller) {
		this.controller = controller;
	}
	
	public void updateGUID() {
		if(owner == null) {
			return;
		}
		this.GUIDPath = this.owner.getGUIDPath().concat("/").concat(GUID);
	}

}
