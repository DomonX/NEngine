package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.container.NeContainer;

public class NeMouseFollowerController extends NeMouseController{

	private int screenWidth;
	private int screenHeight;
	
	public NeMouseFollowerController(NeEntity entity, JFrame informer, int screenWidth, int screenHeight) {
		super(entity, informer);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(entity instanceof NeContainer) {
			((NeContainer)(entity)).getContent().clear();
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		int y = e.getY();
		int x = e.getX();
		if(screenHeight < entity.getHeight() + e.getY()) {
			y -= entity.getHeight();
		}
		if(screenWidth < entity.getWidth() + e.getX()) {
			x -= entity.getWidth();
		}
		entity.move(x, y);
	}

}
