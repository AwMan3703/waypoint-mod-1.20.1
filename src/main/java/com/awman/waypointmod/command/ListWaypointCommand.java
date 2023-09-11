package com.awman.waypointmod.command;

import com.awman.waypointmod.util.StateSaverAnLoader;
import com.awman.waypointmod.util.WaypointData;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.UserCache;

import java.util.Map;
import java.util.UUID;

public class ListWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("list")
                        //.then(CommandManager.argument("username", UuidArgumentType.uuid())
                                .executes(context -> run(context))));//);
    }

    public static int run(CommandContext<ServerCommandSource> context) {//, UUID userId) {

        final boolean listUserCommands = true; // Wether we wanna list commands created by a specific user, or all of them
        final String userName = "unknow_user";

        context.getSource().sendMessage(Text.of("Listing " +
                (listUserCommands ? (userName + "'s") : "all") + " waypoints:"));

        StateSaverAnLoader serverState = StateSaverAnLoader.getServerState(context.getSource().getServer());

        if (serverState.waypointMap.isEmpty()) {
            context.getSource().sendMessage(Text.of("[No waypoints available]"));
        } else {
            for (Map.Entry<String, WaypointData> entry : serverState.waypointMap.entrySet()) {
                String waypointName = entry.getKey();
                WaypointData waypointData = entry.getValue();

                context.getSource().sendMessage(Text.of(
                        "-> \"" + waypointName + "\", created by " +
                        "@" + waypointData.author + ": [" +
                        waypointData.coordinates.toShortString() + " in " +
                        waypointData.dimension.getPath() + "]"));
            }
        }

        return 1;
    }
}
