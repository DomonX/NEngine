package domonx.zoo.core.entity.container;

import domonx.zoo.core.entity.INeEntity;
import domonx.zoo.core.entity.NeEntity;

public interface INeContainer extends INeEntity{
	public void add(NeEntity item);
	public void remove(String guid);
	public void destroy(String guid);
	public int size();	
	public void connectLayout(INeLayout layout);
	public INeLayout getLayout();
}
