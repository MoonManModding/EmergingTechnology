package io.moonman.emergingtechnology.config.polymers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;

public class PolymersModuleWorldGen {

    @Name("Enable Retroactive Generation")
    @Config.Comment(value = "Enable retrogen")
    public boolean RETROGEN = true;

    @Name("Enable Verbose Logging")
    @Config.Comment(value = "Enable verbose logging for retrogen")
    public boolean VERBOSE = false;

    @Name("Generate Polluted Blocks in Overworld")
    @Config.Comment(value = "Generate Polluted Blocks in Overworld")
    public boolean GENERATE_OVERWORLD = true;

    @Name("Generate Polluted Sand")
    @Config.Comment(value = "Generate Polluted Sand Blocks")
    public boolean GENERATE_SAND = true;

    @Name("Generate Polluted Dirt")
    @Config.Comment(value = "Generate Polluted Dirt Blocks")
    public boolean GENERATE_DIRT = true;

    @Name("Minimum Vein Size")
    @Config.Comment(value = "Minimum size of every ore vein")
    public int MIN_VEIN_SIZE = 4;

    @Name("Maximum Vein Size")
    @Config.Comment(value = "Maximum size of every ore vein")
    public int MAX_VEIN_SIZE = 8;

    @Name("Chance to spawn")
    @Config.Comment(value = "Maximum veins per chunk")
    public int CHANCES_TO_SPAWN = 9;

    @Name("Minimum spawn height")
    @Config.Comment(value = "Minimum height for the ore")
    public int MIN_Y = 60;

    @Name("Maximum spawn height")
    @Config.Comment(value = "Maximum height for the ore")
    public int MAX_Y = 100;
}