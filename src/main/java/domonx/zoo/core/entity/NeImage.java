package domonx.zoo.core.entity;

import java.awt.Dimension;
import java.awt.Graphics;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.storage.INeImageStorage;
import domonx.zoo.core.storage.NeCachedImage;

public class NeImage extends NeEntity {
	
	private boolean visible = true;


	private NeCachedImage image;
	private String srcKey;

	private double imageScale = 1;

	private double imageShiftX = 0;
	private double imageShiftY = 0;
	
	private boolean valid = false;

	public NeImage(String guid) {
		super(guid);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public String getSrcKey() {
		return srcKey;
	}

	@Override
	public void render(Graphics g) {
		if(valid && visible) {
			g.drawImage(image.getImage(), (int) (getX() + imageShiftX), (int) (getY() + imageShiftY), null);
		}
		renderDev(g);
	}

	public void load(String key) {
		srcKey = key;
		reload();
	}

	public double getImageScale() {
		return imageScale;
	}
	
	@Override
	public void connectStorage(INeImageStorage storage) {
		super.connectStorage(storage);
		reload();
	}

	@Override
	protected void reload() {
		if (srcKey == null) {
			valid = false;
			return;
		}
		if(storage == null) {
			return;
		}
		calculateImage();
		NeCachedImage temp = storage.get(srcKey, getImageScale());
		if(temp == null || temp.getImage() == null) {
			System.out.println("Failed loading " + srcKey);
			valid = false;
		} else {
			valid = true;
		}
		if (image != null) {
			image.unlink();
		}
		image = temp;
	}
	
	private void calculateImage() {
		if(storage == null) {
			return;
		}
		Dimension size = storage.getSize(srcKey);
		if (getWidth() < getHeight()) {
			calculateImageOffsetX(size);
			return;
		}
		calculateImageOffsetY(size);
	}
	
	private void resetOffset() {
		imageScale = 1;
		imageShiftY = 0;
		imageShiftX = 0;
	}
	
	private void calculateImageOffsetX(Dimension size) {
		if(size == null) {
			resetOffset();
			return;
		}
		imageScale = getHeight() / size.height;
		imageShiftY = 0;
		imageShiftX = getWidth() - (size.width * imageScale);
		imageShiftX /= 2;
	}
	private void calculateImageOffsetY(Dimension size) {
		if(size == null) {
			resetOffset();
			return;
		}
		imageScale = getWidth() / size.width;
		imageShiftY = getHeight() - (size.height * imageScale);
		imageShiftY /= 2;
		imageShiftX = 0;
	}

	@Override
	public void destroy() {
		super.destroy();
		image.unlink();
	}

}
