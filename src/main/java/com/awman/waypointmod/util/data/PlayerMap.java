package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.visitor.NbtElementVisitor;

import java.util.*;
import java.util.function.Function;

public class PlayerMap extends HashMap<String, PlayerData> {

    public static final String NBT_STORAGE_KEY = "playerMap";

    // Add a new entry
    public void insert(UUID key, PlayerData content) {
        this.put(key, content);
    }

    // Modify an existing entry
    public void edit(UUID key, Function<PlayerData, PlayerData> f) {
        this.put(key, f.apply(
                this.getOrDefault(key, new PlayerData())
        ));
    }

    // Find the keys of entries based on a filter function
    public List<Map.Entry<String, PlayerData>> find(Function<Entry<String, PlayerData>, Boolean> f) {
        List<Map.Entry<String, PlayerData>> results = Arrays.asList();

        for (Map.Entry<String, PlayerData> entry : this.entrySet()) {
            if (f.apply(entry)) results.add(entry);
        }

        return results;
    }

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

    public NbtCompound toNbt() {
        // A conversion from Hashmap to NbtCompound is needed:

        // Create an empty compound
        final NbtCompound nbt = new NbtCompound();

        // For each entry in the hashmap:
        for (Map.Entry<String, PlayerData> entry : this.entrySet()) {

            // Get the player data's user and PlayerData content
            String uuid = entry.getKey().toString();
            PlayerData playerData = entry.getValue();

            // Convert the data to Nbt-friendly
            NbtCompound playerDataCompound = playerData.toNbt();

            // Finally, add this entry to the main compound
            nbt.put(uuid, playerDataCompound);
        }

        // Return the converted data
        return nbt;
    }
}
