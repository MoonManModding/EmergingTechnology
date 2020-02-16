package io.moonman.emergingtechnology.fluid.fluidblocks;

import io.moonman.emergingtechnology.fluid.BlockFluidBase;
import io.moonman.emergingtechnology.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NutrientBlock extends BlockFluidBase {

    private static final String _name = "nutrientblock";

    public NutrientBlock() {
        super(_name, ModFluids.NUTRIENT, Material.WATER);
    }
}