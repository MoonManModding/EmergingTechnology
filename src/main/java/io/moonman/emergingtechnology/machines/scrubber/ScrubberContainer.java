package io.moonman.emergingtechnology.machines.scrubber;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class ScrubberContainer extends MachineContainer {
	private int water;
	private int energy;
	private int progress;
	private int gas;

	public ScrubberContainer(InventoryPlayer player, ScrubberTileEntity tileEntity) {
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
			checkField(listener, EnumTileField.GAS, this.gas);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.progress = this.tileEntity.getField(EnumTileField.PROGRESS);
		this.water = this.tileEntity.getField(EnumTileField.FLUID);
		this.gas = this.tileEntity.getField(EnumTileField.GAS);
	}
}