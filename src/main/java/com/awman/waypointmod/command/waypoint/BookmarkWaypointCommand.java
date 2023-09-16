package com.awman.waypointmod.command.waypoint;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class BookmarkWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("bookmark")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .executes(context -> run(context, dispatcher)))));
    }

    public static int run(CommandContext<ServerCommandSource> context, CommandDispatcher<ServerCommandSource> d) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        context.getSource().getPlayer().getServer().getCommandManager().execute(new ParseResults<>(new CommandContextBuilder<>(d, context.getSource(), context.getRootNode(), 0)), "say hello");
        return 1;
    }
}
