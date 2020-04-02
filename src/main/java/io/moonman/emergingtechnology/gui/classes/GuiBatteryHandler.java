package io.moonman.emergingtechnology.gui.classes;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.machines.battery.BatteryTileEntity;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import io.moonman.emergingtechnology.network.PacketHandler;
import io.moonman.emergingtechnology.network.gui.BatteryUpdatePacket;
import io.moonman.emergingtechnology.util.Lang;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class GuiBatteryHandler {

    private final BlockPos position;

    private boolean UP;
    private boolean DOWN;
    private boolean NORTH;
    private boolean SOUTH;
    private boolean EAST;
    private boolean WEST;

    public GuiBatteryHandler(BatteryTileEntity tileEntity) {
        this.position = tileEntity.getPos();

        UP = tileEntity.getField(EnumTileField.BATTERYUP) == 1;
        DOWN = tileEntity.getField(EnumTileField.BATTERYDOWN) == 1;
        NORTH = tileEntity.getField(EnumTileField.BATTERYNORTH) == 1;
        SOUTH = tileEntity.getField(EnumTileField.BATTERYSOUTH) == 1;
        EAST = tileEntity.getField(EnumTileField.BATTERYEAST) == 1;
        WEST = tileEntity.getField(EnumTileField.BATTERYWEST) == 1;
    }

    public void updateValue(int buttonId) {
        switch (buttonId) {
            case 0:
                tryUpdate(EnumTileField.BATTERYUP, false);
                UP = false;
                break;
            case 1:
                tryUpdate(EnumTileField.BATTERYUP, true);
                UP = true;
                break;
            case 2:
                tryUpdate(EnumTileField.BATTERYDOWN, false);
                DOWN = false;
                break;
            case 3:
                tryUpdate(EnumTileField.BATTERYDOWN, true);
                DOWN = true;
                break;
            case 4:
                tryUpdate(EnumTileField.BATTERYNORTH, false);
                NORTH = false;
                break;
            case 5:
                tryUpdate(EnumTileField.BATTERYNORTH, true);
                NORTH = true;
                break;
            case 6:
                tryUpdate(EnumTileField.BATTERYSOUTH, false);
                SOUTH = false;
                break;
            case 7:
                tryUpdate(EnumTileField.BATTERYSOUTH, true);
                SOUTH = true;
                break;
            case 8:
                tryUpdate(EnumTileField.BATTERYEAST, false);
                EAST = false;
                break;
            case 9:
                tryUpdate(EnumTileField.BATTERYEAST, true);
                EAST = true;
                break;
            case 10:
                tryUpdate(EnumTileField.BATTERYWEST, false);
                WEST = false;
                break;
            case 11:
                tryUpdate(EnumTileField.BATTERYWEST, true);
                WEST = true;
                break;
            default:
                break;
        }

    }

    private void tryUpdate(EnumTileField field, boolean input) {
        sendPacket(field, input);
    }

    public boolean shouldRenderButton(int buttonId) {
        switch (buttonId) {
            case 0:
                return UP;
            case 1:
                return !UP;
            case 2:
                return DOWN;
            case 3:
                return !DOWN;
            case 4:
                return NORTH;
            case 5:
                return !NORTH;
            case 6:
                return SOUTH;
            case 7:
                return !SOUTH;
            case 8:
                return EAST;
            case 9:
                return !EAST;
            case 10:
                return WEST;
            case 11:
                return !WEST;
            default:
                return false;
        }
    }

    public List<String> getTooltip(int buttonId) {
        List<String> tooltips = new ArrayList<String>();

        switch (buttonId) {
            case 0:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.UP.toString(), false));
                break;
            case 1:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.UP.toString(), true));
                break;
            case 2:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.DOWN.toString(), false));
                break;
            case 3:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.DOWN.toString(), true));
                break;
            case 4:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.NORTH.toString(), false));
                break;
            case 5:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.NORTH.toString(), true));
                break;
            case 6:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.SOUTH.toString(), false));
                break;
            case 7:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.SOUTH.toString(), true));
                break;
            case 8:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.EAST.toString(), false));
                break;
            case 9:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.EAST.toString(), true));
                break;
            case 10:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.WEST.toString(), false));
                break;
            case 11:
                tooltips.add(Lang.getBatteryLabel(EnumFacing.WEST.toString(), true));
                break;
            default:
                tooltips.add("ERROR");
                break;
        }

        return tooltips;
    }

    private void sendPacket(EnumTileField field, boolean value) {
        PacketHandler.INSTANCE
                .sendToServer(new BatteryUpdatePacket(position, EnumTileField.getId(field), value ? 1 : 0));
    }
}