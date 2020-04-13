package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import domonx.zoo.actions.NeActionEntityBlured;
import domonx.zoo.actions.NeActionEntityClicked;
import domonx.zoo.actions.NeActionEntityHovered;
import domonx.zoo.actions.NeActionEntityMoved;
import domonx.zoo.core.entity.NeEntity;

public class NeMouseController extends NeController implements MouseListener, MouseMotionListener {
	
	protected boolean clicked = false;
	protected boolean hovered = false;

	protected double offsetX = 0;
	protected double offsetY = 0;
	
	protected int hoverCounter = 0;
	protected int hoverThreshold = 0;

	public NeMouseController(NeEntity entity, JFrame informer) {
		super(entity, informer);
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
		boolean mouseInside = false;
		if (entity.isPointInside(e.getX(), e.getY())) {
			mouseInside = true;
		}
		if(mouseInside && !hovered) {
			informHovered();
			hovered = true;
			return;
		}
		if(!mouseInside && hovered) {
			informBlured();
			hovered = false;
			return;
		}
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
		if (hovered) {
			setClicked(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(clicked) {
			clicked = false;
		}
	}
	
	@Override
	public void tick(int hertz) {
		super.tick(hertz);
		handleHoverThreshold(hertz);
	}
	
	public void setThreshold(int threshold) {
		this.hoverThreshold = threshold;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	protected void setClicked(int xPos, int yPos) {
		if(!entity.isControllerActive()) {
			return;
		}
		clicked = true;
		oldX = entity.getX();
		oldY = entity.getY();
		offsetX = xPos - entity.getX();
		offsetY = yPos - entity.getY();
	}
	
	protected void handleHoverThreshold(int hertz) {
		if(!hovered) {
			return;
		}
		if(hoverThreshold == 0) {
			return;
		}
		hoverCounter += hertz;
	}
	
	protected void informHovered() {
		if(listener == null) {
			return;
		}
		listener.handleAction(new NeActionEntityHovered(entity.getGUIDPath()));
	}
	
	protected void informBlured() {
		if(listener == null) {
			return;
		}
		listener.handleAction(new NeActionEntityBlured(entity.getGUIDPath()));
	}
	
	protected void informClicked() {
		if(listener == null) {
			return;
		}
		listener.handleAction(new NeActionEntityClicked(entity.getGUIDPath()));
	}

}
