package domonx.zoo.core.configuration;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class NeConfiguration {	
	
	private static long fps;
	private static long hertz;
	private static boolean isCapped;	
	private static long fpsTime;
	private static long hertzTime;	
	private static boolean isFullScreen;	
	private static Dimension screenResolution;	
	private static final String INSTALL_PATH = "E:\\Programowanie\\Java\\Eclipse\\zoo";
	private static final boolean DEVELOPER_MODE = true;
	private static boolean showFps = true;
	
	private NeConfiguration() {
		
	}
	
	public static long getHertz() {
		return hertz;
	}
	
	public static long getFps() {
		return fps;
	}
	
	public static boolean isFpsCapped() {
		return isCapped;
	}
	
	public static long getFpsTime() {
		return fpsTime;
	}
	
	public static long getHertzTime() {
		return hertzTime;
	}
	
	public static void setFps(long fps) {
		NeConfiguration.fps = fps;
		loadFpsTime();
	}
	
	public static void setHertz(long hertz) {
		NeConfiguration.hertz = hertz;
		loadHertzTime();
	}
	
	public static void loadConfigTo(JFrame window) {
		window.setSize(screenResolution);
		if(isFullScreen) {
			setFullScreen(window);			
		}
		if(!isFullScreen) {
			setWindowed(window);
		}
	}
	
	public static void setResolution(int width, int height) {
		screenResolution.width = width;
		screenResolution.height = height;
	}
	
	public static String getPath() {
		return INSTALL_PATH;
	}
	
	public static boolean isDeveloperMode() {
		return DEVELOPER_MODE;
	}
	
	public static boolean isShowFps() {
		return showFps;
	}
	
	public static void setShowFps(boolean showFps) {
		NeConfiguration.showFps = showFps;
	}
	
	private static void setFullScreen(JFrame target) {
		target.setUndecorated(true);
	}
	
	private static void setWindowed(JFrame target) {
		target.setUndecorated(false);
	}
	
	private static void loadFpsTime() {
		fpsTime = NeConstantsRegistry.SECOND_IN_NANO / NeConfiguration.getFps();
	}
	
	private static void loadHertzTime() {
		hertzTime = NeConstantsRegistry.SECOND_IN_NANO / NeConfiguration.getHertz();
	}
	
	private static void setDefaultGraphics() {
		isFullScreen = true;
		screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
	}
	

	static {
		fps = 60;
		hertz = 30;
		isCapped = false;
		setDefaultGraphics();
		loadFpsTime();
		loadHertzTime();
	}
	
}
