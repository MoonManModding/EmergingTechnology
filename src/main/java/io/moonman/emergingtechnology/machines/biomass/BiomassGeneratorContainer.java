package io.moonman.emergingtechnology.machines.biomass;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BiomassGeneratorContainer extends MachineContainer {
	private int energy;
	private int progress;

	public BiomassGeneratorContainer(InventoryPlayer player, BiomassGeneratorTileEntity tileEntity) {
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
			checkField(listener, EnumTileField.PROGRESS, this.progress);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.progress = this.tileEntity.getField(EnumTileField.PROGRESS);
	}
}