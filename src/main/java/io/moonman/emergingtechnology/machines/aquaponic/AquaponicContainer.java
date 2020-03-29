package io.moonman.emergingtechnology.machines.aquaponic;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class AquaponicContainer extends MachineContainer {
	private int energy;
	private int water;
	private int progress;
	private int nutrient;

	public AquaponicContainer(InventoryPlayer player, AquaponicTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 12, 12);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {

		int START_X = 13;
		int START_Y = 26;
		int counter = 0;

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				addSlotToContainer(new SlotItemHandler(handler, counter, START_X + (24 * x), START_Y + (24 * y)));
				counter++;
			}
		}

		int OUT_START_X = 98;
		int OUT_START_Y = 67;

		for (int x = 0; x < 3; x++) {
			addSlotToContainer(new SlotItemHandler(handler, counter, OUT_START_X + (24 * x), OUT_START_Y));
			counter++;
		}
	}

	@Override
	public void addPlayerSlots(InventoryPlayer player) {

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 124 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 182));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {

			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			checkField(listener, EnumTileField.ENERGY, this.energy);
			checkField(listener, EnumTileField.FLUID, this.water);
			checkField(listener, EnumTileField.PROGRESS, this.progress);
			checkField(listener, EnumTileField.NUTRIENT, this.nutrient);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.water = this.tileEntity.getField(EnumTileField.FLUID);
		this.progress = this.tileEntity.getField(EnumTileField.PROGRESS);
		this.nutrient = this.tileEntity.getField(EnumTileField.NUTRIENT);
	}
}