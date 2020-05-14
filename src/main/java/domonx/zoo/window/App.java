package domonx.zoo.window;

import javax.swing.JFrame;

public class App {
	public static void main(String[] args) {
		NeBaseGame game = new NeBaseGame(new JFrame());
		game.show();
		game.enterMainLoop();
	}
}
