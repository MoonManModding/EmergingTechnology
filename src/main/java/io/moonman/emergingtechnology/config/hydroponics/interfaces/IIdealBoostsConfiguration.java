package io.moonman.emergingtechnology.config.hydroponics.interfaces;

import io.moonman.emergingtechnology.config.hydroponics.enums.CropTypeEnum;

public interface IIdealBoostsConfiguration {
    public int getBoost(CropTypeEnum cropType);
}