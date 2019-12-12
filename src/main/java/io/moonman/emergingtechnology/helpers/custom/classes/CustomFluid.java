package io.moonman.emergingtechnology.helpers.custom.classes;

/**
A custom fluid loaded from a user-defined JSON file
*/
public class CustomFluid {

    public int id;
    public String name;
    public int color;
    public int fluidUsage;
    public int growthModifier;

    public int boostModifier;
    public boolean allPlants;
    public String[] plants;


    public CustomFluid(int id, String name, int fluidUsage, int growthModifier, boolean allPlants, String[] plants, int boostModifier) {
        this.id = id;
        this.name = name;
        this.fluidUsage = fluidUsage;
        this.growthModifier = growthModifier;

        this.boostModifier = boostModifier;
        this.allPlants = allPlants;
        this.plants = plants;
    }
}