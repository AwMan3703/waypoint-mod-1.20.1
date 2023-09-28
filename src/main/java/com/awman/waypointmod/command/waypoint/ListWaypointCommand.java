package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.command.suggestion.WaypointAuthorSuggestionProvider;
import com.awman.waypointmod.util.ChatUI;
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
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

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

            // Send a spacer
            ChatUI.sendSpacer(context.getSource());

            // Send the waypoint list's header in the chat, using a ternary operator to set a coherent title
            ChatUI.sendMsg(
                    context.getSource(),
                    ChatUI.colored("Listing " + (listUserCommands ? (username + "'s") : "all") + " waypoints on this server:", ChatUI.color_Header)
            );
            /*context.getSource().sendMessage(Text.of("Listing " +
                    (listUserCommands ? (username + "'s") : "all") + " waypoints on this server:"));*/

            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

            // If the server's WaypointMap instance is empty:
            if (serverState.waypointMap.isEmpty()) {
                // Inform the player via a chat message
                ChatUI.sendMsg(
                        context.getSource(),
                        ChatUI.colored("[No waypoints available]", ChatUI.color_Bg)
                );
                //context.getSource().sendMessage(Text.of("[No waypoints available]"));
            } else { // If any waypoints are saved on this server:
                // Boolean for alternating the list items' colors
                boolean isOdd = true;

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
                        ChatUI.sendMsg(
                                context.getSource(),
                                ChatUI.colored("-> \"",
                                        isOdd ? ChatUI.color_Secondary : ChatUI.color_Bg).append(
                                ChatUI.colored(waypointName,
                                        ChatUI.color_Main)).append(
                                ChatUI.colored("\"" + (listUserCommands ? "" : (", created by @" + waypointData.author)),
                                        isOdd ? ChatUI.color_Secondary : ChatUI.color_Bg)).append(
                                ChatUI.styledText(" [+]", Formatting.GREEN,
                                        HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of("Click to follow")),
                                        new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/waypoint follow " + waypointName)))
                        );
                        /*context.getSource().sendMessage(Text.of(
                                "-> \"" + waypointName + "\"" + (listUserCommands ? "" : (", created by @" + waypointData.author))));*/
                        isOdd = !isOdd; // Switch colors for the next item
                    }
                }
            }

            // Send another spacer
            ChatUI.sendSpacer(context.getSource());

            // Return 1 (command executed successfully)
            return 1;
        } catch (Exception e) {
            // Print any exception to the chat
            ChatUI.sendError(context.getSource(), e.toString());
            // Return -1 (command execution failed)
            return -1;
        }
    }
}
