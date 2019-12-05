package io.moonman.emergingtechnology.init;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.block.blocks.Frame;
import io.moonman.emergingtechnology.block.blocks.Hydroponic;
import io.moonman.emergingtechnology.block.blocks.Light;
import io.moonman.emergingtechnology.item.items.BlueBulb;
import io.moonman.emergingtechnology.item.items.GreenBulb;
import io.moonman.emergingtechnology.item.items.PurpleBulb;
import io.moonman.emergingtechnology.item.items.RedBulb;
import io.moonman.emergingtechnology.tile.tiles.HydroponicTileEntity;
import io.moonman.emergingtechnology.tile.tiles.LightTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistrationHandler {

    public static void registerBlocks(Register<Block> event) {
        final Block[] blocks = { new Hydroponic(), new Light(), new Frame() };

        ResourceLocation hydroponicLocation = new ResourceLocation(EmergingTechnology.MODID + ":hydroponic");
        ResourceLocation lightLocation = new ResourceLocation(EmergingTechnology.MODID + ":light");

        GameRegistry.registerTileEntity(HydroponicTileEntity.class, hydroponicLocation);
        GameRegistry.registerTileEntity(LightTileEntity.class, lightLocation);

        event.getRegistry().registerAll(blocks);
    }

    public static void registerItems(Register<Item> event) {

        // Items
        final Item[] items = { new RedBulb(), new GreenBulb(), new BlueBulb(), new PurpleBulb() };

        // Items from Blocks
        Item hydroponic = generateItemBlock(ModBlocks.hydroponic);
        Item light = generateItemBlock(ModBlocks.light);
        Item frame = generateItemBlock(ModBlocks.frame);

        final Item[] itemBlocks = { hydroponic, light, frame };

        // Register
        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);
    }

    public static void registerModels(ModelRegistryEvent event) {

        // Item Models
        registerModel(ModItems.redbulb, 0);
        registerModel(ModItems.greenbulb, 0);
        registerModel(ModItems.bluebulb, 0);
        registerModel(ModItems.purplebulb, 0);

        // Block Models
        registerModel(Item.getItemFromBlock(ModBlocks.hydroponic), 0);
        registerModel(Item.getItemFromBlock(ModBlocks.light), 0);
        registerModel(Item.getItemFromBlock(ModBlocks.frame), 0);
    }

    private static Item generateItemBlock(Block block) {
        EmergingTechnology.logger.info("EmergingTechnology - Registering " + block.toString());
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}