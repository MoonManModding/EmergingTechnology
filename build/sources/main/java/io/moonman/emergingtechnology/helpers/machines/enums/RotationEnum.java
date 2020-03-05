package io.moonman.emergingtechnology.helpers.machines.enums;

import net.minecraft.util.EnumFacing;

public enum RotationEnum {
    NORTH, EAST, SOUTH, WEST;

    private static RotationEnum[] values = null;

    public static RotationEnum getById(int id) {
        checkValues();
        return RotationEnum.values[id];
    }

    public static int getId(RotationEnum rotation) {
        checkValues();

        for (int i = 0; i < values.length; i++) {
            if (values[i] == rotation) {
                return i;
            }
        }

        return 0;
    }

    public static RotationEnum getRotationFromFacing(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return RotationEnum.EAST;
            case NORTH:
                return RotationEnum.NORTH;
            case SOUTH:
                return RotationEnum.SOUTH;
            case WEST:
                return RotationEnum.WEST;
            default:
                return RotationEnum.NORTH;

        }
    }
    
    public static String getRotationFromEnum(RotationEnum rotation) {
        switch (rotation) {
            case EAST:
                return "east";
            case NORTH:
                return "north";
            case SOUTH:
                return "south";
            case WEST:
                return "west";
            default:
                return "north";
        }
    }

    private static void checkValues() {
        if (RotationEnum.values == null) {
            RotationEnum.values = RotationEnum.values();
        }
    }
}