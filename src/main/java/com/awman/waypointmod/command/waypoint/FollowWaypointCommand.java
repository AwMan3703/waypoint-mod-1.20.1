package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FollowWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("follow")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                .executes(context -> runFollow(context,
                                        StringArgumentType.getString(context, "waypoint_id")))))
                .then(CommandManager.literal("unfollow")
                        .executes(context -> runUnfollow(context))));
    }

    public static int runFollow(CommandContext<ServerCommandSource> context, String waypointId) {
        CommandManager commandManager = context.getSource().getServer().getCommandManager();
        CommandSource playerCommandSource = context.getSource().getPlayer().getCommandSource();

        commandManager.execute((ParseResults<ServerCommandSource>) playerCommandSource, "title @s title testing");

        context.getSource().sendMessage(Text.of("Following waypoint!!!! :)))"));
        return 1;
    }

    public static int runUnfollow(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.of("Waypoint unfollowed :("));
        return 1;
    }
}
