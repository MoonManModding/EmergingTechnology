package io.moonman.emergingtechnology.config.hydroponics.lights.boosts;

import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class IdealGrowthBoostGreenBulb implements IIdealBoostsConfiguration {

    @Name("Wheat")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Wheat")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int wheatAddedProbability = 0;

    @Name("Carrots")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Carrots")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int carrotsAddedProbability = 0;

    @Name("Potatoes")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Potatoes")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int potatoesAddedProbability = 0;

    @Name("Beetroot")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Beetroot")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int beetrootAddedProbability = 0;

    @Name("Sugarcane")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Sugarcane")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int sugarcaneAddedProbability = 5;

    @Name("Cactus")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Cacti")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int cactusAddedProbability = 0;
    
    @Name("Melon")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Melons")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int melonAddedProbability = 0;

    @Name("Pumpkin")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Pumpkins")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int pumpkinAddedProbability = 0;

    @Name("Nether Wart")
    @Config.Comment("When grown under this bulb, this value will be added to the base growth probability for Nether Wart")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int netherWartAddedProbability = 0;

    public int getBoost(CropTypeEnum cropType) {
        switch (cropType) {
        case WHEAT:
            return wheatAddedProbability;
        case CARROTS:
            return carrotsAddedProbability;
        case POTATOES:
            return potatoesAddedProbability;
        case BEETROOT:
            return beetrootAddedProbability;
        case REEDS:
            return sugarcaneAddedProbability;
        case CACTUS:
            return cactusAddedProbability;
        case MELON:
            return melonAddedProbability;
        case PUMPKIN:
            return pumpkinAddedProbability;
        case NETHERWART:
            return netherWartAddedProbability;
        default:
            return 0;
        }
    };
}