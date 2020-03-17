package io.moonman.emergingtechnology.machines.classes.tile;

public enum EnumTileField {
    ENERGY,
    MAXENERGY,
    FLUID,
    MAXFLUID,
    GAS,
    MAXGAS,
    PROGRESS,
    MAXPROGRESS,
    HEAT,
    MAXHEAT,
    BULB,
    MEDIUM,
    BATTERYINPUT,
    BATTERYOUTPUT,
    FABRICATORSELECTION,
    FABRICATORISPRINTING,
    FABRICATORSTATUS,
    PLANTS,
    NOZZLE,
    LIGHTBOOST,
    NUTRIENT,
    OPTIMISERENERGY,
    OPTIMISERFLUID,
    OPTIMISERGAS,
    OPTIMISERPROGRESS,
    OPTIMISERCORES;

    private static EnumTileField[] values = null;

    public static EnumTileField getById(int id) {
        checkValues();
        return EnumTileField.values[id];
    }

    public static int getId(EnumTileField status) {
        checkValues();
        
        for (int i = 0; i < values.length; i++) {
            if (values[i] == status) {
                return i;
            }
        }

        return 0;
    }

    private static void checkValues() {
        if(EnumTileField.values == null) {
            EnumTileField.values = EnumTileField.values();
        }
    }
}