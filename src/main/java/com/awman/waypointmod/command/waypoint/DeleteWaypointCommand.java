package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.ChatUI;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.data.WaypointMap;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class DeleteWaypointCommand {
    // Class for the /waypoint delete command

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")
                // Specify the literal "delete"
                .then(CommandManager.literal("delete")

                        // Take a mandatory string argument "waypoint_id" to specify the name of the waypoint to delete
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                // Send custom suggestions, choosing from the name of the available waypoints
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                // Delete the waypoint
                                .executes(context -> run(context,
                                        StringArgumentType.getString(context, "waypoint_id"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {
        try {
            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

            // Get the server's WaypointMap instance
            WaypointMap waypointMap = serverState.waypointMap;

            // Get the waypoint's data
            WaypointData waypointData = waypointMap.get(waypointId);

            // If the waypointMap doesn't contain the chosen waypoint
            if (!waypointMap.containsKey(waypointId)) {
                // Send a message in chat to inform the player
                context.getSource().sendMessage(Text.of("Waypoint not found!"));
                // Return -1 (command execution failed)
                return -1;
            } else if ( // If the waypointMap DOES contain the chosen waypoint, check if they DO NOT have enough permissions
                    !context.getSource().getName().equals(waypointData.author) && // Either the player isn't the author
                    !context.getSource().hasPermissionLevel(WaypointMod.opPermissionLevel) // Or the player isn't an op
            ) {
                // If the player doesn't have the necessary permissions, inform them via a chat message
                context.getSource().sendMessage(Text.of("Insufficient permissions!"));
                // Return -1 (command execution failed)
                return -1;
            } else { // If no problem is detected:
                // Delete the waypoint from the server's waypointMap
                serverState.waypointMap.remove(waypointId);

                // Do this so that the serverState will be saved as soon as possible
                serverState.markDirty();

                // Send a message in chat, saying the waypoint has been deleted
                context.getSource().sendMessage(Text.of("Waypoint deleted!"));

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
