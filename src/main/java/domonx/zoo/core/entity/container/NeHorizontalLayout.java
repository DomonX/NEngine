package domonx.zoo.core.entity.container;

import domonx.zoo.core.entity.NeEntity;

public class NeHorizontalLayout extends NeLayout{
	
	public int indent = 20;
	
	private int currentIndent = 0;
	
	private int biggestElementWidth = 0;

	public NeHorizontalLayout(NeContainer container) {
		super(container);
	}

	@Override
	public void applyLayout() {
		if(container.content.isEmpty()) {
			return;
		}
		biggestElementWidth = 0;
		container.content.forEach((String key, NeEntity item) -> {
			biggestElementWidth = (int) (item.getWidth() > biggestElementWidth ? item.getWidth() : biggestElementWidth);
		});		
		indent = (int) ((container.getWidth() - biggestElementWidth) / (container.content.size() - 1));
		container.content.forEach((String key, NeEntity item) -> {
			item.moveRelatively(currentIndent, 0);
			currentIndent += indent;
		});
		currentIndent = 0;
	}
	
}
