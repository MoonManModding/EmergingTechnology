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

    @Name("Generate Polluted Gravel")
    @Config.Comment(value = "Generate Polluted Gravel Blocks")
    public boolean GENERATE_GRAVEL = true;

    // Sand

    @Name("Sand Biome Restriction")
    @Config.Comment(value = "Restrict Polluted Sand to Ocean, Beach and River Biomes")
    public boolean SAND_BIOME_RESTRICTION = true;

    @Name("Minimum Sand Vein Size")
    @Config.Comment(value = "Minimum size of every sand vein")
    public int SAND_MIN_VEIN_SIZE = 4;

    @Name("Maximum Sand Vein Size")
    @Config.Comment(value = "Maximum size of every sand vein")
    public int SAND_MAX_VEIN_SIZE = 8;

    @Name("Chance to spawn sand")
    @Config.Comment(value = "Maximum sand veins per chunk")
    public int SAND_CHANCES_TO_SPAWN = 9;

    @Name("Sand Minimum spawn height")
    @Config.Comment(value = "Minimum height for Polluted Sand")
    public int SAND_MIN_Y = 60;

    @Name("Sand Maximum spawn height")
    @Config.Comment(value = "Maximum height for Polluted Sand")
    public int SAND_MAX_Y = 100;

    // Dirt

    @Name("Dirt Biome Restriction")
    @Config.Comment(value = "Restrict Polluted Dirt to Ocean, Beach and River Biomes")
    public boolean DIRT_BIOME_RESTRICTION = true;

    @Name("Minimum Dirt Vein Size")
    @Config.Comment(value = "Minimum size of every Dirt vein")
    public int DIRT_MIN_VEIN_SIZE = 3;

    @Name("Maximum Dirt Vein Size")
    @Config.Comment(value = "Maximum size of every Dirt vein")
    public int DIRT_MAX_VEIN_SIZE = 5;

    @Name("Chance to spawn Dirt")
    @Config.Comment(value = "Maximum Dirt veins per chunk")
    public int DIRT_CHANCES_TO_SPAWN = 9;

    @Name("Dirt Minimum spawn height")
    @Config.Comment(value = "Minimum height for Polluted Dirt")
    public int DIRT_MIN_Y = 60;

    @Name("Dirt Maximum spawn height")
    @Config.Comment(value = "Maximum height for Polluted Dirt")
    public int DIRT_MAX_Y = 100;

    // Gravel

    @Name("Gravel Biome Restriction")
    @Config.Comment(value = "Restrict Polluted Gravel to Ocean, Beach and River Biomes")
    public boolean GRAVEL_BIOME_RESTRICTION = true;

    @Name("Minimum Gravel Vein Size")
    @Config.Comment(value = "Minimum size of every Gravel vein")
    public int GRAVEL_MIN_VEIN_SIZE = 5;

    @Name("Maximum Gravel Vein Size")
    @Config.Comment(value = "Maximum size of every Gravel vein")
    public int GRAVEL_MAX_VEIN_SIZE = 10;

    @Name("Chance to spawn Gravel")
    @Config.Comment(value = "Maximum Gravel veins per chunk")
    public int GRAVEL_CHANCES_TO_SPAWN = 9;

    @Name("Gravel Minimum spawn height")
    @Config.Comment(value = "Minimum height for Polluted Gravel")
    public int GRAVEL_MIN_Y = 25;

    @Name("Gravel Maximum spawn height")
    @Config.Comment(value = "Maximum height for Polluted Gravel")
    public int GRAVEL_MAX_Y = 60;
}