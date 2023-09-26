package com.awman.waypointmod.util;

import com.awman.waypointmod.command.waypoint.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
public class ModRegistries {
    public static void registerEverything() {
        // Register everything
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(CreateWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(ListWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(InfoWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(FollowWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(DeleteWaypointCommand::register);

        // Available in full version
        //CommandRegistrationCallback.EVENT.register(BookmarkWaypointCommand::register);
    }
}
