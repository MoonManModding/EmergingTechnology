package io.moonman.emergingtechnology;

import io.moonman.emergingtechnology.proxy.interop.InactiveOCInteropProxy;
import io.moonman.emergingtechnology.proxy.interop.OCInteropProxy;
import net.minecraftforge.fml.common.Loader;

public class ModLoader {
	
	private static boolean initialized = false;
	
	private static boolean ICLoaded = false;
	private static boolean OCLoaded = false;
	
	public static void init() {
		if (initialized) return;
		
		ICLoaded = Loader.isModLoaded("ic2");
		OCLoaded = Loader.isModLoaded("opencomputers");
		
		initialized = true;
	}
	
	public static boolean isICLoaded() {
		return ICLoaded;
	}
	
	public static boolean isOpenComputersLoaded() {
		return OCLoaded;
	}

	public static OCInteropProxy getOCProxy()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (ModLoader.isOpenComputersLoaded()) {
			return Class.forName("io.moonman.emergingtechnology.proxy.ActiveOCInteropProxy").asSubclass(OCInteropProxy.class).newInstance();
		  } else {
			return new InactiveOCInteropProxy();
		  }
	}
}
