package io.moonman.emergingtechnology.machines.classes.tile;

import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;

/**
 * A machine tile that can be optimised by an Optimiser
 */
public interface IOptimisableTile {

    public OptimiserPacket getPacket();
    public void setPacket(OptimiserPacket packet);

}