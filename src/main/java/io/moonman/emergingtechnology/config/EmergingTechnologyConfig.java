package io.moonman.emergingtechnology.config;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.electrics.ElectricsModule;
import io.moonman.emergingtechnology.config.hydroponics.HydroponicsModule;
import io.moonman.emergingtechnology.config.polymers.PolymersModule;
import io.moonman.emergingtechnology.config.synthetics.SyntheticsModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
The main configuration class for Emerging Technology
*/
@Config(modid = EmergingTechnology.MODID, name = "emergingtechnology/" + EmergingTechnology.NAME)
@Config.LangKey("config.emergingtechnology.title")
public class EmergingTechnologyConfig {
	
	@Name("Hydroponics Module")
	@LangKey("config.emergingtechnology.hydroponics.title")
	public static final HydroponicsModule HYDROPONICS_MODULE = new HydroponicsModule();

	@Name("Polymers Module")
	@LangKey("config.emergingtechnology.polymers.title")
	public static final PolymersModule POLYMERS_MODULE = new PolymersModule();

	@Name("Synthetics Module")
	@LangKey("config.emergingtechnology.synthetics.title")
	public static final SyntheticsModule SYNTHETICS_MODULE = new SyntheticsModule();

	@Name("Electrics Module")
	@LangKey("config.emergingtechnology.electrics.title")
	public static final ElectricsModule ELECTRICS_MODULE = new ElectricsModule();

	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(new ServerConfigEventHandler());
	}
	
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ClientConfigEventHandler());
	}

	@Mod.EventBusSubscriber(modid = EmergingTechnology.MODID)
	private static class ServerConfigEventHandler {

	}

	@Mod.EventBusSubscriber(modid = EmergingTechnology.MODID)
	private static class ClientConfigEventHandler {

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