package io.moonman.emergingtechnology.config.hydroponics.beds.boosts;

import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;
import io.moonman.emergingtechnology.config.hydroponics.interfaces.IIdealBoostsConfiguration;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class IdealGrowthBoostBiochar implements IIdealBoostsConfiguration {

    @Name("Wheat")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Wheat")
    @RangeInt(min = 0, max = 100)
    public int wheatAddedProbability = 5;

    @Name("Carrots")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Carrots")
    @RangeInt(min = 0, max = 100)
    public int carrotsAddedProbability = 5;

    @Name("Potatoes")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Potatoes")
    @RangeInt(min = 0, max = 100)
    public int potatoesAddedProbability = 5;

    @Name("Beetroot")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Beetroot")
    @RangeInt(min = 0, max = 100)
    public int beetrootAddedProbability = 5;

    @Name("Sugarcane")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Sugarcane")
    @RangeInt(min = 0, max = 100)
    public int sugarcaneAddedProbability = 5;

    @Name("Cactus")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Cacti")
    @RangeInt(min = 0, max = 100)
    public int cactusAddedProbability = 5;

    @Name("Pumpkin")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Pumpkin")
    @RangeInt(min = 0, max = 100)
    public int pumpkinAddedProbability = 5;
    
    @Name("Melon")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Melons")
    @RangeInt(min = 0, max = 100)
    public int melonAddedProbability = 5;

    @Name("Nether Wart")
    @Config.Comment("When grown on this medium, this value will be added to the base growth probability for Nether Wart")
    @RangeInt(min = 0, max = 100)
    public int netherWartAddedProbability = 5;

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