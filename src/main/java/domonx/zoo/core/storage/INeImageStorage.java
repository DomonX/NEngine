package domonx.zoo.core.storage;

import java.awt.Dimension;

public interface INeImageStorage {
	public NeCachedImage get(String key, double scale);	
	public Dimension getSize(String key);	
	public void clear();	
	public void hardCache(String key, boolean mode);
}
