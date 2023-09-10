package com.awman.waypointmod.util;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class WaypointMap extends HashMap<String, WaypointData> {
    public WaypointMap fromNbt(NbtCompound nbt) {
        final WaypointMap waypointMap = new WaypointMap();
        return waypointMap;
    }

    public NbtCompound toNbt(WaypointMap waypointMap) {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty compound
        final NbtCompound nbt = new NbtCompound();

        // For each entry in the hashmap:
        for (Map.Entry<String, WaypointData> entry : waypointMap.entrySet()) {
            String waypointName = entry.getKey();
            WaypointData waypointData = entry.getValue();

            NbtCompound waypointDataCompound = new NbtCompound();

            waypointDataCompound.putString(WaypointData.AUTHOR_NBT_KEY,
                    waypointData.author);
            waypointDataCompound.putIntArray(WaypointData.POSITION_NBT_KEY,
                    new int[]{ waypointData.coordinates.getX(), waypointData.coordinates.getY(), waypointData.coordinates.getZ() });
            waypointDataCompound.putString(WaypointData.DIMENSION_NBT_KEY,
                    waypointData.dimension.toString());
        }

        return nbt;
    }
}
