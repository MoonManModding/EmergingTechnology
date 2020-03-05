package io.moonman.emergingtechnology.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    @Override
    public World getClientWorld() {

        Minecraft minecraft = Minecraft.getInstance();

        return minecraft.world;
    }

}