package io.moonman.emergingtechnology.config;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = EmergingTechnology.MODID)
@Config.LangKey("emergingtechnology.config.title")
public class EmergingTechnologyConfig {

	
	@Name("Hydroponics Module")
	@Comment("Configure Grow Beds and Lights here")
	public static final HydroponicsModule HYDROPONICS_MODULE = new HydroponicsModule();

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