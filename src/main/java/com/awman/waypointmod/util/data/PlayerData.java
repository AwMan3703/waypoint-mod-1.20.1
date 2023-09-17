package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;

import java.util.Arrays;
import java.util.List;

public class PlayerData {

    // constants for nbt storage keys
    private static final String BOOKMARKS_NBT_KEY = "bookmarks";

    // This player's bookmarked waypoints
    public List<String> bookmarks;

    public static PlayerData fromNbt(NbtCompound nbt) {

        // Extract the bookmarks
        List<String> bookmarks = nbt.getCompound(PlayerData.BOOKMARKS_NBT_KEY).getKeys().stream().toList();

        // Return a PlayerData object
        PlayerData playerData = new PlayerData();
        playerData.bookmarks = bookmarks;
        return playerData;
    }
    public NbtCompound toNbt() {
        // Create an empty Nbt Compound
        NbtCompound nbt = new NbtCompound();

        // Convert this object's properties to basic types, then put() them in the compound
        NbtCompound bookmarksList = new NbtCompound();
        this.bookmarks.forEach(b -> bookmarksList.putBoolean(b, true));
        nbt.put(PlayerData.BOOKMARKS_NBT_KEY,
                bookmarksList);

        // Return the NbtCompound object
        return nbt;
    }
}
