package com.awman.waypointmod.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

public class WaypointData {

    // constants for nbt storage keys
    public static final String AUTHOR_NBT_KEY = "author";
    public static final String POSITION_NBT_KEY = "position";
    public static final String DIMENSION_NBT_KEY = "dimension";

    // The player who created the waypoint
    public String author;

    // The coordinates of this waypoint
    public BlockPos coordinates;

    // The dimension this waypoint applies to
    public RegistryKey<World> dimension;

    public WaypointData(String author, BlockPos position, RegistryKey<World> dimensionRegistryKey) {
        this.author = author;
        this.coordinates = position;
        this.dimension = dimensionRegistryKey;
    }

    public static WaypointData fromNbt(NbtCompound nbt) {
        // Extract the author String
        String author = nbt.getString(WaypointData.AUTHOR_NBT_KEY);

        // Extract the position array, then convert it to a BlockPos
        int[] arrayPos = nbt.getIntArray(WaypointData.POSITION_NBT_KEY);
        BlockPos position = new BlockPos(arrayPos[0], arrayPos[1], arrayPos[2]);

        // Extract the dimension String, then convert it to a RegistryKey<World>
        String[] stringDim = nbt.getString(WaypointData.DIMENSION_NBT_KEY).split(":");
        RegistryKey<World> dimension = RegistryKey.of(RegistryKey.ofRegistry(new Identifier("dimension")), new Identifier(stringDim[0], stringDim[1]));

        // Return a WaypointData object
        return new WaypointData(author, position, dimension);
    }

    public NbtCompound toNbt() {
        // Create an empty Nbt Compound
        NbtCompound nbt = new NbtCompound();

        // Convert this object's properties to basic types,
        // then put() them in the compound
        nbt.putString(WaypointData.AUTHOR_NBT_KEY,
                this.author);
        nbt.putIntArray(WaypointData.POSITION_NBT_KEY,
                new int[]{ this.coordinates.getX(), this.coordinates.getY(), this.coordinates.getZ() });
        nbt.putString(WaypointData.DIMENSION_NBT_KEY,
                this.dimension.getValue().toString());

        // Return the NbtCompound object
        return nbt;
    }
}
