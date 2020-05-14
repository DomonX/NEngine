package domonx.zoo.game.interfaces;

import domonx.zoo.core.interfaces.INePickListener;

public interface INeCard extends INePickListener{
	public String getGuid();
	public String getRowGUID();
	public String getSrc();
	public String getCode();
	public int getStrength();
	public int getAdditionalStrength();
	public void setAdditionalStrength(int additionalString);
	public void setSelected(boolean selected);
	public String getOwnerGUID();
	public String getEnemyGUID();
	
	public void onSelfDraw();	
	public void onOtherDraw();	
	public void onDeath();	
	public void onActivate();	
	public void onSelfPlay();	
	public void onOtherPlay();	
	public void onSelfDiscard();	
	public void onOtherDiscard();	
	public void canBePlayed();
}
