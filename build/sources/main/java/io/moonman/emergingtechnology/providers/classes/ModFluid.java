package io.moonman.emergingtechnology.providers.classes;

public class ModFluid {

    public int id;
    public String name;
    public int growthModifier;

    public int boostModifier;
    public boolean allPlants;
    public String[] plants;


    public ModFluid(int id, String name, int growthModifier, String[] plants, int boostModifier) {
        this.id = id;
        this.name = name;
        this.growthModifier = growthModifier;

        this.boostModifier = boostModifier;
        this.allPlants = plants.length == 0;
        this.plants = plants;
    }
}