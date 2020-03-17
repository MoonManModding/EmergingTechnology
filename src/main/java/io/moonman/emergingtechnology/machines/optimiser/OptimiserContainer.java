package io.moonman.emergingtechnology.machines.optimiser;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class OptimiserContainer extends MachineContainer {
	private int energy;
	private int water;
	private int cores;

	private int packetEnergy;
	private int packetFluid;
	private int packetGas;
	private int packetProgress;

	public OptimiserContainer(InventoryPlayer player, OptimiserTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 1, 1);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {
		addSlotToContainer(new SlotItemHandler(handler, 0, 17, 35));
	}

	@Override
	public void addPlayerSlots(InventoryPlayer player) {
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 104 + y * 18));
			}
		}

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
			checkField(listener, EnumTileField.FLUID, this.water);
			checkField(listener, EnumTileField.OPTIMISERENERGY, this.packetEnergy);
			checkField(listener, EnumTileField.OPTIMISERFLUID, this.packetFluid);
			checkField(listener, EnumTileField.OPTIMISERGAS, this.packetGas);
			checkField(listener, EnumTileField.OPTIMISERPROGRESS, this.packetProgress);
			checkField(listener, EnumTileField.OPTIMISERCORES, this.cores);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.water = this.tileEntity.getField(EnumTileField.FLUID);
		this.cores = this.tileEntity.getField(EnumTileField.OPTIMISERCORES);

		this.packetEnergy = this.tileEntity.getField(EnumTileField.OPTIMISERENERGY);
		this.packetFluid = this.tileEntity.getField(EnumTileField.OPTIMISERFLUID);
		this.packetGas = this.tileEntity.getField(EnumTileField.OPTIMISERGAS);
		this.packetProgress = this.tileEntity.getField(EnumTileField.OPTIMISERPROGRESS);
	}
}