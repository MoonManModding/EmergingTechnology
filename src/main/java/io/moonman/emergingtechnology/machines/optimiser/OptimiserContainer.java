package io.moonman.emergingtechnology.machines.optimiser;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class OptimiserContainer extends MachineContainer {
	private int energy;
	private int water;

	public OptimiserContainer(InventoryPlayer player, OptimiserTileEntity tileEntity) {
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

			checkField(listener, EnumTileField.ENERGY, this.energy);
			checkField(listener, EnumTileField.FLUID, this.water);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.water = this.tileEntity.getField(EnumTileField.FLUID);
	}
}