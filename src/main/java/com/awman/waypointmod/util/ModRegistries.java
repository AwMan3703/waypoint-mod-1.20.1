package com.awman.waypointmod.util;

import com.awman.waypointmod.command.waypoint.CreateWaypointCommand;
import com.awman.waypointmod.command.waypoint.DeleteWaypointCommand;
import com.awman.waypointmod.command.waypoint.InfoWaypointCommand;
import com.awman.waypointmod.command.waypoint.ListWaypointCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerEverything() {
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(CreateWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(ListWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(InfoWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(DeleteWaypointCommand::register);
    }
}
