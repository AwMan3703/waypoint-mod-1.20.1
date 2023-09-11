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

            // Extract the author String
            String author = waypointDataCompound.getString(WaypointData.AUTHOR_NBT_KEY);

            // Extract the position array, then convert it to a BlockPos
            int[] arrayPos = waypointDataCompound.getIntArray(WaypointData.POSITION_NBT_KEY);
            BlockPos position = new BlockPos(arrayPos[0], arrayPos[1], arrayPos[2]);

            // Extract the dimension String, then convert it to a RegistryKey<World>
            String[] stringDim = waypointDataCompound.getString(WaypointData.DIMENSION_NBT_KEY).split(":");
            RegistryKey<World> dimension = RegistryKey.of(RegistryKey.of(Registries.DIMENSION_TYPE, new Identifier(stringDim[0], stringDim[1])));

            // Populate a WaypointData object with the retrieved data
            WaypointData waypointData = new WaypointData(author, position, dimension);
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

            // Create an empty compound for this entry
            NbtCompound waypointDataCompound = new NbtCompound();

            // Convert the WaypointData properties to basic types,
            // then populate the compound
            waypointDataCompound.putString(WaypointData.AUTHOR_NBT_KEY,
                    waypointData.author);
            waypointDataCompound.putIntArray(WaypointData.POSITION_NBT_KEY,
                    new int[]{ waypointData.coordinates.getX(), waypointData.coordinates.getY(), waypointData.coordinates.getZ() });
            waypointDataCompound.putString(WaypointData.DIMENSION_NBT_KEY,
                    waypointData.dimension.getValue().toString());

            // Finally, add this entry to the main compound
            nbt.put(waypointName, waypointDataCompound);
        }

        // Return the converted data
        return nbt;
    }
}
