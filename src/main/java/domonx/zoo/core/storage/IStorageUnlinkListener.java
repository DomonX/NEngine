package domonx.zoo.core.storage;

public interface IStorageUnlinkListener {
	public void signalUnlink(double scale);
	public void signalUnlink(String key);
}
