package io.moonman.emergingtechnology.config;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = EmergingTechnology.MODID)
@Config.LangKey("emergingtechnology.config.title")
public class EmergingTechnologyConfig {

	@Name("Grow Bed - Water Usage")
	@Config.Comment("The amount of water used by a grow bed per cycle (~10 ticks).")
	@RangeInt(min = 1, max = 100) 
	public static int growBedWaterUsePerCycle = 1;

	@Name("Dirt Growth Modifier %")
	@Config.Comment("Probability of growth from dirt medium per cycle (~10 ticks).")
	@RangeInt(min = 0, max = 100) 
	public static int growthDirtModifier = 1;

	@Name("Sand Growth Modifier %")
	@Config.Comment("Probability of growth from sand medium per cycle (~10 ticks).")
	@RangeInt(min = 0, max = 100) 
	public static int growthSandModifier = 4;

	@Name("Gravel Growth Modifier %")
	@Config.Comment("Probability of growth from gravel medium per cycle (~10 ticks).")
	@RangeInt(min = 0, max = 100) 
	public static int growthGravelModifier = 2;

	@Name("Clay Growth Modifier %")
	@Config.Comment("Probability of growth from clay medium per cycle (~10 ticks).")
	@RangeInt(min = 0, max = 100) 
	public static int growthClayModifier = 8;

	// public static final Client client = new Client();

	// public static class Client {

	// 	@Config.Comment("This is an example int property.")
	// 	public int baz = -100;

	// 	public final HUDPos chunkEnergyHUDPos = new HUDPos(0, 0);

	// 	public static class HUDPos {
	// 		public HUDPos(final int x, final int y) {
	// 			this.x = x;
	// 			this.y = y;
	// 		}

	// 		@Config.Comment("The x coordinate")
	// 		public int x;

	// 		@Config.Comment("The y coordinate")
	// 		public int y;
	// 	}
	// }

	@Mod.EventBusSubscriber(modid = EmergingTechnology.MODID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(EmergingTechnology.MODID)) {
				ConfigManager.sync(EmergingTechnology.MODID, Config.Type.INSTANCE);
			}
		}
	}
}