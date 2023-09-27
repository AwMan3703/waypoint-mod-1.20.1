package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WaypointMap extends HashMap<String, WaypointData> {
    // This class is used for keeping track of this server's waypoints

    // The name under which the data will be saved statically
    public static final String NBT_STORAGE_KEY = "waypointMap";

    // Method to add a new entry
    public void insert(String key, WaypointData content) {
        this.put(key, content);
    }

    // Method to find keys of entries based on a filter function
    public List<Entry<String, WaypointData>> find(Function<Entry<String, WaypointData>, Boolean> f) {
        // Initialize the results list
        List<Map.Entry<String, WaypointData>> results = Arrays.asList();

        // For each waypoint in this map:
        for (Map.Entry<String, WaypointData> entry : this.entrySet()) {
            // If the waypoint satisfies the filter, add it to the results
            if (f.apply(entry)) results.add(entry);
        }

        // Return the results list
        return results;
    }

    // fromNbt is used to create a WaypointMap instance from NBT data
    public static WaypointMap fromNbt(NbtCompound nbt) {
        // A conversion from NbtCompound to WaypointMap is needed:

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

        // Return the WaypointMap instance
        return waypointMap;
    }

    // toNbt is used to convert a WaypointMap instance's data to NBT
    public NbtCompound toNbt() {
        // A conversion from WaypointMap to NbtCompound is needed:

        // Create an empty compound
        final NbtCompound nbt = new NbtCompound();

        // For each entry in the hashmap:
        for (Map.Entry<String, WaypointData> entry : this.entrySet()) {

            // Get the waypoint's name and WaypointData content
            String waypointName = entry.getKey();
            WaypointData waypointData = entry.getValue();

            // Convert the data to Nbt-friendly
            NbtCompound waypointDataCompound = waypointData.toNbt();

            // Finally, add this entry to the main compound
            nbt.put(waypointName, waypointDataCompound);
        }

        // Return the converted data
        return nbt;
    }
}
