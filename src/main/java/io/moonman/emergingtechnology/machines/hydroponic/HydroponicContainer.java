package io.moonman.emergingtechnology.machines.hydroponic;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class HydroponicContainer extends MachineContainer {
	private int water;
	private int medium;

	public HydroponicContainer(InventoryPlayer player, HydroponicTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 1, 1);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {
		addSlotToContainer(new SlotItemHandler(handler, 0, 17, 35));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {

			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			checkField(listener, EnumTileField.FLUID, this.water);
			checkField(listener, EnumTileField.MEDIUM, this.medium);
		}

		this.water = this.tileEntity.getField(EnumTileField.FLUID);
		this.medium = this.tileEntity.getField(EnumTileField.MEDIUM);
	}
}