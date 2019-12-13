package io.moonman.emergingtechnology.providers.classes;

public class ModBulb {

    public int id;
    public String name;
    public int color;
    public int energyUsage;
    public int growthModifier;

    public int boostModifier;
    public boolean allPlants;
    public String[] plants;


    public ModBulb(int id, String name, int color, int energyUsage, int growthModifier, String[] plants, int boostModifier) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.energyUsage = energyUsage;
        this.growthModifier = growthModifier;

        this.boostModifier = boostModifier;
        this.allPlants = plants.length == 0;
        this.plants = plants;
    }
}