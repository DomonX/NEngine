package domonx.zoo.core.controller;

import javax.swing.JFrame;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.interfaces.INeTickListener;

public interface INeController extends INeTickListener{
	public void addInformer(JFrame informer);
	public void addActionListener(INeActionListener listener);
	public void move(double x, double y);
}
