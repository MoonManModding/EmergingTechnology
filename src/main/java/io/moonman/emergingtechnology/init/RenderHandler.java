package io.moonman.emergingtechnology.init;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
public class RenderHandler {

    public static void registerMeshesAndStatesForBlock(Block block) {

        ModelResourceLocation resourceLocation = new ModelResourceLocation(
                block.getRegistryName(), "fluid");

        ItemMeshDefinition meshDefinition = new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return resourceLocation;
            }
        };

        StateMapperBase stateMapper = new StateMapperBase() {
            @Override
            public ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return resourceLocation;
            }
        };

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), meshDefinition);
        ModelLoader.setCustomStateMapper(block, stateMapper);

    }
}