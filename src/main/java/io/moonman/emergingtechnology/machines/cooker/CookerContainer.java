package io.moonman.emergingtechnology.machines.cooker;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class CookerContainer extends MachineContainer {
	private int heat;
	private int progress;
	private int maxProgress;

	public CookerContainer(InventoryPlayer player, CookerTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 2, 1);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {
		addSlotToContainer(new SlotItemHandler(handler, 0, 17, 35));
		addSlotToContainer(new SlotItemHandler(handler, 1, 80, 35));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {

			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			checkField(listener, EnumTileField.HEAT, this.heat);
			checkField(listener, EnumTileField.PROGRESS, this.progress);
			checkField(listener, EnumTileField.MAXPROGRESS, this.maxProgress);
		}

		this.heat = this.tileEntity.getField(EnumTileField.HEAT);
		this.progress = this.tileEntity.getField(EnumTileField.PROGRESS);
		this.maxProgress = this.tileEntity.getField(EnumTileField.MAXPROGRESS);
	}
}