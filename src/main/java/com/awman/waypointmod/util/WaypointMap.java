package com.awman.waypointmod.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.NbtScannable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.impl.dimension.FabricDimensionInternals;

import java.util.HashMap;
import java.util.Map;

public class WaypointMap extends HashMap<String, WaypointData> {
    public WaypointMap fromNbt(NbtCompound nbt) {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty WaypointMap
        final WaypointMap waypointMap = new WaypointMap();

        // For each entry in the NbtCompound
        for (String waypointName : nbt.getKeys()) {

            // Get the entry's content compound
            NbtCompound waypointDataCompound = nbt.getCompound(waypointName);

            // Create a WaypointData object with the retrieved data
            WaypointData waypointData = WaypointData.fromNbt(waypointDataCompound);

            // Add said object to the WaypointMap
            waypointMap.put(waypointName, waypointData);
        }

        return waypointMap;
    }

    public NbtCompound toNbt(WaypointMap waypointMap) {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty compound
        final NbtCompound nbt = new NbtCompound();

        // For each entry in the hashmap:
        for (Map.Entry<String, WaypointData> entry : waypointMap.entrySet()) {

            // Get the waypoint's name and WaypointData content
            String waypointName = entry.getKey();
            WaypointData waypointData = entry.getValue();

            // Convert the data to Nbt
            NbtCompound waypointDataCompound = waypointData.toNbt();

            // Finally, add this entry to the main compound
            nbt.put(waypointName, waypointDataCompound);
        }

        // Return the converted data
        return nbt;
    }
}
