package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.argument.UsernameArgumentType;
import com.awman.waypointmod.util.StateSaverAndLoader;
import com.awman.waypointmod.util.WaypointData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Map;

public class ListWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("list")
                        .then(CommandManager.argument("username", UsernameArgumentType.username())
                                .executes(context -> run(context,
                                        UsernameArgumentType.getUsername(context, "username"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String username) throws CommandSyntaxException {

        final boolean listUserCommands = true; // Wether we want to list commands created by a specific user, or all of them

        context.getSource().sendMessage(Text.of("Listing " +
                (listUserCommands ? (username + "'s") : "all") + " waypoints:"));

        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

        if (serverState.waypointMap.isEmpty()) {
            context.getSource().sendMessage(Text.of("[No waypoints available]"));
        } else {
            for (Map.Entry<String, WaypointData> entry : serverState.waypointMap.entrySet()) {
                String waypointName = entry.getKey();
                WaypointData waypointData = entry.getValue();

                context.getSource().sendMessage(Text.of(
                        "-> \"" + waypointName + "\", created by @" + waypointData.author));
            }
        }

        return 1;
    }
}
