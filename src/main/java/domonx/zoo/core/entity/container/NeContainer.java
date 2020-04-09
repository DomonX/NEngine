package domonx.zoo.core.entity.container;

import java.awt.Graphics;
import java.util.HashMap;

import domonx.zoo.core.entity.INeEntity;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.storage.NeImageStorage;

public class NeContainer extends NeImage implements INeContainer{
	
	public HashMap<String, INeEntity> content;
	
	private INeLayout layout;

	public NeContainer(NeImageStorage storage, String GUID) {
		super(storage, GUID);
		content = new HashMap<String, INeEntity>();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		content.forEach((String key, INeEntity item) -> {
			item.render(g);
			item.renderDev(g);
		});
	}

	@Override
	public void add(INeEntity item) {
		item.setScale(getScale());
		item.setOwner(this);
		content.put(item.getGUID(), item);
	}

	@Override
	public void remove(String GUID) {
		content.remove(GUID);
	}

	@Override
	public int size() {
		return content.size();
	}

	@Override
	public void connectLayout(INeLayout layout) {
		this.layout = layout;		
	}
	
	@Override
	public void move(double x, double y) {
		super.move(x, y);
		content.forEach((String key, INeEntity item) -> {
			item.move(x, y);
		});
	}
	
	@Override
	public void setScale(double scale) {
		super.setScale(scale);
		content.forEach((String key, INeEntity item) -> {
			item.setScale(getScale());
		});
	}

}
