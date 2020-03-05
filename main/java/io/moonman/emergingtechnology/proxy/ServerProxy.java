package io.moonman.emergingtechnology.proxy;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {

    @Override
    public World getClientWorld() {
        throw new IllegalStateException("getClientWorld called on server.");
    }

}