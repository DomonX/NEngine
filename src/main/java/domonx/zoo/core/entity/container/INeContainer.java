package domonx.zoo.core.entity.container;

import domonx.zoo.core.entity.INeEntity;

public interface INeContainer extends INeEntity{
	public void add(INeEntity item);
	public void remove(String GUID);
	public int size();	
	public void connectLayout(INeLayout layout);
}
