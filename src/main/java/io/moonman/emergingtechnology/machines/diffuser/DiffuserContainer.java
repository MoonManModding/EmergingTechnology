package io.moonman.emergingtechnology.machines.diffuser;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class DiffuserContainer extends MachineContainer {
	private int energy;
	private int gas;
	private int plants;
	private int nozzle;

	public DiffuserContainer(InventoryPlayer player, DiffuserTileEntity tileEntity) {
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
			checkField(listener, EnumTileField.GAS, this.gas);
			checkField(listener, EnumTileField.PLANTS, this.plants);
			checkField(listener, EnumTileField.NOZZLE, this.nozzle);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.gas = this.tileEntity.getField(EnumTileField.GAS);
		this.plants = this.tileEntity.getField(EnumTileField.PLANTS);
		this.nozzle = this.tileEntity.getField(EnumTileField.NOZZLE);
	}
}