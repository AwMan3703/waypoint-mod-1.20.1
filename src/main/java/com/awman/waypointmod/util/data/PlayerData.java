package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    // This class holds information about a player

    // Method to check if this item can be safely deleted (i.e. is not storing any information)
    // This is done to avoid taking up space in the .dat files without actually holding any data
    public boolean isDeletable() {
        return
                (this.bookmarks.equals(null) || this.bookmarks.isEmpty()) // It can be deleted if the player has no bookmarks saved
                && (this.followingWaypointId.equals(null) || this.followingWaypointId.isEmpty()) // AND the player isn't following any waypoint
        ;
    }

    // constants for nbt storage keys
    private static final String BOOKMARKS_NBT_KEY = "bookmarks";
    private static final String FOLLOWING_WAYPOINT_ID_NBT_KEY = "fwid";

    // This player's bookmarked waypoints
    public List<String> bookmarks = new ArrayList<>();
    // Add a bookmark
    public void addBookmark(String bookmark) {
        if (!this.bookmarks.contains(bookmark)) this.bookmarks.add(bookmark);
    }
    public void deleteBookmark(String bookmark) {
        this.bookmarks.remove(bookmark);
    }

    // The waypoint this player is following (if any)
    public String followingWaypointId = "";

    // Constructor
    public PlayerData() {
        this.bookmarks = new ArrayList<>();
        this.followingWaypointId = "";
    }

    // fromNbt is used to create a PlayerData instance from NBT data
    public static PlayerData fromNbt(NbtCompound nbt) {
        // Extract the compound data, adding an if(null) condition for backwards compatibility
        List<String> bookmarkData = nbt.getCompound(PlayerData.BOOKMARKS_NBT_KEY).getKeys().stream().toList();
        if (bookmarkData.equals(null)) bookmarkData = new ArrayList<>();

        String followingWaypointId = nbt.getString(PlayerData.FOLLOWING_WAYPOINT_ID_NBT_KEY);
        // No if(null) here, as this may need to be null to work correctly

        // Put the data in a PlayerData object
        PlayerData playerData = new PlayerData();
        playerData.bookmarks = bookmarkData;
        playerData.followingWaypointId = followingWaypointId;

        // Return it
        return playerData;
    }

    // toNbt is used to convert a PlayerData instance's data to NBT
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
        nbt.putString(PlayerData.FOLLOWING_WAYPOINT_ID_NBT_KEY, followingWaypointId);

        // Return the NbtCompound object
        return nbt;
    }
}
