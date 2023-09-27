package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.command.suggestion.WaypointAuthorSuggestionProvider;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ListWaypointCommand {
    // Class for the /waypoint list command

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, RegistrationEnvironment registrationEnvironment) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")
                // Specify the literal "list"
                .then(CommandManager.literal("list")
                        // If no further argument is provided, list all waypoints
                        .executes(context -> run(context, null))

                        // Take an optional string argument "username", to specify who's waypoints need to be listed
                        .then(CommandManager.argument("username", StringArgumentType.string())
                                // Send custom suggestions, choosing from all the players who have created waypoints on this server
                                .suggests((context, builder) -> new WaypointAuthorSuggestionProvider().getSuggestions(context, builder))
                                // List the chosen player's waypoints
                                .executes(context -> run(context, StringArgumentType.getString(context, "username"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, @Nullable String username) throws CommandSyntaxException {
        try {
            // Find out wether we want to list commands created by a specific user, or all of them
            final boolean listUserCommands = username != null;

            // Send the waypoint list's header in the chat, using a ternary operator to set a coherent title
            context.getSource().sendMessage(Text.of("Listing " +
                    (listUserCommands ? (username + "'s") : "all") + " waypoints on this server:"));

            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

            // If the server's WaypointMap instance is empty:
            if (serverState.waypointMap.isEmpty()) {
                // Inform the player via a chat message
                context.getSource().sendMessage(Text.of("[No waypoints available]"));
            } else { // If any waypoints are saved on this server:
                // For each waypoint:
                for (Map.Entry<String, WaypointData> entry : serverState.waypointMap.entrySet()) {
                    // Get the waypoint's name
                    String waypointName = entry.getKey();

                    // Get the waypoint's data
                    WaypointData waypointData = entry.getValue();

                    if (
                        // If the waypoint is public OR the player is an op
                        (waypointData.isPublic() || context.getSource().hasPermissionLevel(WaypointMod.opPermissionLevel))
                        // AND the waypoint was created by the user we're searching for, OR we're listing all waypoints
                        && (!listUserCommands || (listUserCommands && waypointData.author.equals(username)))
                    ) {
                        // Print the waypoint's name and author in the chat (just the name if we're filtering by author)
                        context.getSource().sendMessage(Text.of(
                                "-> \"" + waypointName + "\"" + (listUserCommands ? "" : (", created by @" + waypointData.author))));
                    }
                }
            }

            // Return 1 (command executed successfully)
            return 1;
        } catch (Exception e) {
            // Print any exception to the chat
            context.getSource().sendMessage(Text.of("WPM ERROR: " + e));
            // Return -1 (command execution failed)
            return -1;
        }
    }
}
