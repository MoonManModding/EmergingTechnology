package io.moonman.emergingtechnology.helpers.machines;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.energy.EnergyStorageHandler;
import io.moonman.emergingtechnology.helpers.StackHelper;
import io.moonman.emergingtechnology.helpers.machines.classes.OptimiserPacket;
import io.moonman.emergingtechnology.recipes.machines.ShredderRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Provides useful methods for the Shredder
 */
public class ShredderHelper {

    public static int doShreddingProcess(World world, BlockPos pos, ItemStackHandler itemHandler,
            EnergyStorageHandler energyHandler, ItemStack inputStack, ItemStack outputStack, OptimiserPacket packet,
            int progress) {
                
        // Nothing in input stack
        if (inputStack.getCount() == 0) {
            return 0;
        }

        // Can't shred this item
        if (!ShredderRecipes.isValidInput(inputStack)) {
            return 0;
        }

        ItemStack plannedStack = ShredderRecipes.getOutputByItemStack(inputStack);

        // This is probably unneccessary
        if (plannedStack == null || plannedStack.isEmpty()) {
            return progress;
        }

        // Output stack is full
        if (outputStack.getCount() == 64) {
            return progress;
        }

        // Output stack incompatible/non-empty
        if (!StackHelper.compareItemStacks(outputStack, plannedStack) && !StackHelper.isItemStackEmpty(outputStack)) {
            return progress;
        }

        // Not enough energy
        if (energyHandler.getEnergyStored() < packet
                .calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderEnergyBaseUsage)) {
            return progress;
        }

        energyHandler.extractEnergy(
                packet.calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderEnergyBaseUsage),
                false);

        // Not enough operations performed
        if (progress < packet
                .calculateProgress(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderBaseTimeTaken)) {
            return progress += 1;
        }

        itemHandler.insertItem(1, plannedStack.copy(), false);
        itemHandler.extractItem(0, 1, false);

        energyHandler.extractEnergy(
                packet.calculateEnergyUse(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderEnergyBaseUsage),
                false);

        progress = 0;

        return progress;
    }
}