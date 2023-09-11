package com.awman.waypointmod.util;

import com.awman.waypointmod.command.CreateWaypointCommand;
import com.awman.waypointmod.command.ListWaypointCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerEverything() {
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(CreateWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(ListWaypointCommand::register);
    }
}
