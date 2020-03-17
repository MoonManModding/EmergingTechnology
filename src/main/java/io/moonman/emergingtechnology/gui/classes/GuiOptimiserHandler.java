package io.moonman.emergingtechnology.gui.classes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.machines.optimiser.OptimiserTileEntity;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.gui.OptimiserUpdatePacket;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.util.math.BlockPos;

public class GuiOptimiserHandler {

    private final BlockPos position;

    private final int MAX_VALUE = 5;

    private int maxAvailablePoints = 0;

    public int energy;
    public int fluid;
    public int gas;
    public int progress;

    public GuiOptimiserHandler(OptimiserTileEntity tileEntity) {
        this.position = tileEntity.getPos();

        energy = tileEntity.getField(EnumTileField.OPTIMISERENERGY);
        fluid = tileEntity.getField(EnumTileField.OPTIMISERFLUID);
        gas = tileEntity.getField(EnumTileField.OPTIMISERGAS);
        progress = tileEntity.getField(EnumTileField.OPTIMISERPROGRESS);
    }

    public void updateValue(int buttonId) {
        switch (buttonId) {
            case 0:
                tryUpdate(EnumTileField.OPTIMISERFLUID, false);
                break;
            case 1:
                tryUpdate(EnumTileField.OPTIMISERFLUID, true);
                break;
            case 2:
                tryUpdate(EnumTileField.OPTIMISERENERGY, false);
                break;
            case 3:
                tryUpdate(EnumTileField.OPTIMISERENERGY, true);
                break;
            case 4:
                tryUpdate(EnumTileField.OPTIMISERGAS, false);
                break;
            case 5:
                tryUpdate(EnumTileField.OPTIMISERGAS, true);
                break;
            case 6:
                tryUpdate(EnumTileField.OPTIMISERPROGRESS, false);
                break;
            case 7:
                tryUpdate(EnumTileField.OPTIMISERPROGRESS, true);
                break;
            default:
                break;
        }

    }

    private void tryUpdate(EnumTileField field, boolean add) {

        if (add && !hasRemainingPoints()) {
            return;
        }

        int value = add ? 1 : -1;

        switch (field) {
            case OPTIMISERENERGY:
                if (inRange(energy + value)) {
                    energy += value;
                    sendPacket(EnumTileField.OPTIMISERENERGY, energy);
                }
                break;
            case OPTIMISERFLUID:
                if (inRange(fluid + value)) {
                    fluid += value;
                    sendPacket(EnumTileField.OPTIMISERFLUID, fluid);
                }
                break;
            case OPTIMISERGAS:
                if (inRange(gas + value)) {
                    gas += value;
                    sendPacket(EnumTileField.OPTIMISERGAS, gas);
                }
                break;
            case OPTIMISERPROGRESS:
                if (inRange(progress + value)) {
                    progress += value;
                    sendPacket(EnumTileField.OPTIMISERPROGRESS, progress);
                }
                break;
            default:
                break;
        }
    }

    private boolean inRange(int value) {
        return (value >= 0 || value <= MAX_VALUE);
    }

    public boolean shouldRenderButton(int buttonId) {
        switch (buttonId) {
            case 0:
                return fluid > 0;
            case 1:
                return fluid < MAX_VALUE && hasRemainingPoints();
            case 2:
                return energy > 0;
            case 3:
                return energy < MAX_VALUE && hasRemainingPoints();
            case 4:
                return gas > 0;
            case 5:
                return gas < MAX_VALUE && hasRemainingPoints();
            case 6:
                return progress > 0;
            case 7:
                return progress < MAX_VALUE && hasRemainingPoints();
            default:
                return false;
        }
    }

    public void resetAllPoints() {
        progress = 0;
        energy = 0;
        fluid = 0;
        gas = 0;

        sendPacket(EnumTileField.OPTIMISERFLUID, fluid);
        sendPacket(EnumTileField.OPTIMISERENERGY, energy);
        sendPacket(EnumTileField.OPTIMISERGAS, gas);
        sendPacket(EnumTileField.OPTIMISERPROGRESS, progress);
    }

    public void setMaxAvailablePoints(int points) {

        if (this.maxAvailablePoints != points && this.maxAvailablePoints != 0) {
            resetAllPoints();
        }

        this.maxAvailablePoints = points;
    }

    public int getMaxAvailablePoints() {
        return this.maxAvailablePoints;
    }

    public int getRemainingPoints() {
        return getMaxAvailablePoints() - getUsedPoints();
    }

    public boolean hasRemainingPoints() {
        return getRemainingPoints() > 0;
    }

    public List<String> getAddTooltips() {
        List<String> tooltips = new ArrayList<String>();

        tooltips.add(Lang.get(Lang.GUI_OPTIMISER_BUTTON_ADD));
        tooltips.add(Lang.getOptimiserRemaining(getRemainingPoints()));

        return tooltips;
    }

    public List<String> getRemoveTooltips() {
        List<String> tooltips = new ArrayList<String>();

        tooltips.add(Lang.get(Lang.GUI_OPTIMISER_BUTTON_REMOVE));
        tooltips.add(Lang.getOptimiserRemaining(getRemainingPoints()));

        return tooltips;
    }
    
    public List<String> getBoostIndicator(EnumTileField field) {
        List<String> tooltips = new ArrayList<String>();

        int value = 0;

        switch(field) {
            case OPTIMISERENERGY: value = energy; break;
            case OPTIMISERFLUID: value = fluid; break;
            case OPTIMISERGAS: value = gas; break;
            default: value = 0; break;
        }

        tooltips.add(Lang.getOptimiserResourceBoost(value * 10));

        return tooltips;
    }
    
    public List<String> getProgressIndicator() {
        List<String> tooltips = new ArrayList<String>();

        tooltips.add(Lang.getOptimiserProgressBoost(progress * 10));

        return tooltips;
    }

    private int getUsedPoints() {
        return this.fluid + this.energy + this.gas + this.progress;
    }

    private void sendPacket(EnumTileField field, int value) {
        PacketHandler.INSTANCE.sendToServer(new OptimiserUpdatePacket(position, EnumTileField.getId(field), value));
    }
}