package com.awman.waypointmod.util.storage;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.util.data.PlayerMap;
import com.awman.waypointmod.util.data.WaypointMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

// I know what I'm doing i swear
// This class extends PersistentState, an interface used to save and load static data as NBT compounds in .dat files
// This mod saves to <world_name>/data/waypoint-mod.dat
public class StateSaverAndLoader extends PersistentState {

    // The waypoint map, to keep track of all the existing waypoints
    // (initialize as empty, will be overwritten later if static data is found)
    public WaypointMap waypointMap = new WaypointMap();

    // The player map, to keep track of all player data (bookmarks, followingWaypointId)
    public PlayerMap playerMap = new PlayerMap();

    // writeNbt is responsible for turning this PersistentState into an NBT compound for static storage
    // Data should be put in the incoming NbtCompound argument
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        // Convert the StateSaverAndLoader's contents
        nbt.put(WaypointMap.NBT_STORAGE_KEY, waypointMap.toNbt());
        nbt.put(PlayerMap.NBT_STORAGE_KEY, playerMap.toNbt());

        // Return it
        return nbt;
    }

    // createFromNbt is responsible for generating an instance from an NbtCompound
    // The static data is passed in the incoming NbtCompound argument
    public static StateSaverAndLoader createFromNbt(NbtCompound nbt) {
        // This is Dave. Dave makes the data storage system work. Without Dave, the whole mod crashes.
        StateSaverAndLoader dave = new StateSaverAndLoader();

        // Initialize the StateSaverAndLoader
        dave.waypointMap = WaypointMap.fromNbt(nbt.getCompound(WaypointMap.NBT_STORAGE_KEY));
        dave.playerMap = PlayerMap.fromNbt(nbt.getCompound(PlayerMap.NBT_STORAGE_KEY));

        // Return it
        return dave;
    }

    // getServerState returns this server's StateSaverAndLoader instance
    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        // Get the server's persistentStateManager
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        // Get the server's StateSaverAndLoader (initialize it if none is found, hence getOrCreate())
        StateSaverAndLoader state = persistentStateManager.getOrCreate(
                StateSaverAndLoader::createFromNbt,
                StateSaverAndLoader::new,
                WaypointMod.MOD_ID
        );

        // Do this so the data will be saved when closing or pausing
        state.markDirty();

        // Return it
        return state;
    }
}
