package com.awman.waypointmod.util;

import com.awman.waypointmod.command.waypoint.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    // This class manages the mod's registries (adds things like modded blocks, items and commands to the game)
    // In this mod's case, we're only adding commands

    public static void registerEverything() {
        // Register everything
        registerCommands();
    }

    private static void registerCommands() {
        // For each command, call it's register() method
        CommandRegistrationCallback.EVENT.register(CreateWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(ListWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(InfoWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(FollowWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(DeleteWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(BookmarkWaypointCommand::register);
    }
}
