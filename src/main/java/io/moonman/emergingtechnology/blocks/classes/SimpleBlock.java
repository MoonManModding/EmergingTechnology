package io.moonman.emergingtechnology.blocks.classes;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class SimpleBlock extends Block {

    public SimpleBlock(String name, Material material, SoundType sound, float hardness) {
        super(Properties.create(material).sound(sound).hardnessAndResistance(hardness));
        setRegistryName(name);
    }

    public SimpleBlock(String name, Material material, SoundType sound) {
        super(Properties.create(material).sound(sound).hardnessAndResistance(0.5f));
        setRegistryName(name);
    }
}