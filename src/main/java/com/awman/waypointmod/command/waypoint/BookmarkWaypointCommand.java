package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.data.PlayerData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;

public class BookmarkWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("bookmark")
                        .then(CommandManager.literal("view")
                                .executes(context -> runView(context)))
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                        .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                        .executes(context -> runAdd(context,
                                                StringArgumentType.getString(context, "waypoint_id")))))
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                        .suggests((context, builder) -> new WaypointNameSuggestionProvider().getBookmarkSuggestions(context, builder))
                                        .executes(context -> runRemove(context,
                                                StringArgumentType.getString(context, "waypoint_id")))))));
    }

    public static int runView(CommandContext<ServerCommandSource> context) {
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getServer());

        if (
            // If this player's waypoint list is empty
                serverState.playerMap.computeIfAbsent(context.getSource().getPlayer().getUuid().toString(), uuid -> new PlayerData()).bookmarks.isEmpty()
        ) {
            context.getSource().sendMessage(Text.of("No bookmarked waypoints!"));
            return -1;
        }

        List<String> bookmarks = serverState.playerMap.computeIfAbsent(context.getSource().getPlayer().getUuid().toString(), uuid -> new PlayerData()).bookmarks;
        context.getSource().sendMessage(Text.of("Your bookmarked waypoints"));
        for (String entry : bookmarks) {
            context.getSource().sendMessage(Text.of(
                    "-> " + entry +
                        ", created by @" + serverState.waypointMap.get(entry).author
            ));
        }

        return 1;
    }

    public static int runAdd(CommandContext<ServerCommandSource> context, String waypointId) {
        ServerCommandSource source = context.getSource();
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(source.getServer());

        try {
            PlayerData playerData = serverState.playerMap.computeIfAbsent(source.getPlayer().getUuid().toString(), uuid -> new PlayerData());
            playerData.addBookmark(waypointId);
            source.sendMessage(Text.of("\"" + waypointId + "\" added to your bookmarks! Run [/waypoint bookmark view] to view them"));
            return 1;
        } catch (CommandException e) {
            source.sendMessage(Text.of("Error adding waypoint: " + e.getMessage()));
            return -1;
        }
    }

    public static int runRemove(CommandContext<ServerCommandSource> context, String waypointId) {
        ServerCommandSource source = context.getSource();
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(source.getServer());

        try {
            PlayerData playerData = serverState.playerMap.computeIfAbsent(source.getPlayer().getUuid().toString(), uuid -> new PlayerData());
            if (!playerData.bookmarks.contains(waypointId)) source.sendMessage(Text.of("\"" + waypointId + "\" wasn't in your bookmarks!"));
            playerData.deleteBookmark(waypointId);
            source.sendMessage(Text.of("\"" + waypointId + "\" removed from your bookmarks! Run [/waypoint bookmark view] to view them"));
            return 1;
        } catch (CommandException e) {
            source.sendMessage(Text.of("Error removing waypoint: " + e.getMessage()));
            return -1;
        }
    }
}
