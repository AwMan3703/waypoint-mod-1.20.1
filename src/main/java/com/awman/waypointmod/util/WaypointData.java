package com.awman.waypointmod.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
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
}
