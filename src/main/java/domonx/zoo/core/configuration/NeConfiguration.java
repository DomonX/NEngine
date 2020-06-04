package domonx.zoo.core.configuration;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class NeConfiguration {	
	
	private static long fps;
	private static long hertz;
	private static boolean isCapped;	
	private static long fpsTime;
	private static long hertzTime;	
	private static boolean isFullScreen;	
	private static Dimension screenResolution;	
	private static final String INSTALL_PATH;
	private static final boolean DEVELOPER_MODE;
	private static boolean showFps = true;
	private static final String SERVERIP;
	private static final int SERVERPORT;
	private static final boolean IS_SERVER;
	
	public static void loadConf() {
		
	}
	
	public static String getServerIp() {
		return SERVERIP;
	}

	public static int getServerPort() {
		return SERVERPORT;
	}

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

	public static boolean isServer() {
		return IS_SERVER;
	}

	static {
		File configuration = new File("config.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.exit(-1);
		}
		Document confDoc = null;
		try {
			confDoc = builder.parse(configuration);
		} catch (SAXException | IOException e) {
			System.exit(-1);
		}
		IS_SERVER = confDoc.getElementsByTagName("runMode").item(0).getTextContent().equals("server");
		INSTALL_PATH = confDoc.getElementsByTagName("installPath").item(0).getTextContent();
		DEVELOPER_MODE = confDoc.getElementsByTagName("developerMode").item(0).getTextContent().equals("true");
		SERVERIP = confDoc.getElementsByTagName("ip").item(0).getTextContent();
		SERVERPORT = Integer.parseInt(confDoc.getElementsByTagName("port").item(0).getTextContent());
		screenResolution = new Dimension();
		screenResolution.width = Integer.parseInt(confDoc.getElementsByTagName("width").item(0).getTextContent());
		screenResolution.height = Integer.parseInt(confDoc.getElementsByTagName("height").item(0).getTextContent());
		fps = Integer.parseInt(confDoc.getElementsByTagName("maxFps").item(0).getTextContent());
		isCapped = fps == 0;
		isFullScreen = confDoc.getElementsByTagName("fullscreen").item(0).getTextContent().equals("true");
		hertz = 30;
		loadFpsTime();
		loadHertzTime();
	}
	
}
