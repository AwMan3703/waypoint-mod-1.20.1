package com.awman.waypointmod.util.data;

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
        // Convert the StateSaverAndLoader to an NBT compound, for saving
        nbt.put(WaypointMap.NBT_STORAGE_KEY, waypointMap.toNbt());

        // Return it
        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound nbt) {
        // This is Dave. Dave makes the data storage system work. Without Dave, the whole mod crashes.
        StateSaverAndLoader dave = new StateSaverAndLoader();

        // Initialize the StateSaverAndLoader
        dave.waypointMap = WaypointMap.fromNbt(nbt.getCompound(WaypointMap.NBT_STORAGE_KEY));

        // Return it
        return dave;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        // Get the server's persistentStateManager
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        // Initialize it
        StateSaverAndLoader state = persistentStateManager.getOrCreate(
                StateSaverAndLoader::createFromNbt,
                StateSaverAndLoader::new,
                WaypointMod.MOD_ID
        );

        // So that the data will be saved when closing or pausing
        state.markDirty();

        // Return it
        return state;
    }
}
