package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import domonx.zoo.core.entity.NeEntity;

public class NeMouseController extends NeController implements MouseListener, MouseMotionListener{	
	
	protected boolean clicked = false;
	protected boolean hovered = false;
	
	protected double offsetX = 0;
	protected double offsetY = 0;
	
	public NeMouseController(NeEntity entity, JFrame informer) {
		super(entity, informer);
	}
	
	protected void setClicked(int xPos, int yPos) {
		clicked = true;
		oldX = entity.getX();
		oldY = entity.getY();
		offsetX = xPos - entity.getX();
		offsetY = yPos - entity.getY();
	}

	@Override
	public void addInformer(JFrame informer) {
		informer.addMouseListener(this);
		informer.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(entity.isPointInside(e.getX(), e.getY())) {
			hovered = true;
			return;
		}
		hovered = false;	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(hovered) {
			setClicked(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicked = false;		
	}
}
