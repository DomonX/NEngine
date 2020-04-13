package domonx.zoo.core.entity.container;

import domonx.zoo.core.entity.NeEntity;

public class NeVerticalLayout extends NeLayout{
	
	public int indent = 20;
	
	private int currentIndent = 0;
	
	private int biggestElementHeight = 0;

	public NeVerticalLayout(NeContainer container) {
		super(container);
	}

	@Override
	public void applyLayout() {
		if(container.content.isEmpty()) {
			return;
		}
		biggestElementHeight = 0;
		container.content.forEach((String key, NeEntity item) -> {
			biggestElementHeight = (int) (item.getHeight() > biggestElementHeight ? item.getHeight() : biggestElementHeight);
		});
		indent = (int) ((container.getHeight() - biggestElementHeight) / (container.content.size() - 1));
		container.content.forEach((String key, NeEntity item) -> {
			item.moveRelatively(0, currentIndent);
			currentIndent += indent;
		});
		currentIndent = 0;
	}

}
