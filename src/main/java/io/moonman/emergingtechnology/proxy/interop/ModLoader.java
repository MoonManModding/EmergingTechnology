package io.moonman.emergingtechnology.proxy.interop;

import io.moonman.emergingtechnology.proxy.interop.InactiveInteropProxy;
import io.moonman.emergingtechnology.proxy.interop.InteropProxy;
import net.minecraftforge.fml.common.Loader;

/**
This class provides methods for checking whether other mods are loaded alongside Emerging Technology
*/
public class ModLoader {
	
	private static boolean initialized = false;
	
	private static boolean ICLoaded = false;
	private static boolean OCLoaded = false;
	private static boolean CTLoaded = false;
	
	public static void init() {
		if (initialized) return;
		
		ICLoaded = Loader.isModLoaded("ic2");
		OCLoaded = Loader.isModLoaded("opencomputers");
		CTLoaded = Loader.isModLoaded("crafttweaker");
		
		initialized = true;
	}
	
	public static boolean isICLoaded() {
		return ICLoaded;
	}
	
	public static boolean isOpenComputersLoaded() {
		return OCLoaded;
	}

	public static boolean isCraftTweakerLoaded() {
		return CTLoaded;
	}

	// Legacy
	public static InteropProxy getProxy()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (ModLoader.isOpenComputersLoaded()) {
			return Class.forName("io.moonman.emergingtechnology.proxy.ActiveInteropProxy").asSubclass(InteropProxy.class).newInstance();
		  } else {
			return new InactiveInteropProxy();
		  }
	}
}
