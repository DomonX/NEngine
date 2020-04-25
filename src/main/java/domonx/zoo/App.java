package domonx.zoo;

import javax.swing.JFrame;

import domonx.zoo.window.NeBaseGame;

public class App {
	public static void main(String[] args) {
		NeBaseGame game = new NeBaseGame(new JFrame());
		game.window.setVisible(true);
		game.enterMainLoop();
	}
}
