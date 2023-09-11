package com.awman.waypointmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

public class ListWaypointCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("list")
                        .then(CommandManager.argument("username", UuidArgumentType.uuid())
                                .executes(context -> run(context)))));
    }

    public static int run(CommandContext<ServerCommandSource> context) {

        return 1;
    }
}
