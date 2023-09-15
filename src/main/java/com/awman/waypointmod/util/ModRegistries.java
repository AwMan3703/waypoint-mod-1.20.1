package com.awman.waypointmod.util;

import com.awman.waypointmod.command.suggestion.WaypointAuthorSuggestionProvider;
import com.awman.waypointmod.command.waypoint.*;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;

public class ModRegistries {
    public static void registerEverything() {
        // Register everything
        registerCommands();
        registerSuggestionProviders();
    }

    private static void registerSuggestionProviders() {
        SuggestionProviders.register(WaypointAuthorSuggestionProvider.ID, new WaypointAuthorSuggestionProvider());
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(CreateWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(ListWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(InfoWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(FollowWaypoint::register);
        CommandRegistrationCallback.EVENT.register(DeleteWaypointCommand::register);
        CommandRegistrationCallback.EVENT.register(BookmarkWaypoint::register);
    }
}
