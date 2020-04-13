package domonx.zoo.core.controller;

import javax.swing.JFrame;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.interfaces.INeActionListener;

public abstract class NeController implements INeController{
	
	protected JFrame informer;	
	protected double oldX = -1;
	protected double oldY = -1;
	
	protected NeEntity entity;
	protected INeActionListener listener;
	
	protected boolean isMovePermitted;

	public NeController(NeEntity entity, JFrame informer) {
		super();
		this.entity = entity;
		entity.connectController(this);
		this.informer = informer;
		addInformer(informer); 
	}

	@Override
	public abstract void addInformer(JFrame informer);
	
	@Override
	public void addActionListener(INeActionListener listener) {
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


}
