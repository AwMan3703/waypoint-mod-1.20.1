package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> bookmarks;
    // Add a bookmark
    public void addBookmark(String bookmark) {
        if (!this.bookmarks.contains(bookmark)) this.bookmarks.add(bookmark);
    }
    public void deleteBookmark(String bookmark) {
        this.bookmarks.remove(bookmark);
    }

    public PlayerData() {
        this.bookmarks = new ArrayList<>();
    }

    public static PlayerData fromNbt(NbtCompound nbt) {
        // Extract the compound data, and create a bookmark map
        List<String> bookmarkData = nbt.getCompound(PlayerData.BOOKMARKS_NBT_KEY).getKeys().stream().toList();

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
        for (String entry : this.bookmarks) {
            bookmarksList.putBoolean(entry, true);
        }
        nbt.put(PlayerData.BOOKMARKS_NBT_KEY,
                bookmarksList);

        // Return the NbtCompound object
        return nbt;
    }
}
