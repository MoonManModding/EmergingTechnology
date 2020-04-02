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
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("up", this.UP);
        compound.setBoolean("down", this.DOWN);
        compound.setBoolean("east", this.EAST);
        compound.setBoolean("west", this.WEST);
        compound.setBoolean("north", this.NORTH);
        compound.setBoolean("south", this.SOUTH);
    }

    private void checkValue(boolean previous, boolean next) {
        if (previous != next) {
            onSideChanged();
        }
    }

    protected void onSideChanged() {

    }
}