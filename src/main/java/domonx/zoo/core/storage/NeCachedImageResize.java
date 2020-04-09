package domonx.zoo.core.storage;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class NeCachedImageResize implements ImageObserver, IStorageUnlinkListener{
	
	private HashMap<Double, NeCachedImage> images;
	
	private Image original;
	
	protected int originalWidth = 0;
	
	protected int originalHeight = 0;
	
	protected boolean valid;
	
	public boolean hardCache = false;
	
	private IStorageUnlinkListener owner;
	
	public NeCachedImageResize(String src, IStorageUnlinkListener owner) {
		this.owner = owner;
		images = new HashMap<Double, NeCachedImage>();
		try {
			original = ImageIO.read(new File(src));
			originalWidth = original.getWidth(this);
			originalHeight = original.getHeight(this);
			valid  = true;
		} catch (IOException e) {
			valid = false;
		}
	}
	
	public NeCachedImage link(double scale) {
		NeCachedImage temp = images.get(scale);
		if(temp != null) {
			return temp;
		}
		temp = new NeCachedImage(original, scale, originalWidth, originalHeight, this);
		images.put(scale, temp);
		return temp.link();
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		originalWidth = x;
		originalHeight = y;
		return false;
	}
	
	public void clear() {
		images.clear();
	}

	@Override
	public void signalUnlink(double scale) {
		NeCachedImage temp = images.get(scale);
		if(temp == null) {
			return;
		}		
		images.remove(scale);
		System.gc();
		if(images.size() == 0 && !hardCache) {
			owner.signalUnlink(scale);
		}
	}

	@Override
	public void signalUnlink(String key) {
		// TODO Auto-generated method stub		
	}
}
