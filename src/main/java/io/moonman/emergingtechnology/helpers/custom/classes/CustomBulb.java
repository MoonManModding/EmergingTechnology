package io.moonman.emergingtechnology.helpers.custom.classes;

/**
A custom bulb loaded from a user-defined JSON file
*/
public class CustomBulb {

    public int id;
    public String name;
    public int color;
    public int energyUsage;
    public int growthModifier;

    public int boostModifier;
    public boolean allPlants;
    public String[] plants;


    public CustomBulb(int id, String name, int color, int energyUsage, int growthModifier, boolean allPlants, String[] plants, int boostModifier) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.energyUsage = energyUsage;
        this.growthModifier = growthModifier;

        this.boostModifier = boostModifier;
        this.allPlants = allPlants;
        this.plants = plants;
    }
}