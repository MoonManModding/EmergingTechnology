package io.moonman.emergingtechnology.helpers.machines.enums;

public enum FabricatorStatusEnum {
    ERROR,
    IDLE,
    OUTPUT_FULL,
    INVALID_OUTPUT,
    INVALID_INPUT,
    INSUFFICIENT_INPUT,
    INSUFFICIENT_ENERGY,
    RUNNING;

    private static FabricatorStatusEnum[] values = null;

    public static FabricatorStatusEnum getById(int id) {
        checkValues();
        return FabricatorStatusEnum.values[id];
    }

    public static int getId(FabricatorStatusEnum status) {
        checkValues();
        
        for (int i = 0; i < values.length; i++) {
            if (values[i] == status) {
                return i;
            }
        }

        return 0;
    }

    private static void checkValues() {
        if(FabricatorStatusEnum.values == null) {
            FabricatorStatusEnum.values = FabricatorStatusEnum.values();
        }
    }
}