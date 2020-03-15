package io.moonman.emergingtechnology.machines.battery;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;

// Handles inventory and slots
public class BatteryContainer extends MachineContainer {
	private int energy;
	private int totalInput;
	private int totalOutput;

	public BatteryContainer(InventoryPlayer player, BatteryTileEntity tileEntity) {
		super(tileEntity, player, null, 2, 1);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {

			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			checkField(listener, EnumTileField.ENERGY, this.energy);
			checkField(listener, EnumTileField.BATTERYINPUT, this.totalInput);
			checkField(listener, EnumTileField.BATTERYOUTPUT, this.totalOutput);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.totalInput = this.tileEntity.getField(EnumTileField.BATTERYINPUT);
		this.totalOutput = this.tileEntity.getField(EnumTileField.BATTERYOUTPUT);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}