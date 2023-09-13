package com.awman.waypointmod.util.storage;

import com.awman.waypointmod.WaypointMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class WaypointData {

    // constants for nbt storage keys
    public static final String AUTHOR_NBT_KEY = "author";
    public static final String POSITION_NBT_KEY = "position";
    public static final String DIMENSION_NBT_KEY = "dimension";

    // The player who created the waypoint
    public String author;
    public boolean isAuthor(String f) { return this.author==f; }

    // The coordinates of this waypoint
    public BlockPos coordinates;
    public boolean isCoords(BlockPos f) { return this.coordinates.equals(f); }

    // The dimension this waypoint applies to
    public Identifier dimension;
    public boolean isDimension(Identifier f) { return this.dimension.equals(f); }

    public WaypointData(String author, BlockPos position, String dimension) {
        this.author = author;
        this.coordinates = position;
        this.dimension = new Identifier(dimension);
    }

    public static WaypointData fromNbt(NbtCompound nbt) {
        // Extract the author String
        String author = nbt.getString(WaypointData.AUTHOR_NBT_KEY);
        WaypointMod.LOGGER.debug(author);

        // Extract the position array, then convert it to a BlockPos
        int[] arrayPos = nbt.getIntArray(WaypointData.POSITION_NBT_KEY);
        BlockPos position = new BlockPos(arrayPos[0], arrayPos[1], arrayPos[2]);

        // Extract the dimension String
        String dimension = nbt.getString(WaypointData.DIMENSION_NBT_KEY);
        //RegistryKey<World> dimension = RegistryKey.of(RegistryKey.ofRegistry( /**/, new Identifier(stringDim[0], stringDim[1]));

        // Return a WaypointData object
        return new WaypointData(author, position, dimension);
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

        // Return the NbtCompound object
        return nbt;
    }
}
