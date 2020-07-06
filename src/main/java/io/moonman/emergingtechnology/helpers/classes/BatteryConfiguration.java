package io.moonman.emergingtechnology.helpers.classes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class BatteryConfiguration {

    private boolean UP = false;
    private boolean DOWN = false;
    private boolean NORTH = true;
    private boolean SOUTH = false;
    private boolean EAST = false;
    private boolean WEST = false;

    public boolean getSideInput(EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return DOWN;
            case EAST:
                return EAST;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case UP:
                return UP;
            case WEST:
                return WEST;
            default:
                return false;
        }
    }

    public int getField(EnumFacing facing) {
        return getSideInput(facing) ? 1 : 0;
    }

    public void setSideInput(EnumFacing facing, int fieldValue) {

        boolean value = fieldValue == 1;

        onSideChanged();

        switch (facing) {
            case DOWN:
                DOWN = value;
                break;
            case EAST:
                EAST = value;
                break;
            case NORTH:
                NORTH = value;
                break;
            case SOUTH:
                SOUTH = value;
                break;
            case UP:
                UP = value;
                break;
            case WEST:
                WEST = value;
                break;
            default:
                break;
        }
    }

    public void readFromNBT(NBTTagCompound compound) {
        checkValue(this.UP, compound.getBoolean("up"));
        checkValue(this.DOWN, compound.getBoolean("down"));
        checkValue(this.NORTH, compound.getBoolean("north"));
        checkValue(this.SOUTH, compound.getBoolean("south"));
        checkValue(this.EAST, compound.getBoolean("east"));
        checkValue(this.WEST, compound.getBoolean("west"));

        this.UP = compound.getBoolean("up");
        this.DOWN = compound.getBoolean("down");
        this.NORTH = compound.getBoolean("north");
        this.SOUTH = compound.getBoolean("south");
        this.EAST = compound.getBoolean("east");
        this.WEST = compound.getBoolean("west");

        System.out.println("Battery NBT read!");
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("up", this.UP);
        compound.setBoolean("down", this.DOWN);
        compound.setBoolean("east", this.EAST);
        compound.setBoolean("west", this.WEST);
        compound.setBoolean("north", this.NORTH);
        compound.setBoolean("south", this.SOUTH);

        System.out.println("Battery NBT written!");
    }

    private void checkValue(boolean previous, boolean next) {

        if (previous != next) {
            onSideChanged();
        }
    }

    protected void onSideChanged() {

    }
}

// Failed to load data for block entity emergingtechnology:battery

// java.lang.NullPointerException: null
// at
// io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase.getState(MachineTileBase.java:24)
// ~[MachineTileBase.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity.updateBlockRender(BatteryTileEntity.java:34)
// ~[BatteryTileEntity.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity.access$000(BatteryTileEntity.java:24)
// ~[BatteryTileEntity.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity$1.onSideChanged(BatteryTileEntity.java:29)
// ~[BatteryTileEntity$1.class:?]
// at
// io.moonman.emergingtechnology.helpers.classes.BatteryConfiguration.checkValue(BatteryConfiguration.java:99)
// ~[BatteryConfiguration.class:?]
// at
// io.moonman.emergingtechnology.helpers.classes.BatteryConfiguration.readFromNBT(BatteryConfiguration.java:69)
// ~[BatteryConfiguration.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity.readFromNBT_OpenComputers(BatteryTileEntity.java:115)
// ~[BatteryTileEntity.class:?]
// at
// li.cil.oc.common.asm.template.StaticSimpleEnvironment.readFromNBT(StaticSimpleEnvironment.java:74)
// ~[OpenComputers-MC1.12.2-1.7.5.212.jar:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity.readFromNBT(BatteryTileEntity.java:52)
// ~[BatteryTileEntity.class:?]
// at net.minecraft.tileentity.TileEntity.create(TileEntity.java:131)
// [TileEntity.class:?]
// at
// net.minecraft.world.chunk.storage.AnvilChunkLoader.loadEntities(AnvilChunkLoader.java:530)
// [AnvilChunkLoader.class:?]
// at
// net.minecraftforge.common.chunkio.ChunkIOProvider.syncCallback(ChunkIOProvider.java:101)
// [ChunkIOProvider.class:?]
// at
// net.minecraftforge.common.chunkio.ChunkIOExecutor.syncChunkLoad(ChunkIOExecutor.java:94)
// [ChunkIOExecutor.class:?]
// at
// net.minecraft.world.gen.ChunkProviderServer.loadChunk(ChunkProviderServer.java:130)
// [ChunkProviderServer.class:?]
// at
// net.minecraft.world.gen.ChunkProviderServer.loadChunk(ChunkProviderServer.java:101)
// [ChunkProviderServer.class:?]
// at
// net.minecraft.world.gen.ChunkProviderServer.provideChunk(ChunkProviderServer.java:147)
// [ChunkProviderServer.class:?]
// at
// net.minecraft.server.MinecraftServer.initialWorldChunkLoad(MinecraftServer.java:383)
// [MinecraftServer.class:?]
// at
// net.minecraft.server.integrated.IntegratedServer.loadAllWorlds(IntegratedServer.java:143)
// [IntegratedServer.class:?]
// at
// net.minecraft.server.integrated.IntegratedServer.init(IntegratedServer.java:160)
// [IntegratedServer.class:?]
// at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:552)
// [MinecraftServer.class:?]
// at java.lang.Thread.run(Thread.java:748) [?:1.8.0_161]
// [10:47:48] [Server thread/ERROR] [FML]: A TileEntity
// emergingtechnology:battery(io.moonman.emergingtechnology.machines.battery.BatteryTileEntity)
// has thrown an exception during loading, its state cannot be restored. Report
// this to the mod author

// java.lang.NullPointerException: null
// at
// io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase.getState(MachineTileBase.java:24)
// ~[MachineTileBase.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity.updateBlockRender(BatteryTileEntity.java:34)
// ~[BatteryTileEntity.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity.access$000(BatteryTileEntity.java:24)
// ~[BatteryTileEntity.class:?]
// at
// io.moonman.emergingtechnology.machines.battery.BatteryTileEntity$1.onSideChanged(BatteryTileEntity.java:29)
// ~[BatteryTileEntity$1.class:?]
// at
// io.moonman.emergingtechnology.helpers.classes.BatteryConfiguration.checkValue(BatteryConfiguration.java:99)
// ~[BatteryConfiguration.class:?]