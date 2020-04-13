package domonx.zoo.core.entity.container;

import java.awt.Graphics;
import java.util.HashMap;

import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.entity.INeEntity;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.NeImage;

public class NeContainer extends NeImage implements INeContainer{
	
	public HashMap<String, NeEntity> content;
	
	private INeLayout layout;

	public NeContainer(String GUID) {
		super(GUID);
		content = new HashMap<String, NeEntity>();
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
	public void add(NeEntity item) {
		item.setScale(getScale());
		item.setOwner(this);
		content.put(item.getGUID(), item);
		item.updateGUID();
		item.recalculateOnScreenPosition();
		layout();
	}

	@Override
	public void remove(String GUID) {
		content.remove(GUID);
		layout();
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
		layout();
	}
	
	@Override
	public void setScale(double scale) {
		super.setScale(scale);
		content.forEach((String key, NeEntity item) -> {
			item.setScale(scale);
		});
		layout();
	}
	
	@Override
	public void connectController(INeController controller) {
		this.controller = controller;
	}
	
	public void tick(int hertz) {
		content.forEach((String key, INeEntity item) -> {
			item.tick(hertz);
		});
	}
	
	@Override
	public void updateGUID() {
		super.updateGUID();
		content.forEach((String key, NeEntity item) -> {
			item.updateGUID();
		});
	}
	
	@Override
	public void recalculateOnScreenPosition() {
		super.recalculateOnScreenPosition();
		layout();
	}
	
	public void layout() {
		if(this.layout == null) {
			customRecalculateChilds();
			return;
		}
		layout.applyLayout();
	}
	
	protected void customRecalculateChilds() {
		content.forEach((String key, INeEntity item) -> {
			item.recalculateOnScreenPosition();
		});
	}

	@Override
	public INeLayout getLayout() {
		return this.layout;
	}
	

}
