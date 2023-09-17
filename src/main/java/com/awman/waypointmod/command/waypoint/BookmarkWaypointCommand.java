package com.awman.waypointmod.command.waypoint;

import net.minecraft.text.*;
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
                                                StringArgumentType.getString(context, "waypoint_id")))))));
    }

    public static int runView(CommandContext<ServerCommandSource> context) {
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getServer());

        if (
            // If this player's waypoint list is empty
            serverState.playerMap.get(context.getSource().getPlayer().getUuid()).bookmarks.isEmpty()
        ) {
            context.getSource().sendMessage(Text.of("No bookmarked waypoints!"));
            return -1;
        }

        List<String> bookmarks = serverState.playerMap.get(context.getSource().getPlayer().getUuid()).bookmarks;
        for (String bookmark : bookmarks) {
            context.getSource().sendMessage(Text.of(
                    "->" + bookmark
            ));
        }

        return 1;
    }

    public static int runAdd(CommandContext<ServerCommandSource> context, String waypointId) {
        ServerCommandSource source = context.getSource();
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(source.getServer());
        context.getSource().sendMessage(Text.of("made it here..."));

        try {
            PlayerData playerData = serverState.playerMap.get(source.getPlayer().getUuid().toString());
            playerData.addBookmark(waypointId);
            source.sendMessage(Text.of("\"" + waypointId + "\" added to your bookmarks!"));
        } catch (CommandException e) {
            source.sendMessage(Text.of("Error adding waypoint: " + e.getMessage()));
        }

        //context.getSource().sendMessage(Text.of("\"" + waypointId + "\" added to your bookmarks! View them with [/waypoint bookmark view]"));

        return 1;
    }
}
