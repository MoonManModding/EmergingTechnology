package io.moonman.emergingtechnology.machines.collector;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class CollectorContainer extends MachineContainer {
	
	public CollectorContainer(InventoryPlayer player, CollectorTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 5, 5);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 22, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 50, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 78, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 106, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 134, 35));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
}