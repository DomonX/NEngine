package domonx.zoo.core.storage;

import java.awt.Image;

public class NeCachedImage {
	
	protected Image originalImage = null;
	protected Image cachedImage = null;
	protected String src = "";
	protected boolean hardCached = false;
	protected int numberOfLeeches = 0;
	protected double scale = 1;
	protected int originalWidth = 0;
	protected int originalHeight = 0;
	
	protected int width = 0;
	protected int height = 0;
	
	private IStorageUnlinkListener owner;
	
	
	NeCachedImage(Image src, double scale, int originalWidth, int originalHeight, IStorageUnlinkListener owner) {
		this.owner = owner;
		originalImage = src;
		numberOfLeeches = 0;
		this.scale = scale;
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.cachedImage = getScaledImage(originalImage);
	}
	
	public NeCachedImage link() {
		numberOfLeeches++;
		return this;
	}
	
	public void unlink() {
		numberOfLeeches--;
		if(numberOfLeeches < 1 && !hardCached) {
			owner.signalUnlink(this.scale);
		}
	}	
	
	public Image getImage() {
		return cachedImage;
	}
	
	public int getWidth() {
		return (int) (originalWidth * scale);
	}
	
	public int getHeight() {
		return (int) (originalHeight * scale);
	}
	
	protected Image getScaledImage(Image source) {
		if (scale == 1) {
			return source;
		}
		return source.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
	}

}
