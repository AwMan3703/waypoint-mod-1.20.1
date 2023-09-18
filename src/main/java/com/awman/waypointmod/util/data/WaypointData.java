package com.awman.waypointmod.util.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class WaypointData {

    // constants for nbt storage keys
    private static final String AUTHOR_NBT_KEY = "author";
    private static final String POSITION_NBT_KEY = "position";
    private static final String DIMENSION_NBT_KEY = "dimension";
    private static final String VISIBILITY_NBT_KEY = "visibility";

    // The player who created the waypoint
    public String author;
    public boolean isAuthor(String f) { return this.author.equals(f); }

    // The coordinates of this waypoint
    public BlockPos coordinates;
    public boolean isCoords(BlockPos f) { return this.coordinates.equals(f); }

    // The dimension this waypoint applies to
    public Identifier dimension;
    public boolean isDimension(Identifier f) { return this.dimension.equals(f); }

    // Wether this waypoint is visible to everyone
    private final Boolean visibility; // True = public ; False = private
    public boolean isPublic() { return this.visibility.equals(true); }

    public WaypointData(String author, BlockPos position, String dimension, Boolean isPublic) {
        this.author = author;
        this.coordinates = position;
        this.dimension = new Identifier(dimension);
        this.visibility = isPublic;
    }

    public static WaypointData fromNbt(NbtCompound nbt) {
        // Extract the compound data, adding an if(null) condition for backwards compatibility
        String author = nbt.getString(WaypointData.AUTHOR_NBT_KEY);
        if (author.equals(null)) author = "<unknown_author>";

        int[] arrayPos = nbt.getIntArray(WaypointData.POSITION_NBT_KEY);
        if (arrayPos.equals(null)) arrayPos = new int[]{-999, -999, -999};
        BlockPos position = new BlockPos(arrayPos[0], arrayPos[1], arrayPos[2]);

        String dimension = nbt.getString(WaypointData.DIMENSION_NBT_KEY);
        if (dimension.equals(null)) dimension = "minecraft:overworld";

        Boolean visibility = nbt.getBoolean(WaypointData.VISIBILITY_NBT_KEY);
        if (visibility.equals(null)) visibility = true;

        // Return a WaypointData object
        return new WaypointData(author, position, dimension, visibility);
    }

    public NbtCompound toNbt() {
        // Create an empty Nbt Compound
        NbtCompound nbt = new NbtCompound();

        // Convert this object's properties to basic types, then put() them in the compound
        nbt.putString(WaypointData.AUTHOR_NBT_KEY,
                this.author);
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
