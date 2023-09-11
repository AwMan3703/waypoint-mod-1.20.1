package com.awman.waypointmod.util;

import com.awman.waypointmod.WaypointMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;

public class StateSaverAnLoader extends PersistentState {

    public WaypointMap waypointMap;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put(WaypointMap.NBT_STORAGE_KEY, waypointMap.toNbt());
        return nbt;
    }

    public static StateSaverAnLoader createFromNbt(NbtCompound nbt) {
        StateSaverAnLoader state = new StateSaverAnLoader();
        state.waypointMap = WaypointMap.fromNbt(nbt);
        return state;
    }

    public static StateSaverAnLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getOverworld().getPersistentStateManager();

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
