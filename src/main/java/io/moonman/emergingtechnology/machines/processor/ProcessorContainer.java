package io.moonman.emergingtechnology.machines.processor;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class ProcessorContainer extends MachineContainer {
	
	private int energy;
	private int water;
	private int progress;
	private int maxProgress;

	public ProcessorContainer(InventoryPlayer player, ProcessorTileEntity tileEntity) {
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

			checkField(listener, EnumTileField.ENERGY, this.energy);
			checkField(listener, EnumTileField.FLUID, this.water);
			checkField(listener, EnumTileField.PROGRESS, this.progress);
			checkField(listener, EnumTileField.MAXPROGRESS, this.maxProgress);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.water = this.tileEntity.getField(EnumTileField.FLUID);
		this.progress = this.tileEntity.getField(EnumTileField.PROGRESS);
		this.maxProgress = this.tileEntity.getField(EnumTileField.MAXPROGRESS);
	}
}