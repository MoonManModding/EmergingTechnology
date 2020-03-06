package io.moonman.emergingtechnology.config;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.electrics.ElectricsConfiguration;
import io.moonman.emergingtechnology.config.hydroponics.HydroponicsConfiguration;
import io.moonman.emergingtechnology.config.polymers.PolymersConfiguration;
import io.moonman.emergingtechnology.config.synthetics.SyntheticsConfiguration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

    public static ForgeConfigSpec COMMON_CONFIG;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        HydroponicsConfiguration.initCommon(COMMON_BUILDER);
        SyntheticsConfiguration.initCommon(COMMON_BUILDER);
        PolymersConfiguration.initCommon(COMMON_BUILDER);
        ElectricsConfiguration.initCommon(COMMON_BUILDER);

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading configEvent) {
    }

}