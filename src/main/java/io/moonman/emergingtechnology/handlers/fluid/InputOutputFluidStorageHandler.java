// package io.moonman.emergingtechnology.handlers.fluid;

// import net.minecraftforge.fluids.FluidStack;
// import net.minecraftforge.fluids.FluidTank;
// import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
// import net.minecraftforge.fluids.capability.IFluidHandler;
// import net.minecraftforge.fluids.capability.IFluidTankProperties;

// /**
//  * A customisable Input/Output FluidTank used in Emerging Technology
//  */
// public class InputOutputFluidStorageHandler implements IFluidHandler {

//     private final FluidTank inputTank;
//     private final FluidTank outputTank;

//     public InputOutputFluidStorageHandler(FluidTank inputTank, FluidTank outputTank) {
//         this.inputTank = inputTank;
//         this.outputTank = outputTank;
//     }

//     @Override
//     public int fill(FluidStack resource, boolean doFill) {
//         return inputTank.fill(resource, doFill);
//     }

//     @Override
//     public FluidStack drain(FluidStack resource, boolean doDrain) {
//         return outputTank.drain(resource, doDrain);
//     }

//     @Override
//     public FluidStack drain(int maxDrain, boolean doDrain) {
//         return outputTank.drain(maxDrain, doDrain);
//     }

//     @Override
//     public IFluidTankProperties[] getTankProperties() {
//         return new IFluidTankProperties[] {
//             new FluidTankPropertiesWrapper(inputTank),
//             new FluidTankPropertiesWrapper(outputTank)
//         };
//     }

// }