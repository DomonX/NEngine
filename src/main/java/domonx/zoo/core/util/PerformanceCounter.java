package domonx.zoo.core.util;

import domonx.zoo.core.configuration.NeConstantsRegistry;

public class PerformanceCounter {
	
	private PerformanceCounter() {
		
	}
	
	private static long time;
	
	public static void start() {
		time = System.nanoTime();
	}
	
	public static void end() {
		System.out.println((double)(System.nanoTime() - time) / NeConstantsRegistry.SECOND_IN_NANO);
	}
}
