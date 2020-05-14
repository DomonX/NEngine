package domonx.zoo.core.controller;

import javax.swing.JFrame;

import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.interfaces.INeTickListener;

public interface INeController extends INeTickListener{
	public void addInformer(JFrame informer);
	public void addActionListener(NeAbstractActionListener listener);
	public void move(double x, double y);
	public void returnOldPosition();
	public EControllerSignatures getExtraSignature();
	public void setExtraSignature(EControllerSignatures signature);
	public void destroy();
	public boolean isValid();
	public boolean isEntityValid();
}
