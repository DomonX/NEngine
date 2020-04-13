package domonx.zoo.core.storage;

import java.awt.Dimension;
import java.util.HashMap;

public class NeImageStorage implements IStorageUnlinkListener, INeImageStorage {
	
	private HashMap<String, NeCachedImageResize> images;
	
	public NeImageStorage() {
		images = new HashMap<String, NeCachedImageResize>();
	}
	
	@Override
	public NeCachedImage get(String key, double scale) {
		NeCachedImageResize temp = images.get(key);
		if(temp == null) {
			temp = new NeCachedImageResize(key, this);
			images.put(key, temp);
		}
		return temp.link(scale);
	}
	
	@Override
	public Dimension getSize(String key) {
		Dimension size = null;
		NeCachedImageResize temp = images.get(key);
		if(temp == null) {
			temp = new NeCachedImageResize(key, this);
			images.put(key, temp);
			size = new Dimension(temp.originalWidth, temp.originalHeight);
			images.remove(key);
			System.gc();
			return size;
		}
		size = new Dimension(temp.originalWidth, temp.originalHeight);
		return size;
	}
	
	@Override
	public void clear() {
		images.forEach((String key, NeCachedImageResize i) -> {
			i.clear();
		});
		images.clear();
	}
	
	@Override
	public void hardCache(String key, boolean mode) {
		NeCachedImageResize temp = images.get(key);
		if(temp == null) {
			return;
		}
		temp.hardCache = mode;
	}

	@Override
	public void signalUnlink(double scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signalUnlink(String key) {
		NeCachedImageResize temp = images.get(key);
		if(temp == null) {
			return;
		}
		images.remove(key);
		System.gc();	
	}
}
