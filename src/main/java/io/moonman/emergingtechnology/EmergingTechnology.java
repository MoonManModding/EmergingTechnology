package io.moonman.emergingtechnology;

import org.apache.logging.log4j.Logger;

import io.moonman.emergingtechnology.gui.GuiProxy;
import io.moonman.emergingtechnology.proxy.CommonProxy;
import io.moonman.emergingtechnology.util.TechnologyTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = EmergingTechnology.MODID, name = EmergingTechnology.NAME, version = EmergingTechnology.VERSION)
public class EmergingTechnology
{
    public static final String MODID = "emergingtechnology";
    public static final String NAME = "Emerging Technology";
    public static final String VERSION = "1.1.0-beta";

    public static final TechnologyTab TECHNOLOGYTAB = new TechnologyTab("technologytab");

    @Mod.Instance()
    public static EmergingTechnology instance;

    public static Logger logger;

    @SidedProxy(clientSide="io.moonman.emergingtechnology.proxy.ClientOnlyProxy", serverSide="io.moonman.emergingtechnology.proxy.DedicatedServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
      proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
      proxy.init();
      NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
      proxy.postInit(event);
    }
}
