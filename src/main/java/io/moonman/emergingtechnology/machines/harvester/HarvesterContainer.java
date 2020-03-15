package io.moonman.emergingtechnology.machines.harvester;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class HarvesterContainer extends MachineContainer {
	private int energy;

	public HarvesterContainer(InventoryPlayer player, HarvesterTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 1, 1);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {
		addSlotToContainer(new SlotItemHandler(handler, 0, 17, 35));
		addSlotToContainer(new SlotItemHandler(handler, 1, 52, 35));
		addSlotToContainer(new SlotItemHandler(handler, 2, 80, 35));
		addSlotToContainer(new SlotItemHandler(handler, 3, 108, 35));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {

			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			checkField(listener, EnumTileField.ENERGY, this.energy);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
	}
}