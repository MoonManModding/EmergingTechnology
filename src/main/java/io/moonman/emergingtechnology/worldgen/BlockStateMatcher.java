package io.moonman.emergingtechnology.worldgen;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockStateMatcher implements Predicate<IBlockState>
{
    private final Block block;

    private BlockStateMatcher(Block blockType)
    {
        this.block = blockType;
    }

    public static BlockStateMatcher forBlock(Block blockType)
    {
        return new BlockStateMatcher(blockType);
    }

    public boolean apply(@Nullable IBlockState state)
    {
        return state != null && state.getBlock().getStateFromMeta(0) == this.block.getStateFromMeta(0);
    }
}