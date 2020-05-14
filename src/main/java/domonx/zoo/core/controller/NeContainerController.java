package domonx.zoo.core.controller;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.container.NeContainer;

public class NeContainerController extends NeMouseController {

	private String currentKey;


	public NeContainerController(NeEntity entity, JFrame informer) {
		super(entity, informer);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if (!hovered) {
			return;
		}
		if (!(entity instanceof NeContainer)) {
			return;
		}
		NeContainer entityAsContainer = (NeContainer) (entity);
		entityAsContainer.getContent().forEach(
				(String key, NeEntity item) -> currentKey = item.isPointInside(e.getX(), e.getY()) ? key : currentKey);
		entityAsContainer.getContent()
				.forEach((String key, NeEntity item) -> item.setControllerActive(key.equals(currentKey)));
	}

	public String getCurrentKey() {
		return currentKey;
	}
}
