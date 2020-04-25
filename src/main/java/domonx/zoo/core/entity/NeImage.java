package domonx.zoo.core.entity;

import java.awt.Dimension;
import java.awt.Graphics;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.storage.INeImageStorage;
import domonx.zoo.core.storage.NeCachedImage;
import domonx.zoo.core.storage.NeImageStorage;

public class NeImage extends NeEntity {
	
	public boolean visible = true;

	private NeCachedImage image;
	private String srcKey;

	private double imageScale = 1;

	private double imageShiftX = 0;
	private double imageShiftY = 0;
	
	private boolean valid = false;

	public NeImage(String GUID) {
		super(GUID);
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

	@Override
	public void load(String key) {
		srcKey = key;
		reload();
	}

	@Override
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
		if (size == null) {
			imageScale = 1;
		}
		if (getWidth() < getHeight()) {
			imageScale = getWidth() / size.width;
			imageShiftY = getHeight() - (size.height * imageScale);
			imageShiftY /= 2;
			imageShiftX = 0;
			return;
		}
		imageScale = getHeight() / size.height;
		imageShiftY = 0;
		imageShiftX = getWidth() - (size.width * imageScale);
		imageShiftX /= 2;
	}

}
