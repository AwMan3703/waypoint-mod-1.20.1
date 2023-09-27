package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PlayerMap extends HashMap<String, PlayerData> {
    // This class is used for keeping track of players' data (bookmarks, followingId)

    // The name under which the data will be saved statically
    public static final String NBT_STORAGE_KEY = "playerMap";

    // Add a new entry
    public void insert(String key, PlayerData content) {
        this.put(key, content);
    }

    // Modify an existing entry with a modifier function (takes the old entry, outputs the new one)
    public void edit(String key, Function<PlayerData, PlayerData> f) {
        // Replace the old entry for <key> with the result of f()
        this.put(key, f.apply(
                this.getOrDefault(key, new PlayerData())
        ));
    }

    // Find the keys of entries based on a filter function
    public List<Map.Entry<String, PlayerData>> find(Function<Entry<String, PlayerData>, Boolean> f) {
        // Initialize the results list
        List<Map.Entry<String, PlayerData>> results = Arrays.asList();

        // For each waypoint in this map:
        for (Map.Entry<String, PlayerData> entry : this.entrySet()) {
            // If the player data satisfies the filter, add it to the results
            if (f.apply(entry)) results.add(entry);
        }

        // Return the results list
        return results;
    }

    // fromNbt is used to create a PlayerMap instance from NBT data
    public static PlayerMap fromNbt(NbtCompound nbt) {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty PlayerMap
        final PlayerMap playerMap = new PlayerMap();

        // For each entry in the NbtCompound
        for (String rawUuid : nbt.getKeys()) {

            // Convert the key into an actual UUID
            //UUID uuid = UUID.fromString(rawUuid);

            // Get the entry's content compound
            NbtCompound dataNbt = nbt.getCompound(rawUuid);

            // Create a PlayerData object with the retrieved data
            PlayerData playerData = PlayerData.fromNbt(dataNbt);

            // Add said object to the PlayerMap
            playerMap.put(rawUuid, playerData);
        }

        return playerMap;
    }

    // toNbt is used to convert a PlayerMap instance's data to NBT
    public NbtCompound toNbt() {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty compound
        final NbtCompound nbt = new NbtCompound();

        // For each entry in the hashmap:
        for (Map.Entry<String, PlayerData> entry : this.entrySet()) {

            // Get the player data's user and PlayerData content
            String uuid = entry.getKey();
            PlayerData playerData = entry.getValue();

            // If the player data is determined to be superfluous, just don't save it
            if (playerData.isDeletable()) { continue; }

            // Convert the data to Nbt-friendly
            NbtCompound playerDataCompound = playerData.toNbt();

            // Finally, add this entry to the main compound
            nbt.put(uuid, playerDataCompound);
        }

        // Return the converted data
        return nbt;
    }
}
