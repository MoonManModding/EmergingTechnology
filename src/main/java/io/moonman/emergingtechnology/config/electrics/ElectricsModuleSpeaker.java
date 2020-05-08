package io.moonman.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModuleSpeaker {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Speaker Listen Radius")
    @Config.Comment("Radius in blocks a Speaker will listen for chat messages")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int listenRadius = 15;
}