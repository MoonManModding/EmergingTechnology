package io.moonman.emergingtechnology.helpers.machines.enums;

public enum TurbineSpeedEnum {
    OFF,
    SLOW,
    FAST;

    private static TurbineSpeedEnum[] values = null;

    public static TurbineSpeedEnum getById(int id) {
        checkValues();
        return TurbineSpeedEnum.values[id];
    }

    public static int getId(TurbineSpeedEnum speed) {
        checkValues();
        
        for (int i = 0; i < values.length; i++) {
            if (values[i] == speed) {
                return i;
            }
        }

        return 0;
    }

    private static void checkValues() {
        if(TurbineSpeedEnum.values == null) {
            TurbineSpeedEnum.values = TurbineSpeedEnum.values();
        }
    }
}