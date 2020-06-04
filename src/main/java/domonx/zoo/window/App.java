package domonx.zoo.window;

import javax.swing.JFrame;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.web.module.NeServer;


public class App {
	public static void main(String[] args) {
		if(NeConfiguration.isServer()) {
			NeServer s = new NeServer();
			s.run();
		} else {
			NeBaseGame game = new NeBaseGame(new JFrame());
			game.show();
			game.enterMainLoop();
		}

	}
}


