package com.awman.waypointmod.util.storage;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WaypointMap extends HashMap<String, WaypointData> {

    public static final String NBT_STORAGE_KEY = "waypointMap";

    // Add a new entry
    public void insert(String key, WaypointData content) {
        this.put(key, content);
    }

    // Find the keys of entries based on a filter function
    public List<Entry<String, WaypointData>> find(Function<Entry<String, WaypointData>, Boolean> f) {
        List<Map.Entry<String, WaypointData>> results = Arrays.asList();

        for (Map.Entry<String, WaypointData> entry : this.entrySet()) {
            if (f.apply(entry)) results.add(entry);
        }

        return results;
    }

    public static WaypointMap fromNbt(NbtCompound nbt) {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty WaypointMap
        final WaypointMap waypointMap = new WaypointMap();

        // For each entry in the NbtCompound
        for (String waypointName : nbt.getKeys()) {

            // Get the entry's content compound
            NbtCompound dataNbt = nbt.getCompound(waypointName);

            // Create a WaypointData object with the retrieved data
            WaypointData waypointData = WaypointData.fromNbt(dataNbt);

            // Add said object to the WaypointMap
            waypointMap.put(waypointName, waypointData);
        }

        return waypointMap;
    }

    public NbtCompound toNbt() {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty compound
        final NbtCompound nbt = new NbtCompound();

        // For each entry in the hashmap:
        for (Map.Entry<String, WaypointData> entry : this.entrySet()) {

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
