package io.moonman.emergingtechnology.machines.battery;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

// Handles inventory and slots
public class BatteryContainer extends MachineContainer {
	private int energy;
	private int totalInput;
	private int totalOutput;

	private int up;
	private int down;
	private int north;
	private int south;
	private int east;
	private int west;

	public BatteryContainer(InventoryPlayer player, BatteryTileEntity tileEntity) {
		super(tileEntity, player, null, 2, 1);
	}

	@Override
	public void addPlayerSlots(InventoryPlayer player) {
		
		// for (int y = 0; y < 3; y++) {
		// 	for (int x = 0; x < 9; x++) {
		// 		this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 104 + y * 18));
		// 	}
		// }

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 162));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {

			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			checkField(listener, EnumTileField.ENERGY, this.energy);
			checkField(listener, EnumTileField.BATTERYINPUT, this.totalInput);
			checkField(listener, EnumTileField.BATTERYOUTPUT, this.totalOutput);

			checkField(listener, EnumTileField.BATTERYUP, this.up);
			checkField(listener, EnumTileField.BATTERYDOWN, this.down);
			checkField(listener, EnumTileField.BATTERYNORTH, this.north);
			checkField(listener, EnumTileField.BATTERYSOUTH, this.south);
			checkField(listener, EnumTileField.BATTERYEAST, this.east);
			checkField(listener, EnumTileField.BATTERYWEST, this.west);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.totalInput = this.tileEntity.getField(EnumTileField.BATTERYINPUT);
		this.totalOutput = this.tileEntity.getField(EnumTileField.BATTERYOUTPUT);

		this.up = this.tileEntity.getField(EnumTileField.BATTERYUP);
		this.down = this.tileEntity.getField(EnumTileField.BATTERYDOWN);
		this.north = this.tileEntity.getField(EnumTileField.BATTERYNORTH);
		this.south = this.tileEntity.getField(EnumTileField.BATTERYSOUTH);
		this.east = this.tileEntity.getField(EnumTileField.BATTERYEAST);
		this.west = this.tileEntity.getField(EnumTileField.BATTERYWEST);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}