package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.ChatUI;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class InfoWaypointCommand {
    // Class for the /waypoint info command

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")
                // Specify the literal "info"
                .then(CommandManager.literal("info")

                        // Take a mandatory string argument "waypoint_id" to specify the name of the waypoint to inspect
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                // Send custom suggestions, choosing from the name of the available waypoints
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                // Get information about the waypoint
                                .executes(context -> run(context,
                                    StringArgumentType.getString(context, "waypoint_id"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {
        try {
            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

            // If the server's WaypointMap instance does not contain the chosen waypoint:
            if (!serverState.waypointMap.containsKey(waypointId)) {
                // Inform the player via a chat message
                ChatUI.sendMsg(context.getSource(), ChatUI.errorText("That waypoint doesn't exist!", "\"" + waypointId + "\" doesn't exist!"));
                // Return -1 (command execution failed)
                return -1;
            } else { // If the server's WaypointMap instance DOES contain the chosen waypoint:
                // Get the waypoint's data
                WaypointData waypointData = serverState.waypointMap.get(waypointId);

                // Send the header
                context.getSource().sendMessage(Text.of("Waypoint data:"));

                // Send the waypoint's info (name, author, visibility (not implemented), coordinates and dimension)
                context.getSource().sendMessage(Text.of(
                        "-> \"" + waypointId + "\", created by " +
                                "@" + waypointData.author + " (" +
                                (waypointData.isPublic() ? "public" : "private") + "): [" +
                                waypointData.coordinates.toShortString() + " in " +
                                waypointData.dimension.toString() + "]"));

                // Return 1 (command executed successfully)
                return 1;
            }
        } catch (Exception e) {
            // Print any exception to the chat
            ChatUI.sendMsg(context.getSource(), ChatUI.errorText("WPM ERROR: " + e, "An error occurred in the Waypoint Mod"));
            // Return -1 (command execution failed)
            return -1;
        }
    }
}

