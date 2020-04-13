package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.container.NeContainer;

public class NeContainerController extends NeMouseController{
	
	public String currentKey;

	public NeContainerController(NeEntity entity, JFrame informer) {
		super(entity, informer);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if(!hovered) {
			return;
		}
		if(!(entity instanceof NeContainer)) {
			return;
		}
		int x = e.getX();
		int y = e.getY();
		NeContainer entityAsContainer = (NeContainer)(entity);
		entityAsContainer.content.forEach((String key, NeEntity item) -> {
			if(item.isPointInside(x, y)) {
				currentKey = key;				
			}
		});
		entityAsContainer.content.forEach((String key, NeEntity item) -> {
			if(key == currentKey) {
				item.setControllerActive(true);
			} else {
				item.setControllerActive(false);
			}
		});
	}

}
