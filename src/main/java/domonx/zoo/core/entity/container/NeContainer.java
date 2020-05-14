package domonx.zoo.core.entity.container;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import domonx.zoo.core.controller.INeController;
import domonx.zoo.core.entity.INeEntity;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.NeImage;

public class NeContainer extends NeImage implements INeContainer {

	protected Map<String, NeEntity> content = new HashMap<>();

	private INeLayout layout;

	public NeContainer(String guid) {
		super(guid);
	}

	public Map<String, NeEntity> getContent() {
		return content;
	}

	public void setContent(Map<String, NeEntity> content) {
		this.content = content;
	}

	@Override
	public void render(Graphics g) {
		if (!isVisible()) {
			return;
		}
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
	public void remove(String guid) {
		content.remove(guid);
		layout();
	}
	
	@Override
	public void destroy(String guid) {
		NeEntity removedElement = content.remove(guid);
		removedElement.destroy();
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
		content.forEach((String key, INeEntity item) -> item.tick(hertz));
	}

	@Override
	public void updateGUID() {
		super.updateGUID();
		content.forEach((String key, NeEntity item) -> item.updateGUID());
	}

	@Override
	public void recalculateOnScreenPosition() {
		super.recalculateOnScreenPosition();
		layout();
	}

	public void layout() {
		if (this.layout == null) {
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
	
	@Override 
	public void destroy() {
		content.forEach((String key, INeEntity item) -> {
			item.destroy();
		});
		super.destroy();
	}

}
