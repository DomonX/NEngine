package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import domonx.zoo.core.actions.NeActionEntityMoved;
import domonx.zoo.core.entity.NeEntity;

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
		if (!isValid()) {
			return;
		}
		listener.handleAction(new NeActionEntityMoved(entity.getGUIDPath(), e.getX(), e.getY(), (int) (this.offsetX),
				(int) (this.offsetY), getExtraSignature()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (clicked) {
			informDragged(e);
			clicked = false;
		}
	}

}
