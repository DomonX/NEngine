package domonx.zoo.core.entity;

import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.storage.INeImageStorage;

public abstract class NeBaseEntity implements INeEntity {	
	
	protected INeImageStorage storage = null;

	protected INeController controller = null;
	
	private INeEntity owner = null;
	
	private String guid;
	
	private String guidPath;
	
	private boolean controllerActive = true;
	
	
	public NeBaseEntity(String gUID) {
		super();
		guid = gUID;
		guidPath = gUID;
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
		return guid;
	}

	@Override
	public String getGUIDPath() {
		return guidPath;
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
		if(guid == null) {
			return;
		}
		this.guidPath = owner.getGUIDPath().concat("/").concat(guid);
	}
	
	@Override
	public void destroy() {
		this.controller.destroy();
		this.controller = null;
	}

}
