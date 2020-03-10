package io.moonman.emergingtechnology.helpers.machines.classes;

public class OptimiserPacket {
    public final int energyModifier;
    public final int fluidModifier;
    public final int progressModifier;

    public OptimiserPacket(int energy, int fluid, int progress) {
        energyModifier = energy;
        fluidModifier = fluid;
        progressModifier = progress;
    }
}