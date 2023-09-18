package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    public boolean isDeletable() { // Wether it is safe to delete this player's data on save
        return
                (this.bookmarks.isEmpty()) // If the player has no bookmarks saved
                // && ( another_condition || another_alternative )
        ;
    }

    // constants for nbt storage keys
    private static final String BOOKMARKS_NBT_KEY = "bookmarks";

    // This player's bookmarked waypoints
    public HashMap<String, WaypointData> bookmarks;
    // Add a bookmark
    public void addBookmark(String bookmark, WaypointData data) {
        if (!this.bookmarks.containsKey(bookmark)) this.bookmarks.putIfAbsent(bookmark, data);
    }
    public void deleteBookmark(String bookmark) {
        this.bookmarks.remove(bookmark);
    }

    public PlayerData() {
        this.bookmarks = new HashMap<String, WaypointData>();
    }

    public static PlayerData fromNbt(NbtCompound nbt) {
        // Extract the compound data, and create a bookmark map
        NbtCompound bookmarkCompound = nbt.getCompound(PlayerData.BOOKMARKS_NBT_KEY);
        HashMap<String, WaypointData> bookmarkData = new HashMap<String, WaypointData>();
        // Transfer the data into the map
        for (String key : bookmarkCompound.getKeys()) { bookmarkData.put(key, WaypointData.fromNbt(bookmarkCompound.getCompound(key))); }

        // Return a PlayerData object
        PlayerData playerData = new PlayerData();
        playerData.bookmarks = bookmarkData;
        return playerData;
    }
    public NbtCompound toNbt() {
        // Create an empty Nbt Compound
        NbtCompound nbt = new NbtCompound();

        // Convert this object's properties to basic types, then put() them in the compound
        NbtCompound bookmarksList = new NbtCompound();
        for (Map.Entry<String, WaypointData> entry : this.bookmarks.entrySet()) {
            bookmarksList.put(
                    entry.getKey(),
                    entry.getValue().toNbt()
            );
        }
        nbt.put(PlayerData.BOOKMARKS_NBT_KEY,
                bookmarksList);

        // Return the NbtCompound object
        return nbt;
    }
}
