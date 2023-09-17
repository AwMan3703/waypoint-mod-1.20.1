package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class WaypointData {

    // constants for nbt storage keys
    private static final String AUTHOR_NBT_KEY = "author";
    private static final String AUTHOR_UUID_NBT_KEY = "author-uuid";
    private static final String POSITION_NBT_KEY = "position";
    private static final String DIMENSION_NBT_KEY = "dimension";
    private static final String VISIBILITY_NBT_KEY = "visibility";

    // The player who created the waypoint
    public String author;
    public boolean isAuthor(String f) { return this.author.equals(f); }

    // The author's UUID
    public UUID authorUUID;
    public boolean isAuthorUUID(UUID f) { return this.authorUUID.equals(f); }

    // The coordinates of this waypoint
    public BlockPos coordinates;
    public boolean isCoords(BlockPos f) { return this.coordinates.equals(f); }

    // The dimension this waypoint applies to
    public Identifier dimension;
    public boolean isDimension(Identifier f) { return this.dimension.equals(f); }

    // Wether this waypoint is visible to everyone
    private final Boolean visibility; // True = public ; False = private
    public boolean isPublic() { return this.visibility.equals(true); }

    public WaypointData(String author, UUID authorUUID, BlockPos position, String dimension, Boolean isPublic) {
        this.author = author;
        this.authorUUID = authorUUID;
        this.coordinates = position;
        this.dimension = new Identifier(dimension);
        this.visibility = isPublic;
    }

    public static WaypointData fromNbt(NbtCompound nbt) {

        // Extract the author String
        String author = nbt.getString(WaypointData.AUTHOR_NBT_KEY);
        // Extract the author UUID
        UUID authorUUID = nbt.getUuid(WaypointData.AUTHOR_UUID_NBT_KEY);
        // Extract the position array, then convert it to a BlockPos
        int[] arrayPos = nbt.getIntArray(WaypointData.POSITION_NBT_KEY);
        BlockPos position = new BlockPos(arrayPos[0], arrayPos[1], arrayPos[2]);
        // Extract the dimension String
        String dimension = nbt.getString(WaypointData.DIMENSION_NBT_KEY);
        // Extract the visibility Boolean
        Boolean visibility = nbt.getBoolean(WaypointData.VISIBILITY_NBT_KEY);

        // Return a WaypointData object
        return new WaypointData(author, authorUUID, position, dimension, visibility);
    }

    public NbtCompound toNbt() {
        // Create an empty Nbt Compound
        NbtCompound nbt = new NbtCompound();

        // Convert this object's properties to basic types, then put() them in the compound
        nbt.putString(WaypointData.AUTHOR_NBT_KEY,
                this.author);
        nbt.putUuid(WaypointData.AUTHOR_UUID_NBT_KEY,
                this.authorUUID);
        nbt.putIntArray(WaypointData.POSITION_NBT_KEY,
                new int[]{ this.coordinates.getX(), this.coordinates.getY(), this.coordinates.getZ() });
        nbt.putString(WaypointData.DIMENSION_NBT_KEY,
                this.dimension.toString());
        nbt.putBoolean(WaypointData.VISIBILITY_NBT_KEY,
                this.visibility);

        // Return the NbtCompound object
        return nbt;
    }
}
