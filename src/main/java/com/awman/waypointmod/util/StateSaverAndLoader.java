package com.awman.waypointmod.util;

import com.awman.waypointmod.WaypointMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

// I know what I'm doing :clueless:
public class StateSaverAndLoader extends PersistentState {

    public WaypointMap waypointMap;// = new WaypointMap();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put(WaypointMap.NBT_STORAGE_KEY, waypointMap.toNbt());
        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound nbt) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.waypointMap = WaypointMap.fromNbt(nbt.getCompound(WaypointMap.NBT_STORAGE_KEY));
        return state;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(
                StateSaverAndLoader::createFromNbt,
                StateSaverAndLoader::new,
                WaypointMod.MOD_ID
        );

        // So that the data will be saved when closing
        state.markDirty();

        return state;
    }
}
