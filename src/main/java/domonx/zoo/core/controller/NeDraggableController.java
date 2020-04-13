package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import domonx.zoo.actions.NeActionEntityMoved;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.interfaces.INeActionListener;

public class NeDraggableController extends NeMouseController {

	public NeDraggableController(NeEntity entity, JFrame informer) {
		super(entity, informer);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!clicked) {
			return;
		}
		move(e.getX() - offsetX, e.getY() - offsetY);
	}
	
	protected void informDragged(MouseEvent e) {
		if(listener == null) {
			return;
		}
		listener.handleAction(new NeActionEntityMoved(entity.getGUIDPath(), e.getX(), e.getY(), (int) (this.offsetX), (int) (this.offsetY)));
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(clicked) {
			informDragged(e);
			clicked = false;
		}
	}
	

}
