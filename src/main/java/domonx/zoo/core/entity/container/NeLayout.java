package domonx.zoo.core.entity.container;

public abstract class NeLayout implements INeLayout{
	NeContainer container;
	NeLayout(NeContainer container) {
		this.container = container;
		container.connectLayout(this);
	}	
}
