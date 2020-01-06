package io.moonman.emergingtechnology.providers.classes;

public class ModTissue {

    public String displayName;
    public String entityId;
    public String rawMeatName;
    public String cookedMeatName;


    public ModTissue(String displayName, String entityId, String rawMeatName, String cookedMeatName) {
        this.displayName = displayName;
        this.entityId = entityId;
        this.rawMeatName = rawMeatName;
        this.cookedMeatName = cookedMeatName;
    }
}