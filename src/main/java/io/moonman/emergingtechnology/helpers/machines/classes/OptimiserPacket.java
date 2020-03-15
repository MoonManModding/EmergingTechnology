package io.moonman.emergingtechnology.helpers.machines.classes;

/**
 * A packet containing performance boosts for machines. Created by nearby Optimisers
 */
public class OptimiserPacket {
    public int energyModifier;
    public int fluidModifier;
    public int progressModifier;

    public OptimiserPacket(int energy, int fluid, int progress) {
        energyModifier = energy;
        fluidModifier = fluid;
        progressModifier = progress;
    }

    public int calculateEnergyUse(int value) {
        return value / energyModifier;
    }

    public int calculatorFluidUse(int value) {
        return value / fluidModifier;
    }

    public int calculateProgress(int value) {
        return value / progressModifier;
    }

    public void reset() {
        energyModifier = 1;
        fluidModifier = 1;
        progressModifier = 1;
    }
}