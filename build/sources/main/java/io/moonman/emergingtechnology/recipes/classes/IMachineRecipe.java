package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.ItemStack;

public interface IMachineRecipe {

   public ItemStack getInput();
   public ItemStack getOutput();
   
   public String getInputOreName();
   public boolean hasOreDictInput();

   public int getInputCount();
   public int getOutputCount();
}