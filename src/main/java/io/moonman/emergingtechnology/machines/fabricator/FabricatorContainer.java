package io.moonman.emergingtechnology.machines.fabricator;

import io.moonman.emergingtechnology.machines.classes.container.MachineContainer;
import io.moonman.emergingtechnology.machines.classes.tile.EnumTileField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Handles inventory and slots
public class FabricatorContainer extends MachineContainer {

	private int energy;
	private int progress;
	private int selection;
	private int printing;
	private int status;
	
	public FabricatorContainer(InventoryPlayer player, FabricatorTileEntity tileEntity) {
		super(tileEntity, player, tileEntity.itemHandler, 2, 1);
	}

	@Override
	public void addMachineSlots(IItemHandler handler) {
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 17, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 123, 35));

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
			checkField(listener, EnumTileField.PROGRESS, this.progress);
			checkField(listener, EnumTileField.FABRICATORSELECTION, this.selection);
			checkField(listener, EnumTileField.FABRICATORISPRINTING, this.printing);
			checkField(listener, EnumTileField.FABRICATORSTATUS, this.status);
		}

		this.energy = this.tileEntity.getField(EnumTileField.ENERGY);
		this.progress = this.tileEntity.getField(EnumTileField.PROGRESS);
		this.selection = this.tileEntity.getField(EnumTileField.FABRICATORSELECTION);
		this.printing = this.tileEntity.getField(EnumTileField.FABRICATORISPRINTING);
		this.status = this.tileEntity.getField(EnumTileField.FABRICATORSTATUS);
	}
}