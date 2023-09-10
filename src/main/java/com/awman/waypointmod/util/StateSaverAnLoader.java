package com.awman.waypointmod.util;

import com.awman.waypointmod.WaypointMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class StateSaverAnLoader extends PersistentState {
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return nbt;
    }

    public static StateSaverAnLoader createFromNbt(NbtCompound tag) {
        StateSaverAnLoader state = new StateSaverAnLoader();
        return state;
    }

    public static StateSaverAnLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAnLoader state = persistentStateManager.getOrCreate(
                StateSaverAnLoader::createFromNbt,
                StateSaverAnLoader::new,
                WaypointMod.MOD_ID
        );

        // So that the data will be saved when closing
        state.markDirty();

        return state;
    }
}
