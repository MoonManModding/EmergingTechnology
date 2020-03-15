package io.moonman.emergingtechnology.providers;

import java.util.ArrayList;

import io.moonman.emergingtechnology.item.synthetics.SampleItemBase;
import io.moonman.emergingtechnology.item.synthetics.SyringeItemBase;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import io.moonman.emergingtechnology.providers.loaders.CustomTissueLoader;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModTissueProvider {

    public static ModTissue[] allTissues;
    public static ModTissue[] customTissues;

    public static ArrayList<SyringeItemBase> modSyringes = new ArrayList<SyringeItemBase>();
    public static ArrayList<SampleItemBase> modSamples = new ArrayList<SampleItemBase>();

    public static void preInit(FMLPreInitializationEvent event) {

        generateBaseTissues();

        readFromFile(event);

        ArrayList<ModTissue> generatedTissues = new ArrayList<ModTissue>();

        generatedTissues.addAll(generateBaseTissues());
        generatedTissues.addAll(generateCustomTissues());

        allTissues = generatedTissues.toArray(new ModTissue[0]);
        
        modSyringes = generateSyringes();
        modSamples = generateSamples();
    }

    private static ArrayList<ModTissue> generateBaseTissues() {
        ArrayList<ModTissue> baseTissues = new ArrayList<ModTissue>();

            baseTissues.add(new ModTissue("Cow", "minecraft:cow", "emergingtechnology:syntheticcowraw"));
            baseTissues.add(new ModTissue("Chicken", "minecraft:chicken", "emergingtechnology:syntheticchickenraw"));
            baseTissues.add(new ModTissue("Pig", "minecraft:pig", "emergingtechnology:syntheticpigraw"));
            baseTissues.add(new ModTissue("Horse", "minecraft:horse", "emergingtechnology:syntheticleather"));
            baseTissues.add(new ModTissue("Spider", "minecraft:spider", "emergingtechnology:syntheticsilk"));
            baseTissues.add(new ModTissue("Slime", "minecraft:slime", "emergingtechnology:syntheticslime"));
            baseTissues.add(new ModTissue("Zombie", "minecraft:zombie", Items.ROTTEN_FLESH.getRegistryName().toString()));

        return baseTissues;
    }

    private static ArrayList<ModTissue> generateCustomTissues() {
        ArrayList<ModTissue> tissues = new ArrayList<ModTissue>();

        if (customTissues != null) {
            for (ModTissue tissue : customTissues) {
                tissues.add(tissue);
            }
        }

        return tissues;
    }

    private static void readFromFile(FMLPreInitializationEvent event) {
        CustomTissueLoader.preInit(event);
    }

    public static ItemStack getSyringeItemStackByEntityId(String entityId) {
        for(SyringeItemBase item: modSyringes) {
            if (entityId.equalsIgnoreCase(item.entityId)) {
                return new ItemStack(item);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getSampleItemStackByEntityId(String entityId) {
        for(SampleItemBase item: modSamples) {
            if (entityId.equalsIgnoreCase(item.entityId)) {
                return new ItemStack(item);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getResultItemStackByEntityId(String entityId) {
        for(ModTissue modTissue: allTissues) {
            if (entityId.equalsIgnoreCase(modTissue.entityId) && modTissue.result != null) {
                Item item = Item.getByNameOrId(modTissue.result);

                if (item == null) {
                    return ItemStack.EMPTY;
                }

                return new ItemStack(item);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ResourceLocation getResultResourceLocationByEntityId(String entityId) {
        for(ModTissue modTissue: allTissues) {
            if (entityId.equalsIgnoreCase(modTissue.entityId) && modTissue.result != null) {
                return new ResourceLocation(modTissue.result);
            }
        }
        return Items.CHICKEN.getRegistryName();
    }

    public static boolean isValidSyntheticEntity(String entityId) {
        for(ModTissue modTissue: allTissues) {
            if (entityId.equalsIgnoreCase(modTissue.entityId)) {
                return true;
            }
        }

        return false;
    }

    private static ArrayList<SyringeItemBase> generateSyringes() {
        ArrayList<SyringeItemBase> modTissueItems = new ArrayList<SyringeItemBase>();

        for (ModTissue modTissue: allTissues) {
            modTissueItems.add(new SyringeItemBase(modTissue.registryName, modTissue.displayName, modTissue.entityId));
        }

        return modTissueItems;
    }

    private static ArrayList<SampleItemBase> generateSamples() {
        ArrayList<SampleItemBase> modTissueItems = new ArrayList<SampleItemBase>();

        for (ModTissue modTissue: allTissues) {
            modTissueItems.add(new SampleItemBase(modTissue.registryName, modTissue.displayName, modTissue.entityId));
        }

        return modTissueItems;
    }
}