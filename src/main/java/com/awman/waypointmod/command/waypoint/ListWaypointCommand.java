package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointAuthorSuggestionProvider;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.awman.waypointmod.util.storage.WaypointData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ListWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("list")
                        .then(CommandManager.argument("username", StringArgumentType.string())
                                //.suggests((context, builder) -> new WaypointAuthorSuggestionProvider().getSuggestions(context, builder))
                                .executes(context -> run(context, StringArgumentType.getString(context, "username"))))
                        .executes(context -> run(context, null))));
    }

    public static int run(CommandContext<ServerCommandSource> context, @Nullable String username) throws CommandSyntaxException {

        final boolean listUserCommands = username != null; // Wether we want to list commands created by a specific user, or all of them

        // Send the waypoint list's header, using a ternary operator to set a coherent title
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
                        "-> \"" + waypointName + "\", " + (listUserCommands ? "" : ("created by @" + waypointData.author))));
            }
        }

        return 1;
    }
}
