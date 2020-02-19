package io.moonman.emergingtechnology.helpers.machines.enums;

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

    private static void checkValues() {
        if (RotationEnum.values == null) {
            RotationEnum.values = RotationEnum.values();
        }
    }
}