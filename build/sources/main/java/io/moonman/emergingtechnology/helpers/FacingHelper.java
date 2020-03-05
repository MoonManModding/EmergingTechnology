package io.moonman.emergingtechnology.helpers;

import net.minecraft.util.EnumFacing;

/**
 * Provides useful methods for handling horizontal EnumFacings
 */
public class FacingHelper {

    public static int getFacingIdFromEnumFacings(EnumFacing facing, EnumFacing relativeFacing) {
        return getIdFromFacing(relativeFacing);
    }

    public static int getIdFromFacing(EnumFacing facing) {
        switch(facing) {
            case NORTH:
                return 1;
            case EAST:
                return 2;
            case SOUTH:
                return 3;
            case WEST:
                return 4;
            default:
                return 0;
        }
    }

    public static EnumFacing getFacingFromId(int id) {
        switch(id) {
            case 1:
                return EnumFacing.NORTH;
            case 2:
                return EnumFacing.EAST;
            case 3:
                return EnumFacing.SOUTH;
            case 4:
                return EnumFacing.WEST;
            default:
                return EnumFacing.UP;
        }
    }
}