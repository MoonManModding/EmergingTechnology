package io.moonman.emergingtechnology.integration;

import net.minecraftforge.fml.common.Loader;

/**
This class provides methods for checking whether other mods are loaded alongside Emerging Technology
*/
public class ModLoader {
	
	private static boolean initialized = false;
	
	private static boolean ICLoaded = false;
	private static boolean OCLoaded = false;
	private static boolean CTLoaded = false;
	private static boolean DDLoaded = false;
	private static boolean ACLoaded = false;
	
	public static void preInit() {
		if (initialized) return;
		
		ICLoaded = Loader.isModLoaded("ic2");
		OCLoaded = Loader.isModLoaded("opencomputers");
		CTLoaded = Loader.isModLoaded("crafttweaker");
		DDLoaded = Loader.isModLoaded("dumpsterdiving");
		ACLoaded = Loader.isModLoaded("alchemistry");
		
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

	public static boolean isDumpsterDivingLoaded() {
		return DDLoaded;
	}

	public static boolean isAlchemistryLoaded() {
		return ACLoaded;
	}
}
