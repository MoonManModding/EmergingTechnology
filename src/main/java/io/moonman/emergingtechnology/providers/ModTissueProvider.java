package io.moonman.emergingtechnology.providers;

import java.util.ArrayList;

import io.moonman.emergingtechnology.item.synthetics.CookedMeatItemBase;
import io.moonman.emergingtechnology.item.synthetics.RawMeatItemBase;
import io.moonman.emergingtechnology.item.synthetics.SampleItemBase;
import io.moonman.emergingtechnology.item.synthetics.SyringeItemBase;
import io.moonman.emergingtechnology.providers.classes.ModTissue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModTissueProvider {

    public static ModTissue[] modTissues;

    public static ArrayList<SyringeItemBase> modSyringes = new ArrayList<SyringeItemBase>();
    public static ArrayList<SampleItemBase> modSamples = new ArrayList<SampleItemBase>();
    public static ArrayList<RawMeatItemBase> modRawMeats = new ArrayList<RawMeatItemBase>();
    public static ArrayList<CookedMeatItemBase> modCookedMeats = new ArrayList<CookedMeatItemBase>();

    public static void preInit(FMLPreInitializationEvent event) {

        modTissues = new ModTissue[]{
            new ModTissue("Cow", "minecraft:cow", Items.BEEF.getRegistryName().toString(), Items.COOKED_BEEF.getRegistryName().toString()),
            new ModTissue("Chicken", "minecraft:chicken", Items.CHICKEN.getRegistryName().toString(), Items.COOKED_CHICKEN.getRegistryName().toString()),
            new ModTissue("Horse", "minecraft:horse", Items.LEATHER.getRegistryName().toString(), null),
            new ModTissue("Pig", "minecraft:pig", Items.PORKCHOP.getRegistryName().toString(), Items.COOKED_PORKCHOP.getRegistryName().toString()),
            new ModTissue("Zombie", "minecraft:zombie", null, null)
        };

        modSyringes = generateSyringes();
        modSamples = generateSamples();
        modRawMeats = generateRawMeats();
        modCookedMeats = generateCookedMeats();

        readFromFile(event);
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

    public static ItemStack getRawMeatItemStackByEntityId(String entityId) {
        for(RawMeatItemBase item: modRawMeats) {
            if (entityId.equalsIgnoreCase(item.entityId)) {
                return new ItemStack(item);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getCookedMeatItemStackByEntityId(String entityId) {
        for(CookedMeatItemBase item: modCookedMeats) {
            if (entityId.equalsIgnoreCase(item.entityId)) {
                return new ItemStack(item);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ResourceLocation getRawMeatResourceLocationByEntityId(String entityId) {
        for(ModTissue modTissue: modTissues) {
            if (entityId.equalsIgnoreCase(modTissue.entityId) && modTissue.rawMeatName != null) {
                return new ResourceLocation(modTissue.rawMeatName);
            }
        }
        return Items.CHICKEN.getRegistryName();
    }

    public static ResourceLocation getCookedMeatResourceLocationByEntityId(String entityId) {
        for(ModTissue modTissue: modTissues) {
            if (entityId.equalsIgnoreCase(modTissue.entityId) && modTissue.cookedMeatName != null) {
                return new ResourceLocation(modTissue.cookedMeatName);
            }
        }
        return Items.COOKED_CHICKEN.getRegistryName();
    }

    public static boolean isValidSyntheticEntity(String entityId) {
        for(ModTissue modTissue: modTissues) {
            if (entityId.equalsIgnoreCase(modTissue.entityId)) {
                return true;
            }
        }

        return false;
    }

    private static ArrayList<SyringeItemBase> generateSyringes() {
        ArrayList<SyringeItemBase> modTissueItems = new ArrayList<SyringeItemBase>();

        for (ModTissue modTissue: modTissues) {
            modTissueItems.add(new SyringeItemBase(modTissue.displayName, modTissue.entityId));
        }

        return modTissueItems;
    }

    private static ArrayList<SampleItemBase> generateSamples() {
        ArrayList<SampleItemBase> modTissueItems = new ArrayList<SampleItemBase>();

        for (ModTissue modTissue: modTissues) {
            modTissueItems.add(new SampleItemBase(modTissue.displayName, modTissue.entityId));
        }

        return modTissueItems;
    }

    private static ArrayList<RawMeatItemBase> generateRawMeats() {
        ArrayList<RawMeatItemBase> modTissueItems = new ArrayList<RawMeatItemBase>();

        for (ModTissue modTissue: modTissues) {
            if (modTissue.rawMeatName == null) {
                modTissueItems.add(new RawMeatItemBase(modTissue.displayName, modTissue.entityId));
            }
        }

        return modTissueItems;
    }

    private static ArrayList<CookedMeatItemBase> generateCookedMeats() {
        ArrayList<CookedMeatItemBase> modTissueItems = new ArrayList<CookedMeatItemBase>();

        for (ModTissue modTissue: modTissues) {
            if (modTissue.cookedMeatName == null) {
                modTissueItems.add(new CookedMeatItemBase(modTissue.displayName, modTissue.entityId));
            }
        }

        return modTissueItems;
    }

    private static void readFromFile(FMLPreInitializationEvent event) {
        // TODO: Implement this
    }
}