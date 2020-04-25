package domonx.zoo.game;

import domonx.zoo.core.interfaces.INePickListener;

public interface INeCard extends INePickListener{
	public String getGUID();
	public String getRowGUID();
	public String getCardCode();
	public int getStrength();
	public int getAdditionalStrength();
	public void setAdditionalStrength(int additionalString);
	public void setSelected(boolean selected);
	public String getOwnerGUID();
	public String getEnemyGUID();
}
