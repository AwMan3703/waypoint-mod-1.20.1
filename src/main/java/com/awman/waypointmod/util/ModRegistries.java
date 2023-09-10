package com.awman.waypointmod.util;

import com.awman.waypointmod.command.CreateWaypointCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerEverything() {
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(CreateWaypointCommand::register);
        //CommandRegistrationCallback.EVENT.register(TpWaypointCommand::register);
    }
}
