package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.ChatUI;
import com.awman.waypointmod.util.data.PlayerData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class BookmarkWaypointCommand {
    // Class for the /waypoint bookmark command

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")
                // Specify the literal "bookmark"
                .then(CommandManager.literal("bookmark")

                        // Take a literal, choosing from the following 3:
                        // If the literal "view" is passed:
                        .then(CommandManager.literal("view")
                                // Run the view function
                                .executes(context -> runView(context)))

                        // If the literal "add" is passed:
                        .then(CommandManager.literal("add")
                                // Take a mandatory argument "waypoint_id" for the name of the waypoint to be bookmarked
                                .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                        // Send custom suggestions, choosing from the names of the available waypoints
                                        .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                        // Add the chosen waypoint to the player's bookmarks
                                        .executes(context -> runAdd(context,
                                                StringArgumentType.getString(context, "waypoint_id")))))

                        // If the literal "remove" is passed:
                        .then(CommandManager.literal("remove")
                                // Take a mandatory argument "waypoint_id" for the name of the waypoint to be bookmarked
                                .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                        // Send custom suggestions, choosing from the names of the player's bookmarks
                                        .suggests((context, builder) -> new WaypointNameSuggestionProvider().getBookmarkSuggestions(context, builder))
                                        // Remove the chosen waypoint from the player's bookmarks
                                        .executes(context -> runRemove(context,
                                                StringArgumentType.getString(context, "waypoint_id")))))));
    }

    public static int runView(CommandContext<ServerCommandSource> context) {
        try {
            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getServer());

            // Send a spacer
            ChatUI.sendSpacer(context.getSource());

            if (
                // If this player's waypoint list is empty or absent (if absent, create a new one)
                serverState.playerMap.computeIfAbsent(context.getSource().getPlayer().getUuid().toString(), uuid -> new PlayerData()).bookmarks.isEmpty()
            ) {
                // Send a message in chat, saying there are no bookmarks
                ChatUI.sendMsg(
                        context.getSource(),
                        ChatUI.colored("No bookmarked waypoints!", ChatUI.color_Negative)
                );
                //context.getSource().sendMessage(Text.of("No bookmarked waypoints!"));

                // Return -1 (command execution failed)
                return -1;
            }

            // If the player has bookmarks, get them in a List<String>
            List<String> bookmarks = serverState.playerMap.computeIfAbsent(context.getSource().getPlayer().getUuid().toString(), uuid -> new PlayerData()).bookmarks;

            // Send the list header in chat
            ChatUI.sendMsg(
                    context.getSource(),
                    ChatUI.colored("Your bookmarked waypoints:", ChatUI.color_Header)
            );
            //context.getSource().sendMessage(Text.of("Your bookmarked waypoints:"));

            // Boolean for alternating the list items' colors
            boolean isOdd = true;

            // For each bookmarked waypoint:
            for (String entry : bookmarks) {
                // Print the waypoint's name and author to the chat
                ChatUI.sendMsg(
                        context.getSource(),
                        ChatUI.colored("-> \"",
                                isOdd ? ChatUI.color_Secondary : ChatUI.color_Bg).append(
                        ChatUI.colored(entry, ChatUI.color_Main)).append(
                        ChatUI.colored("\", by @" + serverState.waypointMap.get(entry).author,
                                isOdd ? ChatUI.color_Secondary : ChatUI.color_Bg))
                );
                /*context.getSource().sendMessage(Text.of(
                        "-> " + entry +
                            ", created by @" + serverState.waypointMap.get(entry).author
                ));*/

                isOdd = !isOdd; // Switch colors for the next item
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

    public static int runAdd(CommandContext<ServerCommandSource> context, String waypointId) {
        try {
            // Get the command source
            ServerCommandSource source = context.getSource();

            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(source.getServer());

            // Get the player's data
            PlayerData playerData = serverState.playerMap.computeIfAbsent(source.getPlayer().getUuid().toString(), uuid -> new PlayerData());

            // Add the waypoint to the player's bookmarks
            playerData.addBookmark(waypointId);

            // Send a message in chat, saying the bookmark has been added
            ChatUI.sendMsg(
                    context.getSource(),
                    ChatUI.colored("\"", ChatUI.color_Secondary).append(
                    ChatUI.colored(waypointId, ChatUI.color_Main)).append(
                    ChatUI.colored("\" added to your bookmarks! Run ", ChatUI.color_Secondary)).append(
                    ChatUI.styledText("[/waypoint bookmark view]", Formatting.GREEN,
                            HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of("Click to view your bookmarks")),
                            new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/waypoint bookmark view"))).append(
                    ChatUI.colored(" to view them", ChatUI.color_Secondary))
            );
            //source.sendMessage(Text.of("\"" + waypointId + "\" added to your bookmarks! Run [/waypoint bookmark view] to view them"));

            // Return 1 (command executed successfully)
            return 1;
        } catch (Exception e) {
            // Print any exception to the chat
            ChatUI.sendError(context.getSource(), e.toString());

            // Return -1 (command execution failed)
            return -1;
        }
    }

    public static int runRemove(CommandContext<ServerCommandSource> context, String waypointId) {
        try {
            // Get the command source
            ServerCommandSource source = context.getSource();

            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(source.getServer());

            // Get the player's data
            PlayerData playerData = serverState.playerMap.computeIfAbsent(source.getPlayer().getUuid().toString(), uuid -> new PlayerData());

            // If the player's data does not contain the chosen waypoint, send a message in chat to inform the player
            if (!playerData.bookmarks.contains(waypointId)) {
                ChatUI.sendMsg(
                        context.getSource(),
                        ChatUI.colored("\"" + waypointId + "\" wasn't in your bookmarks!", ChatUI.color_Negative)
                );
                //source.sendMessage(Text.of("\"" + waypointId + "\" wasn't in your bookmarks!"));
            }

            // Remove the waypoint to the player's bookmarks
            playerData.deleteBookmark(waypointId);

            // Send a message in chat, saying the bookmark has been removed
            ChatUI.sendMsg(
                    context.getSource(),
                    ChatUI.colored("\"" + waypointId + "\" removed from your bookmarks!", ChatUI.color_Positive)
            );
            //source.sendMessage(Text.of("\"" + waypointId + "\" removed from your bookmarks! Run [/waypoint bookmark view] to view them"));

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
