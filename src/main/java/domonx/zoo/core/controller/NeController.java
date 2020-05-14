package domonx.zoo.core.controller;

import javax.swing.JFrame;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.interfaces.NeAbstractActionListener;

public abstract class NeController implements INeController{
	
	protected JFrame informer;	
	protected double oldX = -1;
	protected double oldY = -1;
	
	protected NeEntity entity;
	protected NeAbstractActionListener listener;
	
	protected boolean isMovePermitted;
	
	private EControllerSignatures extraSignatrue;

	public NeController(NeEntity entity, JFrame informer) {
		super();
		this.entity = entity;
		entity.connectController(this);
		this.informer = informer;
		addInformer(informer); 
	}
	
	@Override
	public EControllerSignatures getExtraSignature() {
		return extraSignatrue;
	}
	
	@Override
	public void setExtraSignature(EControllerSignatures signature) {
		extraSignatrue = signature;
	}
	
	@Override
	public void addActionListener(NeAbstractActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void move(double x, double y) {
		if(!entity.isControllerActive()) {
			return;
		}
		entity.move(x, y);		
	}
	@Override
	public void tick(int hertz) {
		
	}
	@Override
	public void returnOldPosition() {
		move(oldX, oldY);
	}
	@Override
	public void destroy() {
		this.entity = null;
	}
	public boolean isValid() {
		return listener != null && entity != null;
	}
	public boolean isEntityValid() {
		return entity != null;
	}


}
